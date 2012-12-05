package org.catrobat.html5Player.client.scripts;

import org.catrobat.html5Player.client.MessageContainer;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.WaitCount;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class BroadcastScript extends Script {

	public static final String SCRIPT_TYPE = "BroadcastScript";

//	private int currentBrick = 0;
//	private boolean stop = false;
	
	private String selectedMessage = "";
	
	public BroadcastScript(Sprite sprite, String name, String message) {
		super(sprite, SCRIPT_TYPE, name);
		this.selectedMessage = message;
		if (selectedMessage != null && selectedMessage.length() != 0) {
			Stage.getInstance().getMessageContainer()
					.addMessage(selectedMessage, this);
		}
	}
	
	public void executeBroadcast() {
		sprite.startScriptBroadcast(this);
	}

	public void executeBroadcastWait(WaitCount signaler) {
		sprite.startScriptBroadcastWait(this, signaler);
	}
	
//	public void pause(int time) {
//		System.out.println("pause for " + time + "ms");
//		
//		CatScheduler.get().getThread(sprite.getName() + this.name).sleep();
//		
////		stop = true;
//		
//		if (time > 0) {
//			Timer t = new Timer() {
//				public void run() {
////					currentBrick++;
//					resume();
//				}
//			};
//			t.schedule(time);
//		}
////		} else {
////			currentBrick++;
////		}
//	}
//
//	public void resume() {
////		stop = false;
//		
//		CatScheduler.get().getThread(sprite.getName() + this.name).wake();
//		
////		if (sprite == null) {
////			Window.alert("Run error this sprite has never been initialized");
////		}
////		for (int i = currentBrick; i < brickList.size(); i++) {
////			if (!stop)
////				brickList.get(i).execute();
////			else
////				return;
////			currentBrick = i;
////			Stage.getInstance().getSpriteManager().redrawScreen();
////		}
//	}

	public void setBroadcastMessage(String selectedMessage) {
		MessageContainer messageContainer = Stage.getInstance()
				.getMessageContainer();
		messageContainer.deleteReceiverScript(this.selectedMessage, this);
		this.selectedMessage = selectedMessage;
		messageContainer.addMessage(this.selectedMessage, this);
	}

	public String getBroadcastMessage() {
		return this.selectedMessage;
	}
	
	public void resetBroadcastScript() {
		System.out.println("<<< resetBroadcastScript - sprite: " + getSprite().getName() + " >>>");
		this.currentBrick = 0;
		this.resetWorkDone();
		this.scriptFinished = false;
	}

//	public void run() {
////		for (int i = 0; i < brickList.size(); i++) {
////			if (!stop)
////				brickList.get(i).execute();
////			else
////				return;
////			currentBrick = i;
////			Stage.getInstance().getSpriteManager().redrawScreen();
////		}
//		
//		brickList.get(currentBrick).execute();
//		Stage.getInstance().getSpriteManager().redrawScreen();
//		
//		if(currentBrick < (brickList.size() - 1))
//			currentBrick++;
//		else
//			setWorkDone();
//	}

	public void runWait(int wait) {
//		for (int i = 0; i < brickList.size(); i++) {
//			if (!stop)
//				brickList.get(i).execute();
//			else
//				return;
//			currentBrick = i;
//			Stage.getInstance().getSpriteManager().redrawScreen();
//		}
		
		run();
	}

}
