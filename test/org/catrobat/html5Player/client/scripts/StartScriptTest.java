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
package org.catrobat.html5Player.client.scripts;

import java.util.ArrayList;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.Brick;
import org.catrobat.html5Player.client.bricks.LoopEndBrick;
import org.catrobat.html5Player.client.bricks.NextCostumeBrick;
import org.catrobat.html5Player.client.bricks.RepeatBrick;
import org.catrobat.html5Player.client.bricks.SetCostumeBrick;
import org.catrobat.html5Player.client.bricks.WaitBrick;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class StartScriptTest extends GWTTestCase {

	private Sprite sprite_ = null; //for asynchronous testing
	
	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	public StartScriptTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.getSpriteManager().reset(); //important
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
	public void testNewScriptNoBrickList() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		assertEquals(spriteName, startScript.getSprite().getName());
		assertEquals("StartScript", startScript.getType());
		assertTrue(startScript.getBrickList().isEmpty());
	}
	
	/**
	 * 
	 */
	public void testNewScriptWithBrickList() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		ArrayList<Brick> brickList = new ArrayList<Brick>();
		brickList.add(setBrick);
		brickList.add(nextBrick);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName, brickList);
		
		assertEquals(spriteName, startScript.getSprite().getName());
		assertEquals("StartScript", startScript.getType());
		assertEquals(2, startScript.getBrickList().size());
	}
	
	/**
	 * 
	 */
	public void testAddBrickGetBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.addBrick(setBrick);
		
		int position = 0;
		startScript.addBrick(nextBrick, position);
		
		assertEquals(2, startScript.getBrickList().size());
		assertTrue(startScript.getBrick(position) instanceof NextCostumeBrick);
		assertTrue(startScript.getBrick(position+1) instanceof SetCostumeBrick);
		
		startScript.addBrick(null);
		assertEquals(2, startScript.getBrickList().size());
		
		startScript.addBrick(null, position);
		assertEquals(2, startScript.getBrickList().size());
		assertTrue(startScript.getBrick(position) instanceof NextCostumeBrick);
	}
	
	/**
	 * 
	 */
	public void testAddBrickMultipleTimes() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.addBrick(setBrick);
		startScript.addBrick(nextBrick);
		startScript.addBrick(nextBrick);
		
		assertEquals(3, startScript.getBrickList().size());
		assertTrue(startScript.getBrick(0) instanceof SetCostumeBrick);
		assertTrue(startScript.getBrick(1) instanceof NextCostumeBrick);
		assertTrue(startScript.getBrick(2) instanceof NextCostumeBrick);
	}
	
	/**
	 * 
	 */
	public void testDeleteBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.addBrick(setBrick);
		
		int position = 0;
		startScript.addBrick(nextBrick, position);
		
		assertEquals(2, startScript.getBrickList().size());
		assertTrue(startScript.getBrick(position) instanceof NextCostumeBrick);
		assertTrue(startScript.getBrick(position+1) instanceof SetCostumeBrick);
		
		startScript.deleteBrick(position);
		assertEquals(1, startScript.getBrickList().size());
		assertTrue(startScript.getBrick(position) instanceof SetCostumeBrick);
		
		startScript.deleteBrick(position);
		assertTrue(startScript.getBrickList().isEmpty());
		
		//startScript.deleteBrick(position+3); <-- IndexOutOfBoundsException TODO
	}
	
	/**
	 * 
	 */
	public void testRun() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";		
		String spriteName = "Sprite";
		
		Sprite sprite = new Sprite(spriteName);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//

		stage.getSpriteManager().addSprite(sprite);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, costumeName1);
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.addBrick(setBrick);
		startScript.addBrick(nextBrick);
		
		startScript.run();
		startScript.run();
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testRunWithNoBricks() {	
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.run();
		
		assertNull(sprite.getCostume().getCostumeData());
	}
	
//	/**
//	 * 
//	 */
//	public void testRunPauseManualResume() {	
//		String costumeName1 = "costume1";
//		String costumeName2 = "costume2";		
//		String spriteName = "Sprite";
//		
//		Sprite sprite = new Sprite(spriteName);
//		
//		sprite.addCostumeData(createCostumeData(costumeName1));
//		sprite.addCostumeData(createCostumeData(costumeName2));
//		
//		//hide costume, so drawSprite() in redrawScreen() does nothing
//		sprite.getCostume().hide();
//		//
//
//		stage.getSpriteManager().addSprite(sprite);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, costumeName1);
//		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
//		
//		String scriptName = "StartScript";
//		StartScript startScript = new StartScript(sprite, scriptName);
//		
//		startScript.addBrick(setBrick);
//		startScript.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", startScript);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		startScript.pause(1000);
//		
//		CatScheduler.get().execute();
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
//		
//		//resume
//		startScript.resume();
//		
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
//	}
//	
//	/**
//	 * 
//	 */
//	public void testRunPauseResume() {	
//		String costumeName1 = "costume1";
//		String costumeName2 = "costume2";		
//		String spriteName = "Sprite";
//		
//		sprite_ = new Sprite(spriteName);
//		
//		sprite_.addCostumeData(createCostumeData(costumeName1));
//		sprite_.addCostumeData(createCostumeData(costumeName2));
//		
//		//hide costume, so drawSprite() in redrawScreen() does nothing
//		sprite_.getCostume().hide();
//		//
//
//		stage.getSpriteManager().addSprite(sprite_);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, costumeName1);
//		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
//		
//		String scriptName = "StartScript";
//		StartScript startScript = new StartScript(sprite_, scriptName);
//		
//		startScript.addBrick(setBrick);
//		startScript.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", startScript);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		startScript.pause(500);
//		
//		CatScheduler.get().execute();
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName1, sprite_.getCostume().getCostumeData().getName());
//		
//		Timer timer = new Timer() {
//		    public void run() {
//		    	
//		    	CatScheduler.get().execute();
//				
//				assertEquals("costume2", sprite_.getCostume().getCostumeData().getName());
//
//		    	// tell the test system the test is now done
//		    	finishTest();
//		    }
//		  };
//
//		  // Set a delay period significantly longer than the
//		  // event is expected to take
//		  delayTestFinish(1000);
//
//		  // Schedule the event and return control to the test system
//		  timer.schedule(600);
//	}
	
	/**
	 * 
	 */
	public void testSetCurrentBrickIndex() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";		
		String spriteName = "Sprite";
		
		Sprite sprite = new Sprite(spriteName);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//

		stage.getSpriteManager().addSprite(sprite);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, costumeName1);
		SetCostumeBrick setBrick2 = new SetCostumeBrick(spriteName, costumeName2);
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		startScript.addBrick(setBrick);
		startScript.addBrick(setBrick2);
		startScript.addBrick(nextBrick);
		
		int currentBrickIndex = 1;
		startScript.setCurrentBrick(currentBrickIndex);
		assertEquals(currentBrickIndex, startScript.getCurrentBrick());
		
		startScript.run();
		startScript.run();
		
		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testGetLastLoopBeginBrickWithoutLoopEndBrick() {
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "StartScript";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		sprite.addScript(startScript);
		
		int timesToRepeatOuter = 3;
		RepeatBrick repeatBrickOuter = new RepeatBrick(spriteName, timesToRepeatOuter);
		
		int timesToRepeatInner = 1;
		RepeatBrick repeatBrickInner = new RepeatBrick(spriteName, timesToRepeatInner);
		
		startScript.addBrick(repeatBrickOuter);
		startScript.addBrick(repeatBrickInner);
		
		assertEquals(repeatBrickInner, startScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickInner = new LoopEndBrick(spriteName, startScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		startScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(loopEndBrickInner);
		
		assertEquals(repeatBrickOuter, startScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickOuter = new LoopEndBrick(spriteName, startScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		startScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(loopEndBrickOuter);
		
		assertEquals(loopEndBrickOuter, repeatBrickOuter.getLoopEndBrick());
		assertEquals(loopEndBrickInner, repeatBrickInner.getLoopEndBrick());
	}
	
}
