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
 *  This /**
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
 */
 package org.catrobat.html5Player.client;

import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;

public class Stage {

	private static final int TEXT_LINE_HEIGHT = 10;

	private static Stage instance = null;

	private final SpriteManager spriteManager;
	private final MessageContainer messageContainer;

	private String projectNumber;
	private Canvas rootCanvas;
	private TextArea logBox;

	private Stage() {
		spriteManager = new SpriteManager();
		messageContainer = new MessageContainer();
	}

	public static Stage getInstance() {
		if (instance == null) {
			instance = new Stage();
		}
		return instance;
	}
	
	//##########################################################################
	
	/**
	 * Parses the project-XML, adds all StartScripts to the scheduler, loads
	 * all images which where added to the ImageHandler and waits for them to
	 * load. After the images got loaded, the scheduler gets started.
	 * 
	 * @param projectXml
	 */
	public void start(String projectXml) {

		Parser parser = new Parser();
		
		long start = System.currentTimeMillis();
		
		parser.parseXML(spriteManager, projectXml);
		
		CatrobatDebug.on();
		
		CatrobatDebug.console("XML got parsed");
		CatrobatDebug.console("Parsing needed " + (System.currentTimeMillis() - start) + "ms");
		
		ImageHandler.get().loadImages();
		
		CatrobatDebug.console("actual number of attached images: " + ImageHandler.get().getNumberOfAttachedImages());
		CatrobatDebug.off();
		
		 //add StartScripts to scheduler
		spriteManager.playCatroid();
		
//		spriteManager.debugSpriteCostumes();
		
		CatrobatDebug.on();
		CatrobatDebug.console("actual scripts waiting to run: " + CatScheduler.get().getThreadCount());
		CatrobatDebug.off();
		
		//wait for the images to load, then start the scheduler
		Timer imagesLoadedTimer = new Timer() {
			
			@Override
			public void run() {

				if(ImageHandler.get().areImagesLoaded()) {	
					
					CatrobatDebug.on();
					CatrobatDebug.console(ImageHandler.get().getNumberImagesLoaded() + " images are loaded, now start scheduler...");
					CatrobatDebug.off();
					
					ImageHandler.get().reset();
					
					//revive scheduler, so it can run again
					CatScheduler.get().reviveScheduler();
					
					//start CatScheduler
					Scheduler.get().scheduleFixedDelay(CatScheduler.get(), CatScheduler.SCHEDULE_DELAY);
					this.cancel();
				}
				else {
					CatrobatDebug.on();
					CatrobatDebug.console("ImageHandler not finished loading...");
					
					if(ImageHandler.get().hasLoadingFailed()) {
						CatrobatDebug.console("Error: ImageHandler couldn't load an image");
						log("Error: ImageHandler couldn't load an image");
						this.cancel();
						
						loadingFailure();
					}
					
					CatrobatDebug.off();
				}
			}
		};
		
		imagesLoadedTimer.scheduleRepeating(50);
	}
	
	/**
	 * 
	 */
	public void displayLoadingImage() {
		
		CatrobatDebug.on();
		CatrobatDebug.console("displayLoadingImage...");
		CatrobatDebug.off();
		
		ImageHandler.get().addImage("loadgif", "images/ajax-loader.gif");
		ImageHandler.get().loadImages();
		
		//wait for the image to load, then draw it
		Timer imageLoadedTimer = new Timer() {
			
			@Override
			public void run() {

				if(ImageHandler.get().areImagesLoaded()) {	
					
					CatrobatDebug.on();
					CatrobatDebug.console("loading gif ready to display");
					CatrobatDebug.off();
					
					Image image = ImageHandler.get().getImage("loadgif");
					
					ImageHandler.get().reset();
					
					Scene.get().clearCanvas();
					
					Scene.get().drawImage(image, getStageMiddleX(),
										  getStageMiddleY(), image.getWidth(),
										  image.getHeight(), 1);
//					Scene.get().update();
					this.cancel();
				}
			}
		};
		
		imageLoadedTimer.scheduleRepeating(10);
	}
	
	/**
	 * 
	 */
	public void clearStage() {
		defaultLogBoxSettings();
		
		CatrobatDebug.console("Spritemanager contains " + spriteManager.getSpriteList().size() + " sprites");
		CatrobatDebug.console("MessageContainer holds " + messageContainer.getMessages().size() + " messages");
		
		CatrobatDebug.console("Now clear stage...");
		
		//clear messageContainer
		messageContainer.clear();
		
		//remove all sprites and stop currently running sounds
		spriteManager.clearSprites();
		
		//kill all currently running threads and the scheduler
		CatScheduler.get().killScheduler();
		
		//dump all loaded and unloaded images
		ImageHandler.get().dumpAllImages();
		
		CatrobatDebug.console("Spritemanager contains " + spriteManager.getSpriteList().size() + " sprites");
		CatrobatDebug.console("MessageContainer holds " + messageContainer.getMessages().size() + " messages");
		CatrobatDebug.console("Actually scheduled threads: " + CatScheduler.get().getThreadCount());
		CatrobatDebug.console("Actually loaded images " + ImageHandler.get().getTotalNumberOfLoadedImages());
		CatrobatDebug.console("Number of attached images " + ImageHandler.get().getNumberOfAttachedImages());
		
		CatrobatDebug.console("Stage got cleared...");
		
		CatrobatDebug.off();
	}
	
	/**
	 * Display an error message on the canvas
	 */
	private void loadingFailure() {
		
		String message = "Error: Couldn't load an image";
		
		Scene.get().clearCanvas();
		Scene.get().setFont("12pt Calibri");
		Scene.get().write(message, getStageMiddleX(), getStageMiddleY(), "center");
		
		CatrobatDebug.on();
		CatrobatDebug.console("wirte error msg");
		CatrobatDebug.off();
//		Scene.get().update();
	}
	
	//##########################################################################
	
	/**
	 * 
	 * @param message
	 */
	public void log(String message) {
		if (message == null)
			return;
		
		String logText = logBox.getText();
		if (logText == null)
			logText = "";
		
		logText += "\n" + message.trim();
		logBox.setText(logText);
		logBox.setSize("400px", (logText.split("\\n").length * TEXT_LINE_HEIGHT) + "px");
	}
	
	public void defaultLogBoxSettings() {
		if(this.logBox != null) {
			logBox.setText("");
			logBox.setSize("400px", TEXT_LINE_HEIGHT + "px");
			logBox.setVisible(false);
		}
	}
	
	//##########################################################################

	public void setProjectNumber(String number) {
		this.projectNumber = number;
	}

	public String getProjectNumber() {
		return this.projectNumber;
	}

	public SpriteManager getSpriteManager() {
		return spriteManager;
	}

	public void setCanvas(Canvas canvas) {
		rootCanvas = canvas;
	}

	public Canvas getCanvas() {
		return rootCanvas;
	}

	public int getStageMiddleX() {
		int middleX = (int)rootCanvas.getCoordinateSpaceWidth() / 2;
		return middleX;
	}

	public int getStageMiddleY() {
		int middleY = (int)rootCanvas.getCoordinateSpaceHeight() / 2;
		return middleY;
	}

	public MessageContainer getMessageContainer() {
		return messageContainer;
	}

	public void setLogBox(TextArea logBox) {
		this.logBox = logBox;
	}

}
