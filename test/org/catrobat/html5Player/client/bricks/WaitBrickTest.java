/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;



public class WaitBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	
	private Sprite sprite_ = null; //for asynchronous testing
	private String costumeNameResult_ = "costumeNameResult";
	
	private CatScheduler scheduler;
	
	public WaitBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
		scheduler = CatScheduler.get();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
//		Canvas canvas = Canvas.createIfSupported();
//		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
//		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
		
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		spriteManager.reset();
		scheduler.clear();
		sprite_ = null;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * Helper
	 * @param name of Costume
	 * @return CostumeData for costume
	 */
	private CostumeData createCostumeData(String name) {
		CostumeData data = new CostumeData();
		data.setName(name);
		return data;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testWait() {
		
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		CostumeData costumeData2 = createCostumeData(costumeNameResult_);
		
		String spriteName = "sprite1";
		sprite_ = spriteManager.getSprite(spriteName, true);
		
		sprite_.addCostumeData(costumeData1);
		sprite_.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite_.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String startScriptName = "broadcastScript";
		StartScript startScript = new StartScript(sprite_, startScriptName);
		
		int time = 400; //ms
		WaitBrick waitBrick = new WaitBrick(spriteName, time, startScript);
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		startScript.addBrick(waitBrick);
		startScript.addBrick(nextCostumeBrick);
		
		CatThread thread = new CatThread(spriteName + startScriptName, startScript);
		scheduler.schedule(thread);
		
		//execute WaitBrick
		scheduler.execute();
		
		//should have no effect
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		//
		
		Timer timer = new Timer() {
			public void run() {
				
				//execute NextCostumeBrick
				scheduler.execute();
				
				assertEquals(costumeNameResult_, sprite_.getCostume().getCostumeData().getName());
				
				// tell the test system the test is now done
				finishTest();
			}
		};

		// Set a delay period significantly longer than the
		// event is expected to take
		delayTestFinish(700);

		// Schedule the event and return control to the test system
		timer.schedule(500);
	}
	
	/**
	 * 
	 */
	public void testWaitEndTestToEarly() {
		
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		CostumeData costumeData2 = createCostumeData(costumeNameResult_);
		
		String spriteName = "sprite1";
		sprite_ = spriteManager.getSprite(spriteName, true);
		
		sprite_.addCostumeData(costumeData1);
		sprite_.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite_.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String startScriptName = "broadcastScript";
		StartScript startScript = new StartScript(sprite_, startScriptName);
		
		int time = 1000; //ms
		WaitBrick waitBrick = new WaitBrick(spriteName, time, startScript);
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		startScript.addBrick(waitBrick);
		startScript.addBrick(nextCostumeBrick);
		
		CatThread thread = new CatThread(spriteName + startScriptName, startScript);
		scheduler.schedule(thread);
		
		//execute WaitBrick
		scheduler.execute();
		
		//should have no effect
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		scheduler.execute();
		//
		
		Timer timer = new Timer() {
			public void run() {
				
				//execute NextCostumeBrick
				scheduler.execute();
				
				assertEquals("costume1", sprite_.getCostume().getCostumeData().getName());
				
				// tell the test system the test is now done
				finishTest();
			}
		};

		// Set a delay period significantly longer than the
		// event is expected to take
		delayTestFinish(700);

		// Schedule the event and return control to the test system
		timer.schedule(500);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
