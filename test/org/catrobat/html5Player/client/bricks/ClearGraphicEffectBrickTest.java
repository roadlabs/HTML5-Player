package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ClearGraphicEffectBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ClearGraphicEffectBrickTest() {
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
		Costume costume = sprite.getCostume();
		
		costume.setAlphaValue(0.3);
		costume.setBrightnessValue(0.3);
		
		ClearGraphicEffectBrick brick = new ClearGraphicEffectBrick(spriteName);
		
		brick.execute();
		
		assertEquals(1.0, costume.getAlphaValue());
		assertEquals(1.0, costume.getBrightnessValue());
	}

}
