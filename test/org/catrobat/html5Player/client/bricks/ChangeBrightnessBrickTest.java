package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeBrightnessBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeBrightnessBrickTest() {
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
	public void testExecuteTurnBrightnessUp() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = 50.0;
		double newBrightnessValue = sprite.getCostume().getBrightnessValue() + (brightness / 100.);
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(newBrightnessValue, sprite.getCostume().getBrightnessValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteTurnBrightnessDown() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = -50.0;
		double newBrightnessValue = sprite.getCostume().getBrightnessValue() + (brightness / 100.);
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(newBrightnessValue, sprite.getCostume().getBrightnessValue());
	}
	
	/**
	 * 
	 */
	public void testExecuteResultNegative() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double brightness = -200.0;
		
		ChangeBrightnessBrick changeBrightnessBrick = new ChangeBrightnessBrick(spriteName, brightness);
		
		changeBrightnessBrick.execute();
		
		assertEquals(0.0, sprite.getCostume().getBrightnessValue());
	}

}
