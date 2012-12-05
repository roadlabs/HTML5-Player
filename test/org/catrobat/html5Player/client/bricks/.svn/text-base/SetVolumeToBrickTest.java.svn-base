package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.SetVolumeToBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetVolumeToBrickTest extends GWTTestCase{

	private Stage stage;
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public SetVolumeToBrickTest() {
		stage = Stage.getInstance();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
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
	public void testExecute() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double setVolumeTo = 50.0;
		
		SetVolumeToBrick brick = new SetVolumeToBrick(spriteName, setVolumeTo);
		brick.execute();
		
		assertEquals(setVolumeTo, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeUpGreaterThanMax() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double setVolumeTo = 120.0;
		
		SetVolumeToBrick brick = new SetVolumeToBrick(spriteName, setVolumeTo);
		brick.execute();
		
		assertEquals(100.0, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeDownLesserThanMin() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double setVolumeTo = -10.0;
		
		SetVolumeToBrick brick = new SetVolumeToBrick(spriteName, setVolumeTo);
		brick.execute();
		
		assertEquals(0.0, sprite.getVolume());
	}
}
