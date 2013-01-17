/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Look;
import org.catrobat.html5Player.client.common.LookData;

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
	private Look look;
	
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
		look = sprite.getLook();
		LookData lookData = createCostumeData("look");
		look.setLookData(lookData);
	}
	
	public void gwtTearDown() {
		spriteManager.reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * Helper
	 * @param name of Look
	 * @return LookData for look
	 */
	private LookData createCostumeData(String name) {
		LookData data = new LookData();
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
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
		
		look.setXPosition(x);
		look.setYPosition(y);
		look.setRotation(rotation);
		
		look.getLookData().setWidth(width);
		look.getLookData().setHeight(height);
		
		IfOnEdgeBounceBrick brick = new IfOnEdgeBounceBrick(spriteName);
		brick.execute();
		
		double costumeXPosition = look.getXPosition();
		double costumeYPosition = look.getYPosition();
		double costumeRotation = look.getRotation();
		
		assertEquals((int)x, (int)costumeXPosition);
		assertEquals((int)y, (int)costumeYPosition);
		assertEquals(rotation, costumeRotation);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
