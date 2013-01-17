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
import org.catrobat.html5Player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ForeverBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ForeverBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
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
	public void testExecute() {	
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		ForeverBrick repeatBrick = new ForeverBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		repeatBrick.execute();
		
		assertEquals(LoopEndBrick.FOREVER, loopEndBrick.getTimesToRepeat());
	}
	
	/**
	 * 
	 */
	public void testForeverNextCostume() {	
		
		CatScheduler.get().clear();
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";
		String costumeName3 = "costume3";
		
		CostumeData costumeData1 = createCostumeData(costumeName1);
		CostumeData costumeData2 = createCostumeData(costumeName2);
		CostumeData costumeData3 = createCostumeData(costumeName3);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		sprite.addCostumeData(costumeData3);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		ForeverBrick repeatBrick = new ForeverBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(nextCostumeBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		sprite.getCostume().hide();
		
		CatThread thread = new CatThread("threadName", startScript);
		CatScheduler.get().schedule(thread);
		
		CatScheduler.get().execute(); //repeat
		
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop end
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
		
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop end
		
		assertEquals(costumeName3, sprite.getCostume().getCostumeData().getName());
		
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop end
		
		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
		
		CatScheduler.get().execute(); //next
		CatScheduler.get().execute(); //loop end
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
		
		assertEquals(LoopEndBrick.FOREVER, loopEndBrick.getTimesToRepeat());
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		CatScheduler.get().clear();
	}

}
