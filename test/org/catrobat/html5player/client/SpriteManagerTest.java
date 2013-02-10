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
package org.catrobat.html5player.client;

import org.catrobat.html5player.client.bricks.NextLookBrick;
import org.catrobat.html5player.client.bricks.SetLookBrick;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.scripts.WhenScript;
import org.catrobat.html5player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SpriteManagerTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	public SpriteManagerTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {
//		Canvas canvas = Canvas.createIfSupported();
//		canvas.setCoordinateSpaceWidth(100);
//		canvas.setCoordinateSpaceHeight(100);
		
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
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
	 * TODO muss addSprite public sein?
	 */
	public void testAddSprite() {
		String testSpriteName = "testSprite";
		String testSprite2Name = "testSprite2";
		Sprite testSprite = new Sprite(testSpriteName);
		Sprite testSprite2 = new Sprite(testSprite2Name);
		
		spriteManager.addSprite(testSprite);
		spriteManager.addSprite(testSprite2);
		
		assertFalse(spriteManager.getSpriteList().isEmpty());
		assertEquals(testSprite, spriteManager.getSprite(testSpriteName, false));
		assertEquals(testSprite2, spriteManager.getSprite(testSprite2Name, false));
	}
	
	/**
	 * 
	 */
	public void testClearSpriteList() {
		
		String testSpriteName = "testSprite3";
		Sprite testSprite = new Sprite(testSpriteName);
		spriteManager.addSprite(testSprite);
		
		assertFalse("spritelist not empty", spriteManager.getSpriteList().isEmpty());
		
		spriteManager.reset();
		
		assertTrue("spritelist not empty", spriteManager.getSpriteList().isEmpty());
	}
	
	/**
	 * 
	 */
	public void testGetSpriteToCreateSprite() {
		
		spriteManager.reset();
		
		String newSpriteName = "newSprite";
		Sprite newSprite = spriteManager.getSprite(newSpriteName, true);
		
		assertEquals(newSpriteName, newSprite.getName());
		assertEquals(newSprite, spriteManager.getSprite(newSpriteName, false));
		assertEquals("A new sprite with the same name was created",
					 newSprite, spriteManager.getSprite(newSpriteName, true));
	}
	
	/**
	 * 
	 */
	public void testGetSpriteToGetSprites() {
		
		spriteManager.reset();
		
		String newSpriteName = "newSprite";
		String newSprite2Name = "newSprite2";
		String newSprite3Name = "newSprite3";
		Sprite newSprite = spriteManager.getSprite(newSpriteName, true);
		Sprite newSprite2 = spriteManager.getSprite(newSprite2Name, true);
		Sprite newSprite3 = spriteManager.getSprite(newSprite3Name, true);
		
		assertEquals(newSprite, spriteManager.getSprite(newSpriteName, false));
		assertEquals(newSprite2, spriteManager.getSprite(newSprite2Name, false));
		assertEquals(newSprite3, spriteManager.getSprite(newSprite3Name, false));
		
		assertEquals("A new sprite with the same name was created",
				 newSprite, spriteManager.getSprite(newSpriteName, true));
		assertEquals("A new sprite with the same name was created",
				 newSprite2, spriteManager.getSprite(newSprite2Name, true));
		assertEquals("A new sprite with the same name was created",
				 newSprite3, spriteManager.getSprite(newSprite3Name, true));
	}
	
	/**
	 * 
	 */
	public void testPlayCatroid() {
		
		spriteManager.reset();
		
		// Sprites
		String newSprite1Name = "newSprite";
		String newSprite2Name = "newSprite2";
		String newSprite3Name = "newSprite3";
		Sprite newSprite1 = spriteManager.getSprite(newSprite1Name, true);
		Sprite newSprite2 = spriteManager.getSprite(newSprite2Name, true);
		Sprite newSprite3 = spriteManager.getSprite(newSprite3Name, true);
		
		// Costumes
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		String costume3Name = "custome3";
		LookData costumeData1 = createCostumeData(costume1Name);
		LookData costumeData2 = createCostumeData(costume2Name);
		LookData costumeData3 = createCostumeData(costume3Name);
		
		// Add Costumes
		newSprite1.addLookData(costumeData1);
		newSprite1.addLookData(costumeData2);
		newSprite1.addLookData(costumeData3);
		
		newSprite2.addLookData(costumeData1);
		newSprite2.addLookData(costumeData2);
		newSprite2.addLookData(costumeData3);
		
		newSprite3.addLookData(costumeData1);
		newSprite3.addLookData(costumeData2);
		newSprite3.addLookData(costumeData3);
		
		// Scripts
		String script1Name = "scriptForSprite1";
		String script2Name = "scriptForSprite2";
		String script3Name = "scriptForSprite3";
		StartScript scriptSprite1 = new StartScript(newSprite1, script1Name);
		StartScript scriptSprite2 = new StartScript(newSprite2, script2Name);
		WhenScript  scriptSprite3 = new WhenScript(newSprite3, script3Name);
		
		// Bricks
		SetLookBrick setCostumeBrickSprite1 = new SetLookBrick(newSprite1Name, costume1Name);
		SetLookBrick setCostumeBrickSprite2 = new SetLookBrick(newSprite2Name, costume1Name);
		
		NextLookBrick nextCostumeBrickSprite1 = new NextLookBrick(newSprite1Name);
		NextLookBrick nextCostumeBrickSprite2 = new NextLookBrick(newSprite2Name);
		NextLookBrick nextCostumeBrickSprite3 = new NextLookBrick(newSprite3Name);
		
		// Add Bricks
		scriptSprite1.addBrick(setCostumeBrickSprite1);
		scriptSprite1.addBrick(nextCostumeBrickSprite1);
		
		scriptSprite2.addBrick(setCostumeBrickSprite2);
		scriptSprite2.addBrick(nextCostumeBrickSprite2);
		scriptSprite2.addBrick(nextCostumeBrickSprite2);
		
		scriptSprite3.addBrick(nextCostumeBrickSprite3);
		
		// Add Scripts
		newSprite1.addScript(scriptSprite1);	
		newSprite2.addScript(scriptSprite2);
		newSprite3.addScript(scriptSprite3);
		
		// run
		spriteManager.playCatroid();
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		
		assertEquals(costume2Name, newSprite1.getLook().getLookData().getName());
		assertEquals(costume3Name, newSprite2.getLook().getLookData().getName());
		assertNull(newSprite3.getLook().getLookData());
	}
	
	//TODO: tests for redrawScreen(), handleScreenClick()
	//
	//...
	
	/**
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
