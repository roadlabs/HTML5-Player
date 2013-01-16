
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.PlaceAtBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class PlaceAtBrickTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}

	private int xPosition = 100;
	private int yPosition = 200;

	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testPlaceAtSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getCostume().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getCostume().getYPosition());

		PlaceAtBrick brick = new PlaceAtBrick(spriteName, xPosition, yPosition);
		brick.execute();

		//TODO: refactor this
		assertEquals("Incorrect sprite x position after PlaceAtBrick executed", xPosition,
				(int) sprite.getCostume().getXPosition()-50);
		assertEquals("Incorrect sprite y position after PlaceAtBrick executed", yPosition,
				(int) -(sprite.getCostume().getYPosition()-50));
	}

	public void testNullSprite() {
		PlaceAtBrick placeAtBrick = new PlaceAtBrick(null, xPosition, yPosition);
		placeAtBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testPlaceAtSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		PlaceAtBrick placeAtBrick = new PlaceAtBrick(spriteName, Integer.MAX_VALUE, Integer.MIN_VALUE);
		placeAtBrick.execute();

		assertEquals("PlaceAtBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int)sprite.getCostume().getXPosition());
		assertEquals("PlaceAtBrick failed to place Sprite at maximum y integer value", Integer.MAX_VALUE,
				(int) sprite.getCostume().getYPosition());

		placeAtBrick = new PlaceAtBrick(spriteName, Integer.MIN_VALUE, Integer.MIN_VALUE);
		placeAtBrick.execute();

		assertEquals("PlaceAtBrick failed to place Sprite at minimum x integer value", Integer.MIN_VALUE+50,
				(int) sprite.getCostume().getXPosition());
		assertEquals("PlaceAtBrick failed to place Sprite at minimum y integer value", Integer.MAX_VALUE,
				(int) sprite.getCostume().getYPosition());
	}
}