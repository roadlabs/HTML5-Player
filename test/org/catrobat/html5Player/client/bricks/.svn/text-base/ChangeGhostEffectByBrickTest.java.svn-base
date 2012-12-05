package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeGhostEffectByBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeGhostEffectByBrickTest() {
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
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testExecute() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double ghostEffectValue = 5.0;
		double newAlphaValue = sprite.getCostume().getAlphaValue() - (ghostEffectValue / 100.);
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(newAlphaValue, sprite.getCostume().getAlphaValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultNegative() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double ghostEffectValue = 105.0;
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(0.0, sprite.getCostume().getAlphaValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultGreaterThanOne() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double ghostEffectValue = -105.0;
		
		ChangeGhostEffectByBrick changeGhostEffectBrick = new ChangeGhostEffectByBrick(spriteName, ghostEffectValue);
		
		changeGhostEffectBrick.execute();
		
		assertEquals(1.0, sprite.getCostume().getAlphaValue());
	}

}
