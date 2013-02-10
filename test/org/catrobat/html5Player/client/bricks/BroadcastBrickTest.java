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
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;



public class BroadcastBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private CatScheduler scheduler;
	
	public BroadcastBrickTest() {
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
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		spriteManager.reset();
		scheduler.clear();
		
		stage.getMessageContainer().clear();
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
	public void testSingleBroadcastScript() {
		
		String costumeName1 = "costume1";
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName = "broadcastScript";
		String message = "Next";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		
		
		sprite.addScript(broadcastScript);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		
		assertEquals(costumeName2, sprite.getLook().getLookData().getName());
	}
	
	/**
	 * 
	 */
	public void testMultipleBroadcastScripts() {
		
		String costumeName1 = "costume1";
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String costumeName3 = "costume3";
		LookData costumeData3 = createCostumeData(costumeName3);
		
		String spriteName = "sprite2";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		sprite.addLookData(costumeData3);
		
		//simulate SetLookBrick
		sprite.getLook().setLookData(costumeData1);
		
		//create BroadcastScripts
		String message = "Next";
		
		String broadcastScriptName = "broadcastScript";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite, broadcastScriptName2, message);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		
		NextLookBrick nextCostumeBrick2 = new NextLookBrick(spriteName);
		broadcastScript2.addBrick(nextCostumeBrick2);
		
		
		sprite.addScript(broadcastScript);
		sprite.addScript(broadcastScript2);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		//execute BroadcastBrick
		broadcastBrick.execute();
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName3, sprite.getLook().getLookData().getName());
	}
	
	/**
	 * Tests if the BroadcastBrick resets the BroadcastScript (currentBrick)
	 */
	public void testMultipleExecution() {
		
		String costumeName1 = "costume1";
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		
		Look look = sprite.getLook();
		
		//simulate SetLookBrick
		look.setLookData(costumeData1);
		
		look.hide();
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName = "broadcastScript";
		String message = "Next";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		ShowBrick showBrick = new ShowBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		broadcastScript.addBrick(showBrick);
		
		
		sprite.addScript(broadcastScript);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		
		//first execution
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName2, look.getLookData().getName());
		assertTrue(look.isVisible());
		
		look.hide();
		assertFalse(look.isVisible());
		
		//second execution
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName1, look.getLookData().getName());
		assertTrue(look.isVisible());
	}

}
