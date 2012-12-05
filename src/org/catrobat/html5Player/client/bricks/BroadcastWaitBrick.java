package org.catrobat.html5Player.client.bricks;

import java.util.Vector;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.Script;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;
import org.catrobat.html5Player.client.threading.WaitCount;

import com.google.gwt.user.client.Timer;

public class BroadcastWaitBrick extends Brick {

	private String message = "";
	private Script script;

	public BroadcastWaitBrick(String sprite, String message, Script script) {
		super(sprite);
		this.message = message;
		this.script = script;
	}

	@Override
	public boolean execute(Sprite sprite) {
		final Vector<BroadcastScript> receiver = Stage.getInstance().getMessageContainer().getReceiverOfMessage(message);

		CatrobatDebug.on();
		
		CatrobatDebug.console("BroadcastWait - message: " + message);
		
		if (receiver == null) {
			CatrobatDebug.console("receiver == null");
			CatrobatDebug.off();
			return false;
		}
		
		if (receiver.size() == 0) {
			CatrobatDebug.console("no receivers");
			CatrobatDebug.off();
			return true;
		}

		CatThread executiveThread = CatScheduler.get().getThread(script.getExecutor());
		
		WaitCount signaler = new WaitCount(executiveThread);
		
		for (BroadcastScript receiverScript : receiver) {
			
			receiverScript.resetBroadcastScript(); ///TODO ???????
			
			receiverScript.executeBroadcastWait(signaler);
			
			CatrobatDebug.console("BroadcastWait - receiver, script: " + receiverScript.getName() + ", sprite: " + receiverScript.getSprite().getName());
			
		}
		
		executiveThread.awaitSignal(signaler);
		
		CatrobatDebug.off();
		
		return true;
	}

}
