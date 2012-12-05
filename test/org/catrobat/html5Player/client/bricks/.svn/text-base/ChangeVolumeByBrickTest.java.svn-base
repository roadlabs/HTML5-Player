package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ChangeVolumeByBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeVolumeByBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public ChangeVolumeByBrickTest() {
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
	public void testExecuteTurnVolumeUp() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		double changeVolumeBy = 10.0;
		
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(volume + changeVolumeBy, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeDown() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		double changeVolumeBy = -10.0;
		
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(volume + changeVolumeBy, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeUpGreaterThanMax() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		double changeVolumeBy = 30.0;
		
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(100.0, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeDownLesserThanMin() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 30.0;
		
		sprite.setVolume(volume);
		
		double changeVolumeBy = -40.0;
		
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(0.0, sprite.getVolume());
	}
}
