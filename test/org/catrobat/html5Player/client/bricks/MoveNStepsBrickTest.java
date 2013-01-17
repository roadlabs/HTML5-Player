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
import org.catrobat.html5Player.client.bricks.MoveNStepsBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class MoveNStepsBrickTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public MoveNStepsBrickTest() {
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
	public void testMoveHorizontalForward() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 60d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 50d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 70d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 50d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveHorizontalBackward() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, -10);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 40d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 50d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 30d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 50d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveVerticalUp() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(90);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 40d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 30d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveVerticalDown() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(-90);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 60d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 70d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveDiagonalRightUp() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(45);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 57d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 43d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 64d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 36d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveDiagonalLeftUp() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(135);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 43d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 43d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 36d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 36d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveDiagonalRightDown() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(-45);

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 57d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 57d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 64d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 64d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testMoveDiagonalLeftDown() {

		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(-135);
		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 43d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 57d, sprite.getCostume().getYPosition());

		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 36d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 64d, sprite.getCostume().getYPosition());
	}

	/**
	 * 
	 */
	public void testRotateMoveRotateMove() {
		String spriteName = "testMoveNStepsSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		MoveNStepsBrick moveNStepsBrick = new MoveNStepsBrick(spriteName, 10);

		sprite.getCostume().setRotation(10);
		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 60d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 48d, sprite.getCostume().getYPosition());

		sprite.getCostume().setRotation(50);
		moveNStepsBrick.execute();
		assertEquals("Wrong x-position", 66d, sprite.getCostume().getXPosition());
		assertEquals("Wrong y-position", 40d, sprite.getCostume().getYPosition());

	}

}

