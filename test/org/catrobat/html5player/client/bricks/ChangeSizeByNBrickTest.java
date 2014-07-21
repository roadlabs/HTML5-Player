/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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
import org.catrobat.html5player.client.formulaeditor.Formula;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeSizeByNBrickTest extends GWTTestCase{
	
	Formula bigger = new Formula(20);
	Formula smaller = new Formula(-30);
	
	Formula veryBig = new Formula(20);
	Formula negative = new Formula(-230);
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void testNormalBehaviour(){
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(0);
		canvas.setCoordinateSpaceWidth(0);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeSizeByNSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("ChangeSizeByNBrick initial value wrong", 1,
				(int) sprite.getLook().getSize());
		
		ChangeSizeByNBrick changeBrick = new ChangeSizeByNBrick(spriteName, bigger);
		changeBrick.execute();
		
		assertEquals("ChangeSizeByNBrick initial value wrong", 1.2,
				 sprite.getLook().getSize());
	}
	
	public void testNullSprite() {
		ChangeSizeByNBrick changeSizeByNBrick = new ChangeSizeByNBrick(null, bigger);
		changeSizeByNBrick.execute();
	}

}
