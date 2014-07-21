/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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

public class SetLookBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager manager;
	
	/**
	 * 
	 */
	public SetLookBrickTest() {
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
		manager.reset();
	}
	
	/**
	 * 
	 */
	public void testCostumeDataNotInList() {
		
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		Look spriteCostume = sprite.getLook();
		
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
		
		SetLookBrick setLookBrick = new SetLookBrick(spriteName, costumeDataName);
		assertTrue(setLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
	}
	
	/**
	 * 
	 */
	public void testCostumeDataInList() {
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		LookData newCostumeData = new LookData();
		newCostumeData.setName(costumeDataName);
		sprite.addLookData(newCostumeData);
		
		Look spriteCostume = sprite.getLook();
		
		LookData spriteCostumeData = spriteCostume.getLookData();
		assertEquals(null, spriteCostumeData);
		
		SetLookBrick setLookBrick = new SetLookBrick(spriteName, costumeDataName);
		assertTrue(setLookBrick.execute());
		
		spriteCostumeData = spriteCostume.getLookData();
		assertEquals(costumeDataName, spriteCostumeData.getName());
	}

}
