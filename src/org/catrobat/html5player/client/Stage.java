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
 package org.catrobat.html5player.client;

import org.catrobat.html5player.client.formulaeditor.UserVariablesContainer;
import org.catrobat.html5player.client.scripts.Script;
import org.catrobat.html5player.client.threading.CatScheduler;

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
	private Script currentScript = null;
	private Sprite currentSprite = null;
	private UserVariablesContainer userVariables;
	   public UserVariablesContainer getUserVariables() {
	        return userVariables;
	    }
	
	   public Sprite getCurrentSprite() {
	        return currentSprite;
	    }

	    public void setCurrentSprite(Sprite sprite) {
	        currentSprite = sprite;
	    }

	    public Script getCurrentScript() {
	        return currentScript;
	    }

	    public void setCurrentScript(Script script) {
	        if (script == null) {
	            currentScript = null;
	        } else if (currentSprite.getScriptIndex(script) != -1) {
	            currentScript = script;
	        }
	    }

	private Stage() {
		spriteManager = new SpriteManager();
		messageContainer = new MessageContainer();
		userVariables = new UserVariablesContainer();
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
		if(!parser.isParsingComplete())
		{
			return;
		}

		CatrobatDebug.debug("XML got parsed");
		CatrobatDebug.debug("Parsing took " + (System.currentTimeMillis() - start) + " ms");
		
		ImageHandler.get().loadImages();
		
		CatrobatDebug.debug("Actual number of attached images: " + ImageHandler.get().getNumberOfAttachedImages());
		
		 //add StartScripts to scheduler
		spriteManager.playCatroid();
		
//		spriteManager.debugSpriteCostumes();
		
		CatrobatDebug.debug("Actual scripts waiting to run: " + CatScheduler.get().getThreadCount());
		
		//wait for the images to load, then start the scheduler
		Timer imagesLoadedTimer = new Timer() {
			
			@Override
			public void run() {

				if(ImageHandler.get().areImagesLoaded() || ImageHandler.get().hasNothingToDo()) {	
					
					CatrobatDebug.debug(ImageHandler.get().getNumberImagesLoaded() + " images are loaded, now starting scheduler...");

					ImageHandler.get().reset();
					
					//revive scheduler, so it can run again
					CatScheduler.get().reviveScheduler();
					
					//start CatScheduler
					Scheduler.get().scheduleFixedDelay(CatScheduler.get(), CatScheduler.SCHEDULE_DELAY);
					this.cancel();
				}
				else {
					CatrobatDebug.debug("ImageHandler not finished loading...");
					CatrobatDebug.debug(ImageHandler.get().getStatus());
					if(ImageHandler.get().hasLoadingFailed()) {
						CatrobatDebug.error("ImageHandler couldn't load an image");
						log("ImageHandler couldn't load an image");
						this.cancel();
						
						loadingFailure();
					}
				}
			}
		};
		
		imagesLoadedTimer.scheduleRepeating(50);
	}
	
	/**
	 * 
	 */
	public void displayLoadingImage() {

		CatrobatDebug.debug("displayLoadingImage...");

		//ImageHandler.get().addImage("loadgif", "images/ajax-loader.gif");
		Image i = new Image( "images/ajax-loader.gif");
		ImageHandler.get().newImage("loadgif", i);
		ImageHandler.get().loadImages();
		
		//wait for the image to load, then draw it
		Timer imageLoadedTimer = new Timer() {
			
			@Override
			public void run() {

				if(ImageHandler.get().areImagesLoaded()) {	
					
					CatrobatDebug.debug("Loading gif ready to display");
					
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
	    userVariables = new UserVariablesContainer();
		defaultLogBoxSettings();
		
		CatrobatDebug.debug("Spritemanager contains " + spriteManager.getSpriteList().size() + " sprites");
		CatrobatDebug.debug("MessageContainer holds " + messageContainer.getMessages().size() + " messages");
		
		CatrobatDebug.info("Now clearing the stage...");
		
		//clear messageContainer
		messageContainer.clear();
		
		//remove all sprites and stop currently running sounds
		spriteManager.clearSprites();
		
		//kill all currently running threads and the scheduler
		CatScheduler.get().killScheduler();
		
		//dump all loaded and unloaded images
		ImageHandler.get().dumpAllImages();
		ImageHandler.get().reset();
		
		CatrobatDebug.debug("Spritemanager contains " + spriteManager.getSpriteList().size() + " sprites");
		CatrobatDebug.debug("MessageContainer holds " + messageContainer.getMessages().size() + " messages");
		CatrobatDebug.debug("Actually scheduled threads: " + CatScheduler.get().getThreadCount());
		CatrobatDebug.debug("Actually loaded images " + ImageHandler.get().getTotalNumberOfLoadedImages());
		CatrobatDebug.debug("Number of attached images " + ImageHandler.get().getNumberOfAttachedImages());
		
		CatrobatDebug.info("Stage got cleared...");
	}
	
	/**
	 * Display an error message on the canvas
	 */
	private void loadingFailure() {
		
		String message = "Error: Couldn't load an image";
		
		Scene.get().clearCanvas();
		Scene.get().setFont("12pt Calibri");
		Scene.get().write(message, getStageMiddleX(), getStageMiddleY(), "center");

		CatrobatDebug.info("Error message written to canvas");
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
		
		if(logText == "")
		{
			logText = message.trim();
		}
		else
		{
			logText += "\n" + message.trim();
		}
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
