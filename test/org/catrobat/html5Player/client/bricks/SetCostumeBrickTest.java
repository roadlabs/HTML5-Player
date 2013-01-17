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

public class SetCostumeBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager manager;
	
	/**
	 * 
	 */
	public SetCostumeBrickTest() {
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
		manager.reset();
	}
	
	/**
	 * 
	 */
	public void testCostumeDataNotInList() {
		
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		Costume spriteCostume = sprite.getCostume();
		
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
	}
	
	/**
	 * 
	 */
	public void testCostumeDataInList() {
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		CostumeData newCostumeData = new CostumeData();
		newCostumeData.setName(costumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		Costume spriteCostume = sprite.getCostume();
		
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(costumeDataName, spriteCostumeData.getName());
	}

}
