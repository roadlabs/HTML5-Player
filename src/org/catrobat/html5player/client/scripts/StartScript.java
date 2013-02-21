/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client.scripts;

import java.util.List;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.bricks.Brick;


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
