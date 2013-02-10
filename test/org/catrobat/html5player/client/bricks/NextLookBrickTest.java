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

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.common.LookData;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class NextLookBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager manager;
	
	/**
	 * 
	 */
	public NextLookBrickTest() {
		stage = Stage.getInstance();
		manager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		manager.reset();
	}
	
	/**
	 * 
	 */
	public void testNoCostumeData() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);

		Look spriteCostume = sprite.getLook();
		
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		assertTrue(nextLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
	}
	
	/**
	 * 
	 */
	public void testOneCostumeData() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		//add one new costume data to the sprite
		LookData newCostumeData = new LookData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addLookData(newCostumeData);
		
		//no data set
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
		
		SetLookBrick setLookBrick = new SetLookBrick(spriteName, 
															newCostumeDataName);
		assertTrue(setLookBrick.execute());
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		assertTrue(nextLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		String message = "'" + newCostumeDataName + "'" + 
						 "is not the next LookData";
		assertEquals(message, newCostumeDataName, spriteCostumeData.getName());
	}
	
	/**
	 * 
	 */
	public void testTwoCostumeData() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		//add two new costume data to the sprite
		LookData newCostumeData = new LookData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addLookData(newCostumeData);
		
		LookData newCostumeData2 = new LookData();
		String newCostumeDataName2 = "cdata2";
		newCostumeData2.setName(newCostumeDataName2);
		sprite.addLookData(newCostumeData2);
		
		//no data set
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
		
		SetLookBrick setLookBrick = new SetLookBrick(spriteName, 
															newCostumeDataName);
		assertTrue(setLookBrick.execute());
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		assertTrue(nextLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		String message = "'" + newCostumeDataName2 + "'" + 
						 "is not the next LookData";
		assertEquals(message, newCostumeDataName2, spriteCostumeData.getName());
	}
	
	/**
	 * 
	 */
	public void testCostumeDatas() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		//add three costume data to the sprite
		LookData newCostumeData = new LookData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addLookData(newCostumeData);
		
		LookData newCostumeData2 = new LookData();
		String newCostumeDataName2 = "cdata2";
		newCostumeData2.setName(newCostumeDataName2);
		sprite.addLookData(newCostumeData2);
		
		LookData newCostumeData3 = new LookData();
		String newCostumeDataName3 = "cdata3";
		newCostumeData3.setName(newCostumeDataName3);
		sprite.addLookData(newCostumeData3);
		
		//no data set
		assertEquals(null, spriteCostume.getLookData());
		
		SetLookBrick setLookBrick = new SetLookBrick(spriteName, 
															newCostumeDataName3);
		assertTrue(setLookBrick.execute()); //cdata3
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		
		assertTrue(nextLookBrick.execute()); //cdata
		
		assertEquals(newCostumeDataName, spriteCostume.getLookData().getName());
		
		assertTrue(nextLookBrick.execute()); //cdata2
		
		assertEquals(newCostumeDataName2, spriteCostume.getLookData().getName());
	}
	
	/**
	 * no costume data got set with SetLookBrick
	 */
	public void testCurrentCostumeDataNull() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		//add one new costume data to the sprite
		LookData newCostumeData = new LookData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addLookData(newCostumeData);
		
		//no data set
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);

		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		assertTrue(nextLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
	}

}
