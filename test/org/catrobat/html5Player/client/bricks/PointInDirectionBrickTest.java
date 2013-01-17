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
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.PointInDirectionBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class PointInDirectionBrickTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public PointInDirectionBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
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
		spriteManager.reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testPointRight() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 90.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointLeft() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = -90.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointUp() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 0.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointDown() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 180.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testRotateAndPoint() {
		
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double rotation = 42.0;
		sprite.getCostume().setRotation(rotation);
		
		double degrees = 180.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

}
