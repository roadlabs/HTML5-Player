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

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ChangeXByBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class ChangeXByBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	private int xMovement = 100;


	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeXBySprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getLook().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getLook().getYPosition());

		int xPosition = (int) sprite.getLook().getXPosition();

		ChangeXByBrick changeXByBrick = new ChangeXByBrick(spriteName, xMovement);
		changeXByBrick.execute();

		xPosition += xMovement;
		assertEquals("Incorrect sprite x position after ChangeXByBrick executed", (double) xPosition,
				sprite.getLook().getXPosition());
	}

	public void testNullSprite() {
		ChangeXByBrick changeXByBrick = new ChangeXByBrick(null, xMovement);
		changeXByBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(0);
		canvas.setCoordinateSpaceWidth(0);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeXBySprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		int xPosition = 10;
		sprite.getLook().setXYPosition(xPosition, sprite.getLook().getYPosition());
		ChangeXByBrick changeXByBrick = new ChangeXByBrick(spriteName, Integer.MAX_VALUE);
		changeXByBrick.execute();

		assertEquals("ChangeXByBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int) sprite.getLook().getXPosition());

		xPosition = -10;
		sprite.getLook().setXYPosition(xPosition, sprite.getLook().getYPosition());
		changeXByBrick = new ChangeXByBrick(spriteName, Integer.MIN_VALUE);
		changeXByBrick.execute();

		assertEquals("ChangeXByBrick failed to place Sprite at minimum x integer value", Integer.MIN_VALUE,
				(int) sprite.getLook().getXPosition());

	}
}
