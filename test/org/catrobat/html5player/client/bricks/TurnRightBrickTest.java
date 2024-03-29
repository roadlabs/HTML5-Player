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
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.SetSizeToBrick;
import org.catrobat.html5player.client.bricks.TurnLeftBrick;
import org.catrobat.html5player.client.bricks.TurnRightBrick;
import org.catrobat.html5player.client.formulaeditor.Formula;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class TurnRightBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void testTurnRightTwice() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(10));

		turnRightBrick.execute();
		assertEquals("Wrong direction", -10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());

		turnRightBrick.execute();
		assertEquals("Wrong direction", -20d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testTurnRightAndScale() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(10));
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(sprite.getName(), new Formula(50));

		turnRightBrick.execute();
		setSizeToBrick.execute();

		assertEquals("Wrong direction", -10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testScaleandTurnRight() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite2");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(10));
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(sprite.getName(), new Formula(50));

		setSizeToBrick.execute();
		turnRightBrick.execute();

		assertEquals("Wrong direction", -10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}

	public void testTurnRightNegative() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite3");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(-10));

		turnRightBrick.execute();
		assertEquals("Wrong direction", 10d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());

	}

	public void testTurnRight() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite4");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(370));

		turnRightBrick.execute();
		assertEquals("Wrong direction", -370d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());

	}

	public void testTurnRightAndTurnLeft() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testTurnRightSprite5");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), new Formula(50));
		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(sprite.getName(), new Formula(20));

		turnRightBrick.execute();
		turnLeftBrick.execute();

		assertEquals("Wrong direction!", -30d, sprite.getLook().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getLook().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getLook().getYPosition());
	}
}
