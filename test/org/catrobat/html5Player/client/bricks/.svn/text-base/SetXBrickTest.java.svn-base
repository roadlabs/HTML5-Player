package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.SetXBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetXBrickTest extends GWTTestCase {

	private int xPosition = 100;
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}

	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetXSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getCostume().getYPosition());

		SetXBrick setXBrick = new SetXBrick(spriteName, xPosition);
		setXBrick.execute();

		assertEquals("Incorrect sprite x position after SetXBrick executed", (double) xPosition,
				sprite.getCostume().getXPosition()-50);
	}

	public void testNullSprite() {
		SetXBrick setXBrick = new SetXBrick(null, xPosition);
		setXBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetXSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		SetXBrick setXBrick = new SetXBrick(spriteName, Integer.MAX_VALUE);
		setXBrick.execute();

		assertEquals("SetXBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int) sprite.getCostume().getXPosition());

		setXBrick = new SetXBrick(spriteName, Integer.MIN_VALUE);
		setXBrick.execute();

		assertEquals("SetXBrick failed to place Sprite at minimum x integer value", -(Integer.MIN_VALUE-50),
				(int) sprite.getCostume().getXPosition());
	}
}
