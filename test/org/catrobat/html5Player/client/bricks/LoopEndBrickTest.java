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
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class LoopEndBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public LoopEndBrickTest() {
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
		stage.getSpriteManager().reset();
		CatScheduler.get().clear();
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
	public void testExecuteWithRepeatBrick() {
		int timesToRepeat = 2;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		repeatBrick.execute();
		
		assertEquals(2, loopEndBrick.getTimesToRepeat());
		
		loopEndBrick.execute();
		
		assertEquals(1, loopEndBrick.getTimesToRepeat());
		assertEquals(0, startScript.getCurrentBrick());
	}
	
	/**
	 * 
	 */
	public void testExecuteWithForeverBrick() {

		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		ForeverBrick foreverBrick = new ForeverBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, foreverBrick);
		foreverBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(foreverBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		foreverBrick.execute();
		
		assertEquals(LoopEndBrick.FOREVER, loopEndBrick.getTimesToRepeat());
		
		loopEndBrick.execute();
		
		assertEquals(LoopEndBrick.FOREVER, loopEndBrick.getTimesToRepeat());
		assertEquals(0, startScript.getCurrentBrick());
	}
	
	/**
	 * 
	 */
	public void testLoopTwoTimes() {
		int timesToRepeat = 2;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		String costumeName1 = "coustme1";
		String costumeName2 = "coustme2";
		String costumeName3 = "coustme3";
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeName1);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		NextCostumeBrick nexCostumeBrick = new NextCostumeBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(setCostumeBrick);
		startScript.addBrick(repeatBrick);
		startScript.addBrick(nexCostumeBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		sprite.addCostumeData(createCostumeData(costumeName3));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//
		
		sprite.run();
		
		CatScheduler.get().execute(); //set
		CatScheduler.get().execute(); //repeat
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop
		
		assertEquals(costumeName3, sprite.getCostume().getCostumeData().getName());
		assertEquals(0, CatScheduler.get().getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testForever() {

		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		String costumeName1 = "coustme1";
		String costumeName2 = "coustme2";
		String costumeName3 = "coustme3";
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeName1);
		
		ForeverBrick foreverBrick = new ForeverBrick(spriteName);
		NextCostumeBrick nexCostumeBrick = new NextCostumeBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, foreverBrick);
		
		foreverBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(setCostumeBrick);
		startScript.addBrick(foreverBrick);
		startScript.addBrick(nexCostumeBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		sprite.addCostumeData(createCostumeData(costumeName3));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//
		
		sprite.run();
		
		CatScheduler.get().execute(); //set
		CatScheduler.get().execute(); //forever
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop
		
		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
		
		assertEquals(1, CatScheduler.get().getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testExecuteWithRepeatBrickGetTimesToRepeat() {
		int timesToRepeat = 160;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		repeatBrick.execute();
		
		for(int i = 0; i < timesToRepeat; i++) {
			loopEndBrick.execute();
		}
		
		assertEquals(0, loopEndBrick.getTimesToRepeat());
	}
	
}
