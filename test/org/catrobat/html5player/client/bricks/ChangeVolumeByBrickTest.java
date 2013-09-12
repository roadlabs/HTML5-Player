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
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.ChangeVolumeByBrick;
import org.catrobat.html5player.client.formulaeditor.Formula;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ChangeVolumeByBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager spriteManager;
	
	/**
	 * 
	 */
	public ChangeVolumeByBrickTest() {
		stage = Stage.getInstance();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
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
	public void testExecuteTurnVolumeUp() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		//double changeVolumeBy = 10.0;
		Formula changeVolumeBy = new Formula(10.0);
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(volume + changeVolumeBy.interpretFloat(sprite), sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeDown() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		//double changeVolumeBy = -10.0;
		Formula changeVolumeBy = new Formula(-10.0);
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(volume + changeVolumeBy.interpretFloat(sprite), sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeUpGreaterThanMax() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 80.0;
		
		sprite.setVolume(volume);
		
		//double changeVolumeBy = 30.0;
		Formula changeVolumeBy = new Formula(30.0);
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(100.0, sprite.getVolume());
	}
	
	/**
	 *
	 */
	public void testExecuteTurnVolumeDownLesserThanMin() {
		String spriteName = "spriteName";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		double volume = 30.0;
		
		sprite.setVolume(volume);
		
		//double changeVolumeBy = -40.0;
		Formula changeVolumeBy = new Formula(-40.0);
		ChangeVolumeByBrick brick = new ChangeVolumeByBrick(spriteName, changeVolumeBy);
		brick.execute();
		
		assertEquals(0.0, sprite.getVolume());
	}
}
