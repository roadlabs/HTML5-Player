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
import org.catrobat.html5player.client.bricks.SetSizeToBrick;
import org.catrobat.html5player.client.formulaeditor.Formula;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetSizeToBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	private Formula size = new Formula(70);


	public void testSize() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetSizeToSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite size value", 1d, sprite.getLook().getSize());
	
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(spriteName, size);
		setSizeToBrick.execute();
		assertEquals("Incorrect sprite size value after SetSizeToBrick executed", (double) size.interpretFloat(sprite) / 100,
				sprite.getLook().getSize());
	}

	public void testNullSprite() {
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(null, size);
		setSizeToBrick.execute();
	}

}
