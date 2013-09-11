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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;

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
		return "org.catrobat.html5player.html5player";
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
	 * @param name of Look
	 * @return LookData for costume
	 */
	private LookData createCostumeData(String name) {
		LookData data = new LookData();
		data.setName(name);
		return data;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testWait() {
		
		String costumeName1 = "costume1";
		LookData costumeData1 = createCostumeData(costumeName1);
		
		LookData costumeData2 = createCostumeData(costumeNameResult_);
		
		String spriteName = "sprite1";
		sprite_ = spriteManager.getSprite(spriteName, true);
		
		sprite_.addLookData(costumeData1);
		sprite_.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite_.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String startScriptName = "broadcastScript";
		StartScript startScript = new StartScript(sprite_, startScriptName);
		
		Formula time = new Formula(0.4); 
		WaitBrick waitBrick = new WaitBrick(spriteName, time, startScript);
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		startScript.addBrick(waitBrick);
		startScript.addBrick(nextLookBrick);
		
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
				
				//execute NextLookBrick
				scheduler.execute();
				
				assertEquals(costumeNameResult_, sprite_.getLook().getLookData().getName());
				
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
		LookData costumeData1 = createCostumeData(costumeName1);
		
		LookData costumeData2 = createCostumeData(costumeNameResult_);
		
		String spriteName = "sprite1";
		sprite_ = spriteManager.getSprite(spriteName, true);
		
		sprite_.addLookData(costumeData1);
		sprite_.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite_.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String startScriptName = "broadcastScript";
		StartScript startScript = new StartScript(sprite_, startScriptName);
		
		//int time = 1000; //ms
		Formula time = new Formula(1);
		WaitBrick waitBrick = new WaitBrick(spriteName, time, startScript);
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		startScript.addBrick(waitBrick);
		startScript.addBrick(nextLookBrick);
		
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
				
				//execute NextLookBrick
				scheduler.execute();
				
				assertEquals("costume1", sprite_.getLook().getLookData().getName());
				
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
