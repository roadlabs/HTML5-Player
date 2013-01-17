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
import org.catrobat.html5Player.client.bricks.HideBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class HideBrickTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}

	public void testHide() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testHideSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertTrue("Unexpected default visibility", sprite.getCostume().isVisible());
		HideBrick hideBrick = new HideBrick(spriteName);
		hideBrick.execute();
		assertFalse("Sprite is still visible after HideBrick executed", sprite.getCostume().isVisible());
	}

	public void testNullSprite() {
		HideBrick hideBrick = new HideBrick(null);
		hideBrick.execute();
	}
}
