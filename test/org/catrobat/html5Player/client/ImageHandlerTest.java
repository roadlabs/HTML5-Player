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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Image;

public class ImageHandlerTest extends GWTTestCase {

	public ImageHandlerTest() {

	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	//--------------------------------------------------------------------------
	
	public void gwtTearDown() {
		ImageHandler.get().dumpAllImages();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testNewImage() {
		
		//if the ImageHandler was used before this test
		ImageHandler.get().dumpAllImages(); 
		
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		int width = 50;
		int height = 100;
		
		assertTrue(ImageHandler.get().newImage(name, url, width, height));
		assertEquals(1, ImageHandler.get().getTotalNumberOfLoadedImages());
	}
	
	/**
	 * 
	 */
	public void testNewImageAlreadyExisting() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		int width = 50;
		int height = 100;
		
		assertTrue(ImageHandler.get().newImage(name, url, width, height));
		assertEquals(1, ImageHandler.get().getTotalNumberOfLoadedImages());
		assertFalse(ImageHandler.get().newImage(name, "", 13, 37));
		assertEquals(1, ImageHandler.get().getTotalNumberOfLoadedImages());
	}
	
	/**
	 * 
	 */
	public void testGetImage() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		int width = 50;
		int height = 100;
		
		ImageHandler.get().newImage(name, url, width, height);
		
		Image image = ImageHandler.get().getImage(name);
		assertNotNull(image);
		assertEquals(url, image.getUrl());
	}
	
	/**
	 * 
	 */
	public void testGetImageNotAvailable() {
		assertNull(ImageHandler.get().getImage("anything"));
	}
	
	/**
	 * 
	 */
	public void testAddImage() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		ImageHandler.get().addImage(name, url);
		
		Image image = ImageHandler.get().getImage(name);
		assertNull(image);
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
	}
	
	/**
	 * 
	 */
	public void testLoadImages() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		String name2 = "TestImage2";
		String url2 = Const.PROJECT_PATH + "410" + "/images/FE5DF421A5746EC7FC916AC1B94ECC17_Banzai-Katze";
		
		ImageHandler.get().addImage(name, url);
		ImageHandler.get().addImage(name2, url2);
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().loadImages();
		
		assertEquals(2, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		Image image = ImageHandler.get().getImage(name);
		assertNotNull(image);
		assertEquals(url, image.getUrl());
	}
	

	/**
	 * 
	 */
	public void testDumpNotLoadedImages() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		String name2 = "TestImage2";
		String url2 = Const.PROJECT_PATH + "410" + "/images/FE5DF421A5746EC7FC916AC1B94ECC17_Banzai-Katze";
		
		ImageHandler.get().addImage(name, url);
		ImageHandler.get().addImage(name2, url2);
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().dumpNotLoadedImages();
		ImageHandler.get().loadImages();
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
	}
	
	/**
	 * 
	 */
	public void testDumpNotLoadedImagesAndLoadImages() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		String name2 = "TestImage2";
		String url2 = Const.PROJECT_PATH + "410" + "/images/FE5DF421A5746EC7FC916AC1B94ECC17_Banzai-Katze";
		
		ImageHandler.get().addImage(name, url);
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().loadImages();
		
		assertEquals(1, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().addImage(name2, url2);
		
		ImageHandler.get().dumpNotLoadedImages();
		
		assertEquals(1, ImageHandler.get().getTotalNumberOfLoadedImages());
	}
	
	/**
	 * 
	 */
	public void testDumpLoadedImages() {
		String name = "TestImage";
		String url = Const.PROJECT_PATH + "410" + "/images/143780EBC24495149123CCAF3A1CDC35_Katze normal";
		
		String name2 = "TestImage2";
		String url2 = Const.PROJECT_PATH + "410" + "/images/FE5DF421A5746EC7FC916AC1B94ECC17_Banzai-Katze";
		
		ImageHandler.get().addImage(name, url);
		ImageHandler.get().addImage(name2, url2);
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().loadImages();
		
		assertEquals(2, ImageHandler.get().getTotalNumberOfLoadedImages());
		
		ImageHandler.get().dumpLoadedImages();
		
		assertEquals(0, ImageHandler.get().getTotalNumberOfLoadedImages());
	}

}
