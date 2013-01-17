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
import org.catrobat.html5Player.client.bricks.GoNStepsBackBrick;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class GoNStepsBackBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public GoNStepsBackBrickTest() {
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
	 * 
	 */
	public void testExecute() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		int startingZPosition = sprite.getCostume().getZPosition();
		
		int steps = 1;
		
		GoNStepsBackBrick goNStepsBack = new GoNStepsBackBrick(spriteName, steps);
		
		goNStepsBack.execute();
		
		int newZPosition = startingZPosition - steps;
		assertEquals(newZPosition, sprite.getCostume().getZPosition());
	}
	
	/**
	 * 
	 */
	public void testExecuteMinInteger() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		sprite.getCostume().setZPosition(Integer.MIN_VALUE);
		
		int steps = 1;
		
		GoNStepsBackBrick goNStepsBack = new GoNStepsBackBrick(spriteName, steps);
		
		goNStepsBack.execute();
		
		assertEquals(Integer.MIN_VALUE, sprite.getCostume().getZPosition());
	}
	
	/**
	 * 
	 */
	public void testExecuteNegativeStep() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		int startingZPosition = sprite.getCostume().getZPosition();
		
		int steps = -2;
		
		GoNStepsBackBrick goNStepsBack = new GoNStepsBackBrick(spriteName, steps);
		
		assertFalse(goNStepsBack.execute());
		
		assertEquals(startingZPosition, sprite.getCostume().getZPosition());
	}

}
