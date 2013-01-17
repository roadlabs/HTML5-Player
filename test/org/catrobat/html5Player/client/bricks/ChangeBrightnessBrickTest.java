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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeBrightnessBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeBrightnessBrickTest() {
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
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testExecuteTurnBrightnessUp() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = 50.0;
		double newBrightnessValue = sprite.getLook().getBrightnessValue() + (brightness / 100.);
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(newBrightnessValue, sprite.getLook().getBrightnessValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteTurnBrightnessDown() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = -50.0;
		double newBrightnessValue = sprite.getLook().getBrightnessValue() + (brightness / 100.);
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(newBrightnessValue, sprite.getLook().getBrightnessValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultNegative() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = -200.0;
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(0.0, sprite.getLook().getBrightnessValue());
	}

}
