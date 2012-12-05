
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ChangeYByBrick;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class ChangeYByBrickTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeYByBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
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
		stage.getSpriteManager().reset();
		CatScheduler.get().clear();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testExecuteNegativeChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getCostume().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getCostume().getXPosition();
		
		int deltaY = -5;
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		changeYBrick.execute();
		
		double expected = setYPosition - (double)deltaY;
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
	}
	
	/**
	 * 
	 */
	public void testMultipleExecuteNegativeChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getCostume().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getCostume().getXPosition();
		
		int deltaY = -5;
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		double expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
	}
	
	/**
	 * 
	 */
	public void testExecutePositiveChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getCostume().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getCostume().getXPosition();
		
		int deltaY = 5;
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		changeYBrick.execute();
		
		double expected = setYPosition - (double)deltaY;
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
	}
	
	/**
	 * 
	 */
	public void testMultipleExecutePositiveChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getCostume().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getCostume().getXPosition();
		
		int deltaY = 5;
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		double expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
		
		expected = sprite.getCostume().getYPosition() - (double)deltaY;
		changeYBrick.execute();
		assertEquals(expected, sprite.getCostume().getYPosition());
		assertEquals(costumeXPosition, sprite.getCostume().getXPosition());
	}

}
