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
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.PointInDirectionBrick;
import org.catrobat.html5player.client.formulaeditor.Formula;

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
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testPointRight() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		Formula degrees = new Formula(90.0);
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees.interpretFloat(sprite) + 90.0;
		
		assertEquals(expectation, sprite.getLook().getRotation());
	}

	/**
	 * 
	 */
	public void testPointLeft() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		Formula degrees = new Formula(-90.0);
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees.interpretFloat(sprite) + 90.0;
		
		assertEquals(expectation, sprite.getLook().getRotation());
	}

	/**
	 * 
	 */
	public void testPointUp() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		Formula degrees = new Formula(0.0);
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees.interpretFloat(sprite) + 90.0;
		
		assertEquals(expectation, sprite.getLook().getRotation());
	}

	/**
	 * 
	 */
	public void testPointDown() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		Formula degrees = new Formula(180.0);
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees.interpretFloat(sprite) + 90.0;
		
		assertEquals(expectation, sprite.getLook().getRotation());
	}

	/**
	 * 
	 */
	public void testRotateAndPoint() {
		
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double rotation = 42.0;
		sprite.getLook().setRotation(rotation);
		
		Formula degrees = new Formula(180.0);
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees.interpretFloat(sprite) + 90.0;
		
		assertEquals(expectation, sprite.getLook().getRotation());
	}

}
