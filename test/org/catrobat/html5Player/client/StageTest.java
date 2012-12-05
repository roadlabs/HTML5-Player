package org.catrobat.html5Player.client;

import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.TextArea;

public class StageTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public StageTest() {
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
		stage.setProjectNumber(null);
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testGetInstance() {
		SpriteManager spriteManager = stage.getSpriteManager();
		MessageContainer messageContainer = stage.getMessageContainer();
	
		assertTrue(spriteManager instanceof SpriteManager);
		assertTrue(messageContainer instanceof MessageContainer);
		
		assertNull("ProjectNumber already set", stage.getProjectNumber());
	}
	
	/**
	 * 
	 */
	public void testCanvas() {
		
		int canvasHeight = 250;
		int canvasWidth = 100;
		int canvasMiddleHeight = canvasHeight / 2;
		int canvasMiddleWidth = canvasWidth / 2;
		
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(canvasHeight);
		canvas.setCoordinateSpaceWidth(canvasWidth);
		stage.setCanvas(canvas);

		Canvas rootCanvas = stage.getCanvas();
		
		assertTrue("Canvas not the same which got set", canvas.equals(rootCanvas));
		
		assertEquals("Wrong middle x", canvasMiddleWidth, stage.getStageMiddleX());
		assertEquals("Wrong middle y", canvasMiddleHeight, stage.getStageMiddleY());
	}
	
	/**
	 * 
	 */
	public void testProjectNumber() {
		String projectNumber = "411";
		stage.setProjectNumber(projectNumber);
		assertEquals(projectNumber, stage.getProjectNumber());
	}
	
	/**
	 * 
	 */
	public void testLogBox() {
		
		TextArea logBox = new TextArea();
		logBox.setSize("400px", "10px");
		logBox.setVisible(false);
		
		assertEquals("", logBox.getText());
		
		stage.setLogBox(logBox);
		
		stage.log(null);
		assertEquals("", logBox.getText());
		
		String logMessage = "test log message";
		stage.log(logMessage);
		
		assertEquals(logMessage, logBox.getText());
		
		String logMessage2 = "another test log message";
		stage.log(logMessage2);
		
		assertEquals(logMessage + "\n" + logMessage2, logBox.getText());
	}
	
	/**
	 * 
	 */
	public void testClearStageLoxBox() {

		TextArea logBox = new TextArea();
		stage.setLogBox(logBox);
		stage.defaultLogBoxSettings();
		
		String logMessage = "test log message";
		stage.log(logMessage);
		
		String logMessage2 = "another test log message";
		stage.log(logMessage2);
		
		stage.clearStage();
		
		assertEquals("", logBox.getText());
	}
	
	/**
	 * 
	 */
	public void testClearStage() {
		
		String spriteName = "sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "script";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		sprite.addScript(startScript);
		
		sprite.run();
		
		ImageHandler imageHandler = ImageHandler.get();
		imageHandler.addImage("image", "images/ajax-loader.gif"); //TODO better solution
		imageHandler.loadImages();
		imageHandler.addImage("image2", "");
		
		stage.clearStage();
		
		assertEquals(0, CatScheduler.get().getThreadCount());
		assertEquals(0, imageHandler.getTotalNumberOfLoadedImages());
		assertEquals(0, stage.getSpriteManager().getSpriteList().size());
	}
	
//	/**
//	 * 
//	 */
//	public void test() {
//		Stage stage = Stage.getInstance();
//		stage.start("");
//	}
	
}
