package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class TurnLeftBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
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
		assertEquals("Wrong direction!", 10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());

		turnLeftBrick.execute();
		assertEquals("Wrong direction!", 20d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		assertEquals("Wrong direction!", 10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		assertEquals("Wrong direction!", 10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());

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

		assertEquals("Wrong direction!", -10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		assertEquals("Wrong direction!", 370d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		assertEquals("Wrong direction!", 20d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
	}
}
