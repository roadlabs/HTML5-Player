package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.SetSizeToBrick;
import org.catrobat.html5Player.client.bricks.TurnLeftBrick;
import org.catrobat.html5Player.client.bricks.TurnRightBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class TurnRightBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
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
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), 10);

		turnRightBrick.execute();
		assertEquals("Wrong direction", -10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());

		turnRightBrick.execute();
		assertEquals("Wrong direction", -20d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), 10);
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(sprite.getName(), 50);

		turnRightBrick.execute();
		setSizeToBrick.execute();

		assertEquals("Wrong direction", -10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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

		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), 10);
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(sprite.getName(), 50);

		setSizeToBrick.execute();
		turnRightBrick.execute();

		assertEquals("Wrong direction", -10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
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
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), -10);

		turnRightBrick.execute();
		assertEquals("Wrong direction", 10d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());

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
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), 370);

		turnRightBrick.execute();
		assertEquals("Wrong direction", -370d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());

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
	
		TurnRightBrick turnRightBrick = new TurnRightBrick(sprite.getName(), 50);
		TurnLeftBrick turnLeftBrick = new TurnLeftBrick(sprite.getName(), 20);

		turnRightBrick.execute();
		turnLeftBrick.execute();

		assertEquals("Wrong direction!", -30d, sprite.getCostume().getRotation(), 1e-3);
		assertEquals("Wrong X-Position!", 50d, sprite.getCostume().getXPosition());
		assertEquals("Wrong Y-Position!", 50d, sprite.getCostume().getYPosition());
	}
}
