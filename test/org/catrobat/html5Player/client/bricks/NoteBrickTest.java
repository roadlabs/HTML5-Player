package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;



public class NoteBrickTest extends GWTTestCase{
	
	private Stage stage;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private CatScheduler scheduler;
	
	public NoteBrickTest() {
		stage = Stage.getInstance();
		spriteManager = stage.getSpriteManager();
		scheduler = CatScheduler.get();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		spriteManager.reset();
		scheduler.clear();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testNewNoteBrick() {
		
		String spriteName = "spriteName";
		
		@SuppressWarnings("unused")
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		NoteBrick noteBrick = new NoteBrick(spriteName);
		
		assertEquals("", noteBrick.getNote());
	}
	
	/**
	 * 
	 */
	public void testNewNoteBrick2() {
		
		String note = "Correct";
		String spriteName = "spriteName";
		
		@SuppressWarnings("unused")
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		NoteBrick noteBrick = new NoteBrick(spriteName, note);
		
		assertEquals(note, noteBrick.getNote());
	}
	
	/**
	 * 
	 */
	public void testExecute() {
		
		String note = "Correct";
		String spriteName = "spriteName";
		
		@SuppressWarnings("unused")
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		NoteBrick noteBrick = new NoteBrick(spriteName, note);
		
		assertTrue(noteBrick.execute());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
