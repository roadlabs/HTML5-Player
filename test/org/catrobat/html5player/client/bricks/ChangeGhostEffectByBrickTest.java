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

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.formulaeditor.Formula;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeGhostEffectByBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeGhostEffectByBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
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
		stage.getSpriteManager().reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testExecute() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		Formula ghostEffectValue = new Formula(5.0);
		double newAlphaValue = sprite.getLook().getAlphaValue() - (ghostEffectValue.interpretFloat(sprite) / 100.0);
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(newAlphaValue, sprite.getLook().getAlphaValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultNegative() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		Formula ghostEffectValue = new Formula(105.0);
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(0.0, sprite.getLook().getAlphaValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultGreaterThanOne() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		Formula ghostEffectValue = new Formula(-105.0);
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(1.0, sprite.getLook().getAlphaValue());
	}

}
