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
package org.catrobat.html5Player.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Image;

public class SceneTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void gwtTearDown() {
		Scene.get().reset();
		ImageHandler.get().dumpAllImages();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testCreateScene() {
		
		//so a new scene can be created
		Scene.get().reset(); 
		
		assertTrue(Scene.get().createScene());
		
		assertEquals(0, Scene.get().getSceneWidth());
		assertEquals(0, Scene.get().getSceneHeight());
	}

	/**
	 * 
	 */
	public void testSetSceneMeasures() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		
		assertEquals(sceneWidth, Scene.get().getSceneWidth());
		assertEquals(sceneHeight, Scene.get().getSceneHeight());
	}
	
	/**
	 * 
	 */
	public void testCreateSceneWithMeasures() {
		
		int sceneWidth = 150;
		int sceneHeight = 230;
		
		assertTrue(Scene.get().createScene(sceneWidth, sceneHeight));
	
		assertEquals(sceneWidth, Scene.get().getSceneWidth());
		assertEquals(sceneHeight, Scene.get().getSceneHeight());
	}
	
	/**
	 * 
	 */
	public void testCreateSceneIfCreated() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		assertTrue(Scene.get().createScene(sceneWidth, sceneHeight));
	
		assertEquals(sceneWidth, Scene.get().getSceneWidth());
		assertEquals(sceneHeight, Scene.get().getSceneHeight());
	}
	
	/**
	 * 
	 */
	public void testSetSceneMeasuresIfCreated() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		int newSceneWidth = 300;
		int newSceneHeight = 300;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		
		assertEquals(sceneWidth, Scene.get().getSceneWidth());
		assertEquals(sceneHeight, Scene.get().getSceneHeight());
		
		Scene.get().setSceneMeasures(newSceneWidth, newSceneHeight);
	
		assertEquals(newSceneWidth, Scene.get().getSceneWidth());
		assertEquals(newSceneHeight, Scene.get().getSceneHeight());
		
	}
	
	/**
	 * 
	 */
	public void testGetCanvas() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		
		Canvas canvas = Scene.get().getCanvas();
		
		assertEquals(sceneWidth, canvas.getCoordinateSpaceWidth());
		assertEquals(sceneHeight, canvas.getCoordinateSpaceHeight());
	}
	
	/**
	 * 
	 */
	public void testDrawImage() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		
		String imageName = "testImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		ImageHandler.get().newImage(imageName, url, 0, 0);
		Image image = ImageHandler.get().getImage(imageName);
		Scene.get().drawImage(image, 10, 10, image.getWidth(), image.getHeight(), 1);
	}
	
	/**
	 * TODO: Test does not work, because transformations are applied to the 
	 * 		 context in the drawImage() method
	 */
	public void testDrawImage2() {
		
		int sceneWidth = 200;
		int sceneHeight = 400;
		
		assertTrue(Scene.get().createScene());
		Scene.get().setSceneMeasures(sceneWidth, sceneHeight);
		
		String imageName = "testImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		ImageHandler.get().newImage(imageName, url, 0, 0);
		Image image = ImageHandler.get().getImage(imageName);
		
//		Scene.get().drawImage(image, 10, 10, 10, 10, image.getWidth(), image.getHeight(), 0, 1, 1, 1);
	}
	
}
