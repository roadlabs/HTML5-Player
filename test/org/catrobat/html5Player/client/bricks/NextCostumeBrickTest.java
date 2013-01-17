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

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class NextCostumeBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager manager;
	
	/**
	 * 
	 */
	public NextCostumeBrickTest() {
		stage = Stage.getInstance();
		manager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
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

		Costume spriteCostume = sprite.getCostume();
		
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		assertTrue(nextCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
	}
	
	/**
	 * 
	 */
	public void testOneCostumeData() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Costume spriteCostume = sprite.getCostume();
		
		//add one new costume data to the sprite
		CostumeData newCostumeData = new CostumeData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		//no data set
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, 
															newCostumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		assertTrue(nextCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		String message = "'" + newCostumeDataName + "'" + 
						 "is not the next CostumeData";
		assertEquals(message, newCostumeDataName, spriteCostumeData.getName());
	}
	
	/**
	 * 
	 */
	public void testTwoCostumeData() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Costume spriteCostume = sprite.getCostume();
		
		//add two new costume data to the sprite
		CostumeData newCostumeData = new CostumeData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		CostumeData newCostumeData2 = new CostumeData();
		String newCostumeDataName2 = "cdata2";
		newCostumeData2.setName(newCostumeDataName2);
		sprite.addCostumeData(newCostumeData2);
		
		//no data set
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, 
															newCostumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		assertTrue(nextCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		String message = "'" + newCostumeDataName2 + "'" + 
						 "is not the next CostumeData";
		assertEquals(message, newCostumeDataName2, spriteCostumeData.getName());
	}
	
	/**
	 * 
	 */
	public void testCostumeDatas() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Costume spriteCostume = sprite.getCostume();
		
		//add three costume data to the sprite
		CostumeData newCostumeData = new CostumeData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		CostumeData newCostumeData2 = new CostumeData();
		String newCostumeDataName2 = "cdata2";
		newCostumeData2.setName(newCostumeDataName2);
		sprite.addCostumeData(newCostumeData2);
		
		CostumeData newCostumeData3 = new CostumeData();
		String newCostumeDataName3 = "cdata3";
		newCostumeData3.setName(newCostumeDataName3);
		sprite.addCostumeData(newCostumeData3);
		
		//no data set
		assertEquals(null, spriteCostume.getCostumeData());
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, 
															newCostumeDataName3);
		assertTrue(setCostumeBrick.execute()); //cdata3
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		
		assertTrue(nextCostumeBrick.execute()); //cdata
		
		assertEquals(newCostumeDataName, spriteCostume.getCostumeData().getName());
		
		assertTrue(nextCostumeBrick.execute()); //cdata2
		
		assertEquals(newCostumeDataName2, spriteCostume.getCostumeData().getName());
	}
	
	/**
	 * no costume data got set with SetCostumeBrick
	 */
	public void testCurrentCostumeDataNull() {
		
		String spriteName = "testSprite";
		Sprite sprite = manager.getSprite(spriteName, true);
		Costume spriteCostume = sprite.getCostume();
		
		//add one new costume data to the sprite
		CostumeData newCostumeData = new CostumeData();
		String newCostumeDataName = "cdata";
		newCostumeData.setName(newCostumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		//no data set
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);

		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		assertTrue(nextCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
	}

}
