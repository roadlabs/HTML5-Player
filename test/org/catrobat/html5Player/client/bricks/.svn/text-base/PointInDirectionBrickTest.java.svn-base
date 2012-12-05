package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.PointInDirectionBrick;

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
	public void testPointRight() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 90.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointLeft() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = -90.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointUp() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 0.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testPointDown() {
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double degrees = 180.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

	/**
	 * 
	 */
	public void testRotateAndPoint() {
		
		String spriteName = "testPointInDirectionSprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double rotation = 42.0;
		sprite.getCostume().setRotation(rotation);
		
		double degrees = 180.0;
		PointInDirectionBrick pointInDirectionBrick = new PointInDirectionBrick(spriteName, 0, degrees);

		pointInDirectionBrick.execute();
		
		double expectation = -degrees + 90.0;
		
		assertEquals(expectation, sprite.getCostume().getRotation());
	}

}
