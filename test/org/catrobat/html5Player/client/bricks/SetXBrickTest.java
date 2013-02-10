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

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.SetXBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetXBrickTest extends GWTTestCase {

	private int xPosition = 100;
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	public void testNormalBehavior() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetXSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite x position", 50d, sprite.getLook().getXPosition());
		assertEquals("Unexpected initial sprite y position", 50d, sprite.getLook().getYPosition());

		SetXBrick setXBrick = new SetXBrick(spriteName, xPosition);
		setXBrick.execute();

		assertEquals("Incorrect sprite x position after SetXBrick executed", (double) xPosition,
				sprite.getLook().getXPosition()-50);
	}

	public void testNullSprite() {
		SetXBrick setXBrick = new SetXBrick(null, xPosition);
		setXBrick.execute();
	}

	public void testBoundaryPositions() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetXSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);

		SetXBrick setXBrick = new SetXBrick(spriteName, Integer.MAX_VALUE);
		setXBrick.execute();

		assertEquals("SetXBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int) sprite.getLook().getXPosition());

		setXBrick = new SetXBrick(spriteName, Integer.MIN_VALUE);
		setXBrick.execute();

		assertEquals("SetXBrick failed to place Sprite at minimum x integer value", -(Integer.MIN_VALUE-50),
				(int) sprite.getLook().getXPosition());
	}
}
