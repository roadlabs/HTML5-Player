package org.catrobat.html5Player.client.scripts;

import java.util.List;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.Brick;
import org.catrobat.html5Player.client.threading.CatScheduler;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class StartScript extends Script {

	public static final String SCRIPT_TYPE = "StartScript";

//	private int currentBrick = 0;
//	private boolean stop = false;

	public StartScript(Sprite sprite, String name) {
		super(sprite, SCRIPT_TYPE, name);
	}

	public StartScript(Sprite sprite, String name, List<Brick> list) {
		super(sprite, SCRIPT_TYPE, name);
		if (list != null) {
			this.brickList.addAll(list);
		}
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
//		System.out.println(" > > > > Script.run()");
//		System.out.println(" > > > > execute brick");
//		
//		brickList.get(currentBrick).execute();
//		
//		System.out.println(" > > > > redraw screen");
//
//		Stage.getInstance().getSpriteManager().redrawScreen();
//		
//		if(currentBrick < (brickList.size() - 1)) {
//			currentBrick++;
//			System.out.println(" > > > > incremented currentBrick");
//		}
//		else {
//			setWorkDone();
//			System.out.println(" > > > > Schript finished");
//		}
//	}
//
//	public void pause(int time) {
//		System.out.println("pause for " + time + "ms");
//		
////		CatScheduler.get().getThread(sprite.getName() + this.name).sleep();
//		pauseWork();
//		
////		stop = true;
//		
//		if (time > 0) {
//			Timer t = new Timer() {
//				public void run() {
////					currentBrick++;
//					System.out.println("pause is over, resume()");
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
////		CatScheduler.get().getThread(sprite.getName() + this.name).wake();
//		System.out.println("resumeWork()");
//		resumeWork();
//		
//		
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

}
