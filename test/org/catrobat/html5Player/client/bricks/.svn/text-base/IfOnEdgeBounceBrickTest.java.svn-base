package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class IfOnEdgeBounceBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	private int sceneWidth = 200;
	private int sceneHeight = 400;
	private int sceneWidthMiddle = sceneWidth / 2;
	private int sceneHeightMiddle = sceneHeight / 2;
	
	private String spriteName = "spriteName";
	private Sprite sprite;
	private Costume costume;
	
	/**
	 * 
	 */
	public IfOnEdgeBounceBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		scene.createScene(sceneWidth, sceneHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
		
		sprite = spriteManager.getSprite(spriteName, true);
		costume = sprite.getCostume();
		CostumeData costumeData = createCostumeData("costume");
		costume.setCostumeData(costumeData);
	}
	
	public void gwtTearDown() {
		spriteManager.reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * Helper
	 * @param name of Costume
	 * @return CostumeData for costume
	 */
	private CostumeData createCostumeData(String name) {
		CostumeData data = new CostumeData();
		data.setName(name);
		return data;
	}
	
	//--------------------------------------------------------------------------
	
	
	//----------------------- BOUNCE NO ROTATION -------------------------------
	
	/**
	 * 
	 */
	public void testBounceOnLeftEdgeNoRotation() {
		double x = -100 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((-sceneWidthMiddle + width/2) + sceneWidthMiddle, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(0.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnRightEdgeNoRotation() {
		double x = 100 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((sceneWidthMiddle-width/2) + sceneWidthMiddle, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(180.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnTopEdgeNoRotation() {
		double x = 0 + sceneWidthMiddle;
		double y = 200 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((sceneHeightMiddle - height/2) + sceneHeightMiddle, (int)costumeYPosition);
		assertEquals(0.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnBottomEdgeNoRotation() {
		double x = 0 + sceneWidthMiddle;
		double y = -200 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((-sceneHeightMiddle + height / 2) + sceneHeightMiddle, (int)costumeYPosition);
		assertEquals(0.0, costumeRotation);
	}
	
	//----------------------- BOUNCE WITH ROTATION -----------------------------
	
	/**
	 * TODO BETTER TESTS
	 */
	
	/**
	 * 
	 */
	public void testBounceOnLeftEdgeRotation180() {
		double x = -100 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 180;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((-sceneWidthMiddle + width/2) + sceneWidthMiddle, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(0.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnRightEdgeRotation180() {
		double x = 100 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 180;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((sceneWidthMiddle-width/2) + sceneWidthMiddle, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(180.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnTopEdgeRotation180() {
		double x = 0 + sceneWidthMiddle;
		double y = 200 + sceneHeightMiddle;
		double rotation = 180;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((sceneHeightMiddle - height/2) + sceneHeightMiddle, (int)costumeYPosition);
		assertEquals(180.0, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testBounceOnBottomEdgeRotation180() {
		double x = 0 + sceneWidthMiddle;
		double y = -200 + sceneHeightMiddle;
		double rotation = 180;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((-sceneHeightMiddle + height / 2) + sceneHeightMiddle, (int)costumeYPosition);
		assertEquals(180.0, costumeRotation);
	}
	
	//---------------------------- NO BOUNCE -----------------------------------
	
	/**
	 * 
	 */
	public void testNoBounceOnLeftEdge() {
		double x = -90 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(rotation, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testNoBounceOnRightEdge() {
		double x = 90 + sceneWidthMiddle;
		double y = 0 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(rotation, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testNoBounceOnTopEdge() {
		double x = 0 + sceneWidthMiddle;
		double y = 185 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(rotation, costumeRotation);
	}
	
	/**
	 * 
	 */
	public void testNoBounceOnBottomEdge() {
		double x = 0 + sceneWidthMiddle;
		double y = -185 + sceneHeightMiddle;
		double rotation = 0;
		
		int width = 20;
		int height = 30;
		
		costume.setXPosition(x);
		costume.setYPosition(y);
		costume.setRotation(rotation);
		
		costume.getCostumeData().setWidth(width);
		costume.getCostumeData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = costume.getXPosition();
		double costumeYPosition = costume.getYPosition();
		double costumeRotation = costume.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(rotation, costumeRotation);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
