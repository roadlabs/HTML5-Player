package org.catrobat.html5Player.client.scripts;

import java.util.List;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.Brick;
import org.catrobat.html5Player.client.threading.CatScheduler;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class WhenScript extends Script {

	public static final String SCRIPT_TYPE = "TouchScript";

//	private int currentBrick = 0;
//	private boolean stop = false;

	public WhenScript(Sprite sprite, String name) {
		super(sprite, SCRIPT_TYPE, name);
	}

	public WhenScript(Sprite sprite, String name, List<Brick> list) {
		super(sprite, SCRIPT_TYPE, name);
		if (list != null) {
			this.brickList.addAll(list);
		}
	}
	
	public void resetWhenScript() {
		System.out.println("<<< resetWhenScript - sprite: " + getSprite().getName() + " >>>");
		this.currentBrick = 0;
		this.resetWorkDone();
		this.scriptFinished = false;
	}

//	public void run() {
//		
//		
//		brickList.get(currentBrick).execute();
//		Stage.getInstance().getSpriteManager().redrawScreen();
//		
//		if(currentBrick < (brickList.size() - 1))
//			currentBrick++;
//		else
//			setWorkDone();
//		
//		
//	}
//
//	//NOT IN USE
//	public void touched() {
////		for (int i = 0; i < brickList.size(); i++) {
////			if (!stop)
////				brickList.get(i).execute();
////			else
////				return;
////			currentBrick = i;
////			Stage.getInstance().getSpriteManager().redrawScreen();
////		}
//	}
//
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
////		if (sprite == null || Stage.getInstance().getSpriteManager() == null) {
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

}
