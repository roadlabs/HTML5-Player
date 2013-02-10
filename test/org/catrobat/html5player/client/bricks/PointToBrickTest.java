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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.Look;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class PointToBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public PointToBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
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
	public void testExecutePointedSpriteIsNull() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 10.0;
		double spriteY = 10.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(0.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteEqualCoordinates() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 10.0;
		double spriteY = 10.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = 10.0;
		double pointedSpriteY = 10.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(0.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteEqualXCoordinatesGreaterYCoordinateOfSprite() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 10.0;
		double spriteY = 100.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = 10.0;
		double pointedSpriteY = 50.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(90.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteEqualXCoordinatesLesserYCoordinateOfSprite() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 10.0;
		double spriteY = 50.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = 10.0;
		double pointedSpriteY = 100.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(-90.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteEqualYCoordinatesGreaterXCoordinateOfSprite() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 10.0;
		double spriteY = 100.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = -10.0;
		double pointedSpriteY = 100.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(180.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteEqualYCoordinatesLesserXCoordinateOfSprite() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 100.0;
		double spriteY = 100.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = 200.0;
		double pointedSpriteY = 100.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(0.0, spriteCostume.getRotation());
	}
	
	/**
	 * 
	 */
	public void testExecuteDifferentCoordinates() {
		String spriteName = "sprite";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		Look spriteCostume = sprite.getLook();
		
		double spriteX = 100.0;
		double spriteY = 100.0;
		
		spriteCostume.setXYPosition(spriteX, spriteY);
		spriteCostume.setRotation(25.0);
		
		String pointedSpriteName = "pointedSprite";
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, true);
		Look pointedSpriteCostume = pointedSprite.getLook();
		
		double pointedSpriteX = 200.0;
		double pointedSpriteY = 100.0;
		
		pointedSpriteCostume.setXYPosition(pointedSpriteX, pointedSpriteY);
		
		
		double base = Math.abs(spriteY - pointedSpriteY);
		double height = Math.abs(spriteX - pointedSpriteX);
		double value = Math.toDegrees(Math.atan(base/height));
		
		double expectedRotation = 0.0;
		
		if(spriteX < pointedSpriteX) {
			if(spriteY > pointedSpriteY) {
				expectedRotation = 90.0 + value;
			}
			else {
				expectedRotation = 90.0 - value;
			}
		}
		else {
			if(spriteY > pointedSpriteY) {
				expectedRotation = 270.0 - value;
			}
			else {
				expectedRotation = 270.0 + value;
			}
		}
		
		expectedRotation = -expectedRotation + 90;
		
		PointToBrick brick = new PointToBrick(spriteName, pointedSpriteName);
		
		brick.execute();
		
		assertEquals(expectedRotation, spriteCostume.getRotation());
	}
}
