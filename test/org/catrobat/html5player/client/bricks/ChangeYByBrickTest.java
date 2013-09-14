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
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.ChangeYByBrick;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class ChangeYByBrickTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public ChangeYByBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
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
		stage.getSpriteManager().reset();
		CatScheduler.get().clear();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testExecuteNegativeChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getLook().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getLook().getXPosition();
		
		Formula deltaY = new Formula(-5);
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		changeYBrick.execute();
		
		double expected = setYPosition - (double)deltaY.interpretFloat(sprite);
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
	}
	
	/**
	 * 
	 */
	public void testMultipleExecuteNegativeChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getLook().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getLook().getXPosition();
		
		Formula deltaY = new Formula(-5);
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		double expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
	}
	
	/**
	 * 
	 */
	public void testExecutePositiveChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getLook().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getLook().getXPosition();
		
		Formula deltaY = new Formula(5);
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		changeYBrick.execute();
		
		double expected = setYPosition - (double)deltaY.interpretFloat(sprite);
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
	}
	
	public void testMultipleExecutePositiveChange() {
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		double setYPosition = -250.0;
		sprite.getLook().setYPosition(setYPosition);
		
		double costumeXPosition = sprite.getLook().getXPosition();
		
		Formula deltaY = new Formula(5);
		
		ChangeYByBrick changeYBrick = new ChangeYByBrick(spriteName, deltaY);
		
		double expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
		
		expected = sprite.getLook().getYPosition() - (double)deltaY.interpretFloat(sprite);
		changeYBrick.execute();
		assertEquals(expected, sprite.getLook().getYPosition());
		assertEquals(costumeXPosition, sprite.getLook().getXPosition());
	}

}
