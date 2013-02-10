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
import org.catrobat.html5player.client.Stage;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class TurnLeftBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	public void testTurnLeftTwice() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(spriteName, 10);

		turnLeftBrick.execute();
		assertEquals("Wrong direction!", 10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());

		turnLeftBrick.execute();
		assertEquals("Wrong direction!", 20d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testTurnLeftAndScale() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(spriteName, 10);
		SetSizeToBrick brickScale = new SetSizeToBrick(spriteName, 50);

		turnLeftBrick.execute();
		brickScale.execute();

		assertEquals("Wrong direction!", 10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testScaleAndTurnLeft() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite2");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(spriteName, 10);
		SetSizeToBrick brickScale = new SetSizeToBrick(spriteName, 50);

		brickScale.execute();
		turnLeftBrick.execute();

		assertEquals("Wrong direction!", 10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());

	}

	public void testTurnLeftNegative() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite3");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(spriteName, -10);

		turnLeftBrick.execute();

		assertEquals("Wrong direction!", -10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testTurnLeft() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite4");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(spriteName, 370);

		turnLeftBrick.execute();

		assertEquals("Wrong direction!", 370d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testTurnLeftAndTurnRight() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnLeftSprite5");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnLeftBrick brickTurnLeft = new TurnLeftBrick(spriteName, 50);
		TurnRightBrick brickTurnRight = new TurnRightBrick(spriteName, 30);

		brickTurnLeft.execute();
		brickTurnRight.execute();

		assertEquals("Wrong direction!", 20d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}
}
