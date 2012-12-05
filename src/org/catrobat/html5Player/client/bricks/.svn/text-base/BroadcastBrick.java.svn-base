package org.catrobat.html5Player.client.bricks;

import java.util.Vector;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;


public class BroadcastBrick extends Brick {

	private String message = "";

	public BroadcastBrick(String sprite, String message) {
		super(sprite);
		this.message = message;
	}

	@Override
	public boolean execute(Sprite sprite) {
		final Vector<BroadcastScript> receiver = Stage.getInstance().getMessageContainer().getReceiverOfMessage(message);
		
		if (receiver == null) {
			System.out.println("receiver == null");
			return false;
		}
		if (receiver.size() == 0) {
			System.out.println("no receivers for message: " + message);
			return true;
		}
		for (BroadcastScript receiverScript : receiver) {
			
			receiverScript.resetBroadcastScript(); ///TODO ???????
			
			receiverScript.executeBroadcast();
		}
		
		return true;
	}

}
