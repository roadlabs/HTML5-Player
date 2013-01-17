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

//import org.catrobat.html5Player.client.Sprite;
//import org.catrobat.html5Player.client.Stage;
//import org.catrobat.html5Player.client.bricks.NextLookBrick;
//import org.catrobat.html5Player.client.bricks.SetCostumeBrick2;
//import org.catrobat.html5Player.client.common.LookData;
//import org.catrobat.html5Player.client.threading.CatScheduler;
//import org.catrobat.html5Player.client.threading.CatThread;
//
//import com.google.gwt.canvas.client.Canvas;
//import com.google.gwt.junit.client.GWTTestCase;
//import com.google.gwt.user.client.Timer;
//
//public class ScriptTest extends GWTTestCase {
//
//	private Sprite sprite_ = null; //for asynchronous testing
//	
//	private Stage stage;
//	private int canvasCoordinateSpaceWidth = 100;
//	private int canvasCoordinateSpaceHeight = 100;
//	
//	public ScriptTest() {
//		stage = Stage.getInstance();
//	}
//	
//	@Override
//	public String getModuleName() {
//		return "org.catrobat.html5Player.html5player";
//	}
//	
//	public void gwtSetUp() {
//		Canvas canvas = Canvas.createIfSupported();
//		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
//		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
//		stage.setCanvas(canvas);
//	}
//	
//	public void gwtTearDown() {
//		stage.setCanvas(null);
//		stage.getSpriteManager().reset(); //important
//		sprite_ = null;
//	}
//	
//	//--------------------------------------------------------------------------
//	
//	/**
//	 * Helper
//	 * @param name of Look
//	 * @return LookData for costume
//	 */
//	private LookData createCostumeData(String name) {
//		LookData data = new LookData();
//		data.setName(name);
//		return data;
//	}
//	
//	//--------------------------------------------------------------------------
//	
//	/**
//	 * 
//	 */
//	public void testNewScriptNoBrickList() {
//		String spriteName = "Sprite";
//		Sprite sprite = new Sprite(spriteName);
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		assertEquals(spriteName, script.getSprite().getName());
//		assertEquals(scriptName, script.getName());
//		assertEquals("StartScript", script.getType());
//		assertTrue(script.getBrickList().isEmpty());
//	}
//	
//	/**
//	 * 
//	 */
//	public void testAddBrickGetBrick() {
//		String spriteName = "Sprite";
//		Sprite sprite = new Sprite(spriteName);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, "costumeName");
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		
//		int position = 0;
//		script.addBrick(nextBrick, position);
//		
//		assertEquals(2, script.getBrickList().size());
//		assertTrue(script.getBrick(position) instanceof NextLookBrick);
//		assertTrue(script.getBrick(position+1) instanceof SetCostumeBrick2);
//		
//		script.addBrick(null);
//		assertEquals(2, script.getBrickList().size());
//		
//		script.addBrick(null, position);
//		assertEquals(2, script.getBrickList().size());
//		assertTrue(script.getBrick(position) instanceof NextLookBrick);
//	}
//	
//	/**
//	 * 
//	 */
//	public void testAddBrickMultipleTimes() {
//		String spriteName = "Sprite";
//		Sprite sprite = new Sprite(spriteName);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, "costumeName");
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		script.addBrick(nextBrick);
//		script.addBrick(nextBrick);
//		
//		assertEquals(3, script.getBrickList().size());
//		assertTrue(script.getBrick(0) instanceof SetCostumeBrick2);
//		assertTrue(script.getBrick(1) instanceof NextLookBrick);
//		assertTrue(script.getBrick(2) instanceof NextLookBrick);
//	}
//	
//	/**
//	 * 
//	 */
//	public void testDeleteBrick() {
//		String spriteName = "Sprite";
//		Sprite sprite = new Sprite(spriteName);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, "costumeName");
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		
//		int position = 0;
//		script.addBrick(nextBrick, position);
//		
//		assertEquals(2, script.getBrickList().size());
//		assertTrue(script.getBrick(position) instanceof NextLookBrick);
//		assertTrue(script.getBrick(position+1) instanceof SetCostumeBrick2);
//		
//		script.deleteBrick(position);
//		assertEquals(1, script.getBrickList().size());
//		assertTrue(script.getBrick(position) instanceof SetCostumeBrick2);
//		
//		script.deleteBrick(position);
//		assertTrue(script.getBrickList().isEmpty());
//		
//		//startScript.deleteBrick(position+3); <-- IndexOutOfBoundsException TODO
//	}
//	
//	/**
//	 * 
//	 */
//	public void testRun() {
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
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		script.addBrick(nextBrick);
//		
//		script.run();
//		script.run();
//		
//		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
//	}
//	
//	/**
//	 * 
//	 */
//	public void testRunWithNoBricks() {	
//		String spriteName = "Sprite";
//		Sprite sprite = new Sprite(spriteName);
//		stage.getSpriteManager().addSprite(sprite);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.run();
//		
//		assertNull(sprite.getCostume().getCostumeData());
//	}
//	
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
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		script.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", script);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		script.pause(1000);
//		
//		CatScheduler.get().execute();
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
//		
//		//resume
//		script.resume();
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
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite_, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		script.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", script);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		script.pause(500);
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
//	
//	/**
//	 * 
//	 */
//	public void testSetCurrentBrickIndex() {
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
//		SetCostumeBrick2 setBrick2 = new SetCostumeBrick2(spriteName, costumeName2);
//		NextLookBrick nextBrick = new NextLookBrick(spriteName);
//		
//		String scriptName = "Script";
//		Script script = new Script(sprite, StartScript.SCRIPT_TYPE, scriptName);
//		
//		script.addBrick(setBrick);
//		script.addBrick(setBrick2);
//		script.addBrick(nextBrick);
//		
//		int currentBrickIndex = 1;
//		script.setCurrentBrick(currentBrickIndex);
//		assertEquals(currentBrickIndex, script.getCurrentBrick());
//		
//		script.run();
//		script.run();
//		
//		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
//	}
//	
//}
