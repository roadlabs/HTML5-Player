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
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;



public class SpeakBrickTest extends GWTTestCase{

	private Stage stage;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;

	private CatScheduler scheduler;

	public SpeakBrickTest() {
		stage = Stage.getInstance();
		spriteManager = stage.getSpriteManager();
		scheduler = CatScheduler.get();
	}

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
		stage.setCanvas(canvas);
	}

	public void gwtTearDown() {
		stage.setCanvas(null);
		spriteManager.reset();
		scheduler.clear();
	}

	//--------------------------------------------------------------------------

	/**
	 *
	 */
	public void testNewSpeakBrick() {

		String spriteName = "spriteName";
		String toSpeak = "hello world";

		@SuppressWarnings("unused")
		Sprite sprite = spriteManager.getSprite(spriteName, true);

		SpeakBrick speakBrick = new SpeakBrick(spriteName, toSpeak);

		assertEquals(toSpeak, speakBrick.getText());
	}

	/**
	 *
	 */
	public void testExecute() {

		String text = "hello world 2";
		String spriteName = "spriteName";

		@SuppressWarnings("unused")
		Sprite sprite = spriteManager.getSprite(spriteName, true);

		SpeakBrick speakBrick = new SpeakBrick(spriteName, text);

		assertTrue(speakBrick.execute());
	}


}
