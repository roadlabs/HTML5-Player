package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.SetYBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetYBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	private int yPosition = 100;

	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetYSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getCostume().getYPosition());

		SetYBrick setYBrick = new SetYBrick(spriteName, yPosition);
		setYBrick.execute();

		assertEquals("Incorrect sprite y position after SetYBrick executed", (double) yPosition,
				-sprite.getCostume().getYPosition()+50);
	}

	public void testNullSprite() {
		SetYBrick setYBrick = new SetYBrick(null, yPosition);
		setYBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetYSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		SetYBrick setYBrick = new SetYBrick(spriteName, Integer.MAX_VALUE);
		setYBrick.execute();

		assertEquals("SetYBrick failed to place Sprite at maximum y integer value", -(Integer.MAX_VALUE-50),
				(int) sprite.getCostume().getYPosition());

		setYBrick = new SetYBrick(spriteName, Integer.MIN_VALUE);
		setYBrick.execute();

		assertEquals("SetYBrick failed to place Sprite at minimum y integer value", Integer.MAX_VALUE,
				(int) sprite.getCostume().getYPosition());
	}
}
