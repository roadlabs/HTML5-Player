
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ChangeXByBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class ChangeXByBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	private int xMovement = 100;


	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeXBySprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getCostume().getYPosition());

		int xPosition = (int) sprite.getCostume().getXPosition();

		ChangeXByBrick changeXByBrick = new ChangeXByBrick(spriteName, xMovement);
		changeXByBrick.execute();

		xPosition += xMovement;
		assertEquals("Incorrect sprite x position after ChangeXByBrick executed", (double) xPosition,
				sprite.getCostume().getXPosition());
	}

	public void testNullSprite() {
		ChangeXByBrick changeXByBrick = new ChangeXByBrick(null, xMovement);
		changeXByBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(0);
		canvas.setCoordinateSpaceWidth(0);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeXBySprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		int xPosition = 10;
		sprite.getCostume().setXYPosition(xPosition, sprite.getCostume().getYPosition());
		ChangeXByBrick changeXByBrick = new ChangeXByBrick(spriteName, Integer.MAX_VALUE);
		changeXByBrick.execute();

		assertEquals("ChangeXByBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int) sprite.getCostume().getXPosition());

		xPosition = -10;
		sprite.getCostume().setXYPosition(xPosition, sprite.getCostume().getYPosition());
		changeXByBrick = new ChangeXByBrick(spriteName, Integer.MIN_VALUE);
		changeXByBrick.execute();

		assertEquals("ChangeXByBrick failed to place Sprite at minimum x integer value", Integer.MIN_VALUE,
				(int) sprite.getCostume().getXPosition());

	}
}
