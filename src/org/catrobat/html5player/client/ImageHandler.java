/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */ package org.catrobat.html5player.client;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ImageHandler {
	
	private static ImageHandler instance = null;
	
	private HashMap<String, Image> imagesMap;
	private HashMap<String, String> imagesToCreateMap;
	
	//not visible panel to fire the load event for the images
	private VerticalPanel imagePanel;
	
	//to check if images have been loaded
	private boolean imagesLoaded = false;
	private int imagesToLoad = 0;
	private int numberImagesLoaded = 0;
	
	//to check if an image failed loading
	private boolean loadingFailed = false;
	
	//##########################################################################
	
	private ImageHandler() {
		
		imagesToCreateMap = new HashMap<String, String>();
		
		imagesMap = new HashMap<String, Image>();
		
		imagePanel = new VerticalPanel();
		imagePanel.setVisible(false);
		addImagePanel();
	}
	
	/**
	 *
	 */
	public static ImageHandler get() {
		if(instance == null) {
			instance = new ImageHandler();
		}
		
		return instance;
	}
	
	//##########################################################################
	
	/**
	 * @TODO: private nicht public, muss auch nicht mehr boolean sein
	 * 
	 * Adds a new image with a given url and name to the handler
	 * @param name
	 * @param url
	 * @param width 
	 * @param height
	 * @return false if the name is already in use, true otherwise
	 */
	public boolean newImage(String name, String url, int width, int height) {
		
		//Image with this name already exists
		if(imagesMap.containsKey(name))
			return false;
		
		Image image = new Image(url);
		
		image.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				System.out.println("ImageHandler couldn't load an image");
				loadingFailed = true;
			}
		});
		
		image.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				System.out.println("ImageHandler successfully loaded an image");
				numberImagesLoaded++;
				
				if(numberImagesLoaded == imagesToLoad) {
					imagesLoaded = true;
				}
			}
		});
		
		imagePanel.add(image);
		
		imagesMap.put(name, image);

		return true;
	}
	
	/**
	 * 
	 * @param name
	 * @param url
	 * @return
	 */
	public boolean addImage(String name, String url) {
		if(imagesToCreateMap.containsKey(name) || imagesMap.containsKey(name))
			return false;

		imagesToCreateMap.put(name, url);
		return true;
	}
	
	/**
	 * 
	 */
	public void loadImages() {
		
		imagesToLoad = imagesToCreateMap.size();
		
		for(Entry<String, String> entry : imagesToCreateMap.entrySet()) {
			this.newImage(entry.getKey(), entry.getValue(), 0, 0);
		}
		
		dumpNotLoadedImages();
	}
	
	/**
	 * 
	 * @param name of the image
	 * @return returns the image or if an image with the given name does not
	 * exist, null
	 */
	public Image getImage(String name) {
		if(imagesMap.containsKey(name)) {
			return imagesMap.get(name);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return the number of images
	 */
	public int getTotalNumberOfLoadedImages() {
		return imagesMap.size();
	}
	
	/**
	 * Dumps the images which need to be created
	 */
	public void dumpNotLoadedImages() {
		imagesToCreateMap.clear();
	}
	
	/**
	 * Dumps the loaded images
	 */
	public void dumpLoadedImages() {
		
		//remove from 'imagePanel'
		for(Image image : imagesMap.values()) {
			image.removeFromParent();
		}
		
		imagesMap.clear();
	}
	
	/**
	 * Dumps all images
	 */
	public void dumpAllImages() {
		dumpNotLoadedImages();
		dumpLoadedImages();
	}
	
	/**
	 * adds the imagePanel to the RootPanel
	 */
	private void addImagePanel() {
		RootPanel.get().add(imagePanel);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean areImagesLoaded() {
		return imagesLoaded;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasLoadingFailed() {
		return this.loadingFailed;
	}
	
	/**
	 * resets the status of the ImageHandler, so new images can be loaded and
	 * you can check if they have been loaded already or an image failed loading
	 */
	public void reset() {
		imagesLoaded = false;
		imagesToLoad = 0;
		numberImagesLoaded = 0;
		
		loadingFailed = false;
	}
	
	public boolean hasNothingToDo(){
		if(numberImagesLoaded == 0 && imagesToLoad == 0 && !loadingFailed)
		{
			return true;
		}
		return false;
	}
	
	//##########################################################################
	
	/**
	 * FOR TESTING
	 * @return
	 */
	public int getNumberOfAttachedImages() {
		return imagePanel.getWidgetCount();
	}
	
	/**
	 * FOR TESTING
	 * @return
	 */
	public int getNumberImagesLoaded() {
		return this.numberImagesLoaded;
	}
	
	public String getStatus()
	{
		
		return "numberImagesLoaded: "+ this.numberImagesLoaded + " loadingFailed: " + this.loadingFailed + "  imagesToLoad: "+ this.imagesToLoad +  " imagesLoaded: " + this.imagesLoaded; 
	}
	
}
