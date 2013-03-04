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
package org.catrobat.html5player.client.scripts;

import java.util.ArrayList;

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.Brick;
import org.catrobat.html5player.client.bricks.LoopEndBrick;
import org.catrobat.html5player.client.bricks.NextLookBrick;
import org.catrobat.html5player.client.bricks.RepeatBrick;
import org.catrobat.html5player.client.bricks.SetLookBrick;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

//import com.google.gwt.user.client.Timer;

public class WhenScriptTest extends GWTTestCase {

	// private Sprite sprite_ = null; //for asynchronous testing

	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;

	public WhenScriptTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
	}

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	public void gwtSetUp() {
		// Canvas canvas = Canvas.createIfSupported();
		// canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
		// canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);

		scene.createScene(canvasCoordinateSpaceWidth,
				canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}

	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.getSpriteManager().reset(); // important
		// sprite_ = null;

		CatScheduler.get().clear();
	}

	// --------------------------------------------------------------------------

	/**
	 * Helper
	 * 
	 * @param name
	 *            of Look
	 * @return LookData for costume
	 */
	private LookData createCostumeData(String name) {
		LookData data = new LookData();
		data.setName(name);
		return data;
	}

	// --------------------------------------------------------------------------

	/**
	 * 
	 */
	public void testNewScriptNoBrickList() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		assertEquals(spriteName, whenScript.getSprite().getName());
		assertEquals("TouchScript", whenScript.getType());
		assertTrue(whenScript.getBrickList().isEmpty());
	}

	/**
	 * 
	 */
	public void testNewScriptWithBrickList() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		SetLookBrick setBrick = new SetLookBrick(spriteName, "costumeName");
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		ArrayList<Brick> brickList = new ArrayList<Brick>();
		brickList.add(setBrick);
		brickList.add(nextBrick);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName, brickList);

		assertEquals(spriteName, whenScript.getSprite().getName());
		assertEquals("TouchScript", whenScript.getType());
		assertEquals(2, whenScript.getBrickList().size());
	}

	/**
	 * 
	 */
	public void testAddBrickGetBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		SetLookBrick setBrick = new SetLookBrick(spriteName, "costumeName");
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);

		int position = 0;
		whenScript.addBrick(nextBrick, position);

		assertEquals(2, whenScript.getBrickList().size());
		assertTrue(whenScript.getBrick(position) instanceof NextLookBrick);
		assertTrue(whenScript.getBrick(position + 1) instanceof SetLookBrick);

		whenScript.addBrick(null);
		assertEquals(2, whenScript.getBrickList().size());

		whenScript.addBrick(null, position);
		assertEquals(2, whenScript.getBrickList().size());
		assertTrue(whenScript.getBrick(position) instanceof NextLookBrick);
	}

	/**
	 * 
	 */
	public void testAddBrickMultipleTimes() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		SetLookBrick setBrick = new SetLookBrick(spriteName, "costumeName");
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);
		whenScript.addBrick(nextBrick);
		whenScript.addBrick(nextBrick);

		assertEquals(3, whenScript.getBrickList().size());
		assertTrue(whenScript.getBrick(0) instanceof SetLookBrick);
		assertTrue(whenScript.getBrick(1) instanceof NextLookBrick);
		assertTrue(whenScript.getBrick(2) instanceof NextLookBrick);
	}

	/**
	 * 
	 */
	public void testDeleteBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		SetLookBrick setBrick = new SetLookBrick(spriteName, "costumeName");
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);

		int position = 0;
		whenScript.addBrick(nextBrick, position);

		assertEquals(2, whenScript.getBrickList().size());
		assertTrue(whenScript.getBrick(position) instanceof NextLookBrick);
		assertTrue(whenScript.getBrick(position + 1) instanceof SetLookBrick);

		whenScript.deleteBrick(position);
		assertEquals(1, whenScript.getBrickList().size());
		assertTrue(whenScript.getBrick(position) instanceof SetLookBrick);

		whenScript.deleteBrick(position);
		assertTrue(whenScript.getBrickList().isEmpty());

	}

	/**
	 * 
	 */
	public void testRun() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";
		String spriteName = "Sprite";

		Sprite sprite = new Sprite(spriteName);

		sprite.addLookData(createCostumeData(costumeName1));
		sprite.addLookData(createCostumeData(costumeName2));

		// hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getLook().hide();
		//

		stage.getSpriteManager().addSprite(sprite);

		SetLookBrick setBrick = new SetLookBrick(spriteName, costumeName1);
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);
		whenScript.addBrick(nextBrick);

		// costume should change
		whenScript.run();
		whenScript.run();

		assertEquals(costumeName2, sprite.getLook().getLookData().getName());
	}

	/**
	 * 
	 */
	public void testRunWithNoBricks() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.run();

		assertNull(sprite.getLook().getLookData());
	}

	/**
	 * 
	 */
	public void testResetWhenScript() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";
		String spriteName = "Sprite";

		Sprite sprite = new Sprite(spriteName);

		sprite.addLookData(createCostumeData(costumeName1));
		sprite.addLookData(createCostumeData(costumeName2));

		// hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getLook().hide();
		//

		stage.getSpriteManager().addSprite(sprite);

		SetLookBrick setBrick = new SetLookBrick(spriteName, costumeName1);
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);
		whenScript.addBrick(nextBrick);

		// run script as a thread
		CatThread thread = new CatThread("thread", whenScript);
		CatScheduler.get().schedule(thread);

		// costume should change
		CatScheduler.get().execute();
		CatScheduler.get().execute();

		assertEquals(costumeName2, sprite.getLook().getLookData().getName());

		whenScript.resetWhenScript();
		whenScript.deleteBrick(1); // delete NextLookBrick

		// run script again as a thread
		CatThread thread2 = new CatThread("thread", whenScript);
		CatScheduler.get().schedule(thread2);

		// costume should change
		CatScheduler.get().execute();

		assertEquals(costumeName1, sprite.getLook().getLookData().getName());
	}

	// /**
	// *
	// */
	// public void testRunPauseManualResume() {
	// String costumeName1 = "costume1";
	// String costumeName2 = "costume2";
	// String spriteName = "Sprite";
	//
	// Sprite sprite = new Sprite(spriteName);
	//
	// sprite.addCostumeData(createCostumeData(costumeName1));
	// sprite.addCostumeData(createCostumeData(costumeName2));
	//
	// //hide costume, so drawSprite() in redrawScreen() does nothing
	// sprite.getCostume().hide();
	// //
	//
	// stage.getSpriteManager().addSprite(sprite);
	//
	// SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName,
	// costumeName1);
	// NextLookBrick nextBrick = new NextLookBrick(spriteName);
	//
	// String scriptName = "WhenScript";
	// WhenScript whenScript = new WhenScript(sprite, scriptName);
	//
	// whenScript.addBrick(setBrick);
	// whenScript.addBrick(nextBrick);
	//
	// //run script as a thread
	// CatThread thread = new CatThread("thread", whenScript);
	// CatScheduler.get().schedule(thread);
	//
	// CatScheduler.get().execute();
	//
	// //pause
	// whenScript.pause(1000);
	//
	// CatScheduler.get().execute();
	// CatScheduler.get().execute();
	//
	// assertEquals(costumeName1,
	// sprite.getCostume().getCostumeData().getName());
	//
	// //resume
	// whenScript.resume();
	//
	// CatScheduler.get().execute();
	//
	// assertEquals(costumeName2,
	// sprite.getCostume().getCostumeData().getName());
	// }
	//
	// /**
	// *
	// */
	// public void testRunPauseResume() {
	// String costumeName1 = "costume1";
	// String costumeName2 = "costume2";
	// String spriteName = "Sprite";
	//
	// sprite_ = new Sprite(spriteName);
	//
	// sprite_.addCostumeData(createCostumeData(costumeName1));
	// sprite_.addCostumeData(createCostumeData(costumeName2));
	//
	// //hide costume, so drawSprite() in redrawScreen() does nothing
	// sprite_.getCostume().hide();
	// //
	//
	// stage.getSpriteManager().addSprite(sprite_);
	//
	// SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName,
	// costumeName1);
	// NextLookBrick nextBrick = new NextLookBrick(spriteName);
	//
	// String scriptName = "WhenScript";
	// WhenScript whenScript = new WhenScript(sprite_, scriptName);
	//
	// whenScript.addBrick(setBrick);
	// whenScript.addBrick(nextBrick);
	//
	// //run script as a thread
	// CatThread thread = new CatThread("thread", whenScript);
	// CatScheduler.get().schedule(thread);
	//
	// CatScheduler.get().execute();
	//
	// //pause
	// whenScript.pause(500);
	//
	// CatScheduler.get().execute();
	// CatScheduler.get().execute();
	//
	// assertEquals(costumeName1,
	// sprite_.getCostume().getCostumeData().getName());
	//
	// Timer timer = new Timer() {
	// public void run() {
	//
	// CatScheduler.get().execute();
	//
	// assertEquals("costume2",
	// sprite_.getCostume().getCostumeData().getName());
	//
	// // tell the test system the test is now done
	// finishTest();
	// }
	// };
	//
	// // Set a delay period significantly longer than the
	// // event is expected to take
	// delayTestFinish(1000);
	//
	// // Schedule the event and return control to the test system
	// timer.schedule(600);
	// }

	/**
	 * 
	 */
	public void testSetCurrentBrickIndex() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";
		String spriteName = "Sprite";

		Sprite sprite = new Sprite(spriteName);

		sprite.addLookData(createCostumeData(costumeName1));
		sprite.addLookData(createCostumeData(costumeName2));

		// hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getLook().hide();
		//

		stage.getSpriteManager().addSprite(sprite);

		SetLookBrick setBrick = new SetLookBrick(spriteName, costumeName1);
		SetLookBrick setBrick2 = new SetLookBrick(spriteName, costumeName2);
		NextLookBrick nextBrick = new NextLookBrick(spriteName);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		whenScript.addBrick(setBrick);
		whenScript.addBrick(setBrick2);
		whenScript.addBrick(nextBrick);

		int currentBrickIndex = 1;
		whenScript.setCurrentBrick(currentBrickIndex);
		assertEquals(currentBrickIndex, whenScript.getCurrentBrick());

		whenScript.run();
		whenScript.run();

		assertEquals(costumeName1, sprite.getLook().getLookData().getName());
	}

	/**
	 * 
	 */
	public void testGetLastLoopBeginBrickWithoutLoopEndBrick() {
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);

		String scriptName = "WhenScript";
		WhenScript whenScript = new WhenScript(sprite, scriptName);

		sprite.addScript(whenScript);

		int timesToRepeatOuter = 3;
		RepeatBrick repeatBrickOuter = new RepeatBrick(spriteName,
				timesToRepeatOuter);

		int timesToRepeatInner = 1;
		RepeatBrick repeatBrickInner = new RepeatBrick(spriteName,
				timesToRepeatInner);

		whenScript.addBrick(repeatBrickOuter);
		whenScript.addBrick(repeatBrickInner);

		assertEquals(repeatBrickInner,
				whenScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickInner = new LoopEndBrick(spriteName,
				whenScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		whenScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(
				loopEndBrickInner);

		assertEquals(repeatBrickOuter,
				whenScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickOuter = new LoopEndBrick(spriteName,
				whenScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		whenScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(
				loopEndBrickOuter);

		assertEquals(loopEndBrickOuter, repeatBrickOuter.getLoopEndBrick());
		assertEquals(loopEndBrickInner, repeatBrickInner.getLoopEndBrick());
	}

}
