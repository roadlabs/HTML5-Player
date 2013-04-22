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
 */
 package org.catrobat.html5player.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class Scene {

	private static Scene instance = null;
	
	private Canvas sceneCanvas;
	private boolean isSceneCreated;
	private CssColor fillColor;
	
	private int sceneWidth = 0;
	private int sceneHeight = 0;
	
	//##########################################################################
	
	private Scene() {
		isSceneCreated = false;
	}
	
	/**
	 *
	 */
	public static Scene get() {
		if(instance == null) {
			instance = new Scene();
		}
		
		return instance;
	}
	
	//##########################################################################
	
	/**
	 * Create a canvas
	 * @return true if scene is created or was already created, false if canvas
	 * is not supported
	 */
	public boolean createScene() {
		
		if(!isSceneCreated) {
			sceneCanvas = Canvas.createIfSupported();
			
			if(sceneCanvas == null)
				return false;
			
			isSceneCreated = true;
			fillColor = CssColor.make("rgb(255,255,255)");
		}
		
		CatrobatDebug.info("Scene created");
		
		return true;
	}
	
	/**
	 * Create a canvas with the given measures
	 * @param sceneWidth
	 * @param sceneHeight
	 * @return true if scene is created or was already created, false if canvas
	 * is not supported
	 */
	public boolean createScene(int sceneWidth, int sceneHeight) {
		
		if(createScene()) {
			setSceneMeasures(sceneWidth, sceneHeight);
			System.out.println("Scene created with width: " + sceneWidth + " and height: " + sceneHeight);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets the given measures for front and back canvas
	 * @param sceneWidth
	 * @param sceneHeight
	 */
	public void setSceneMeasures(int sceneWidth, int sceneHeight) {
		
		if(isSceneCreated) {
			clearCanvas();
			
			this.sceneWidth = sceneWidth;
			this.sceneHeight = sceneHeight;
			
			sceneCanvas.setWidth(sceneWidth + "px");
			sceneCanvas.setHeight(sceneHeight + "px");
			
			sceneCanvas.setCoordinateSpaceWidth(this.sceneWidth);
			sceneCanvas.setCoordinateSpaceHeight(this.sceneHeight);
			
//			back.getContext2d().translate(sceneWidth/2, sceneHeight/2);
			
			System.out.println("Scene got measures - width: " + this.sceneWidth + " and height: " + this.sceneHeight);
		}
	}
	
	
	//############################ DRAW IMAGE ##################################
	
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawImage(Image image, double x, double y, double width, double height, double alpha) {
		ImageElement imageElement = (ImageElement)image.getElement().cast();
		
		drawImageElement(imageElement, x, y, width, height, alpha);
	}
	
	/**
	 * 
	 * @param imageElement
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawImageElement(ImageElement imageElement, double x, double y, double width, double height, double alpha) {
		
		long start = System.currentTimeMillis();
		
		Context2d context = sceneCanvas.getContext2d();
		context.save();
		context.setGlobalAlpha(alpha);
		context.drawImage(imageElement, x, y, width, height);
		context.restore();
		
		
//		CatrobatDebug.on();
		CatrobatDebug.console("drawImageElement-execution needed " + (System.currentTimeMillis() - start) + "ms");
		CatrobatDebug.off();
		
	}
	
	/**
	 * 
	 * @param image
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 */
	public void drawImage(Image image, double translateX, double translateY, double x, double y, double width, double height, double degrees, double xSize, double ySize, double alpha) {
		
		long start = System.currentTimeMillis();
		
		ImageElement imageElement = (ImageElement)image.getElement().cast();
		
		drawImageElement(imageElement, translateX, translateY, x, y, width, height, degrees, xSize, ySize, alpha);
		
//		CatrobatDebug.on();
		CatrobatDebug.console("drawImage-execution needed " + (System.currentTimeMillis() - start) + "ms");
		CatrobatDebug.off();
	}
	
	/**
	 * 
	 * @param imageElement
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 */
	public void drawImageElement(ImageElement imageElement, double translateX, double translateY, double x, double y, double width, double height, double degrees, double xSize, double ySize, double alpha) {
		
		long start = System.currentTimeMillis();
		
		Context2d context = sceneCanvas.getContext2d();
		
		context.save();
		context.setGlobalAlpha(alpha);
		context.translate(translateX, translateY);
		context.rotate(Math.toRadians(degrees));
		context.scale(xSize, ySize);
		context.drawImage(imageElement, x, y, width, height);
		
		//for testing - draws a rectangular around the sprite
//		context.strokeRect(x, y, width, height);
		//
		
		context.restore();
		
//		CatrobatDebug.on();
		CatrobatDebug.console("drawImageElement-execution needed " + (System.currentTimeMillis() - start) + "ms");
		CatrobatDebug.off();
	}
	
	//############################### TEXT #####################################

	/**
	 * 
	 * @param fontSetting
	 */
	public void setFont(String fontSetting) {
		sceneCanvas.getContext2d().setFont(fontSetting);
	}
	
	/**
	 * 
	 * @param text
	 * @param x
	 * @param y
	 */
	public void write(String text, double x, double y) {
		Context2d context = sceneCanvas.getContext2d();
		context.fillText(text, x, y);
	}
	
	/**
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param align
	 */
	public void write(String text, double x, double y, String align) {
		Context2d context = sceneCanvas.getContext2d();
		context.setTextAlign(align);
		context.fillText(text, x, y);
	}
	
	//############################### BRIGHTNESS ###############################
	
	/**
	 * 
	 * @param image
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 */
	public void drawImageBrightness(Image image, double translateX, double translateY, double x, double y, double width, double height, double degrees, double xSize, double ySize, double alpha, double brightness) throws JavaScriptException {
		ImageElement imageElement = (ImageElement)image.getElement().cast();
		
		try {
			drawImageElementBrightness(imageElement, translateX, translateY, x, y, width, height, degrees, xSize, ySize, alpha, brightness);
		}
		catch(JavaScriptException exception) {
			throw exception;
		}
	}
	
	/**
	 * 
	 * @param imageElement
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 */
	public void drawImageElementBrightness(ImageElement imageElement, double translateX, double translateY, double x, double y, double width, double height, double degrees, double xSize, double ySize, double alpha, double brightness) throws JavaScriptException {
		Context2d context = sceneCanvas.getContext2d();
		context.save();
		context.setGlobalAlpha(alpha);
		context.translate(translateX, translateY);
		context.rotate(Math.toRadians(degrees));
		context.scale(xSize, ySize);
		
		try {
			Canvas adjustedImage = adjustImageBrightness(imageElement, brightness);
			context.drawImage(adjustedImage.getCanvasElement(), x, y, width, height);
			context.restore();
		}
		catch(JavaScriptException exception) {
			context.restore();
			throw exception;
		}
	}
	
	/**
	 * 
	 * @param imageElement
	 * @param brightness
	 * @return Canvas canvas with the adjusted image
	 */
	private Canvas adjustImageBrightness(ImageElement imageElement, double brightness) {
		
		int width = imageElement.getWidth();
		int height = imageElement.getHeight();
		
		Canvas temp = Canvas.createIfSupported();
		temp.setCoordinateSpaceWidth(width);
		temp.setCoordinateSpaceHeight(height);

		Context2d context = temp.getContext2d();
		
		context.drawImage(imageElement, 0, 0);
		
		ImageData imageData = context.getImageData(0, 0, width, height);
		
		CanvasPixelArray pixelsData = imageData.getData();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				int index = (y * width + x) * 4; //'pixel'-index (red-channel of pixel)
				
				int r = checkColorRange((int)((double)pixelsData.get(index) * brightness)); //red channel
				pixelsData.set(index, r);
				
				int g = checkColorRange((int)((double)pixelsData.get(++index) * brightness)); //green channel
				pixelsData.set(index, g);
				
				int b = checkColorRange((int)((double)pixelsData.get(++index) * brightness)); //blue channel
				pixelsData.set(index, b);
				
				//++index -> alpha channel
			}
		}

		context.putImageData(imageData, 0, 0);
		
		return temp;
	}
	
	/**
	 * Checks if the given color value is within the range 0...255
	 * @param colorValue
	 * @return 0 if the value is lesser or equal than 0 <br />
	 *         255 if the value is greater or equal than 255 <br />
	 *         otherwise the value itself
	 */
	private int checkColorRange(int colorValue) {
		if(colorValue <= 0)
			return 0;
		else if(colorValue >= 255) {
			return 255;
		}
		else {
			return colorValue;
		}
	}
	
	//############################### JSNI #####################################
	
	/**
	 * 
	 * @param image
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 * @param alpha
	 */
	public void drawImageJSNI(Image image, double translateX, double translateY, double x, double y, double width, double height, double degrees, double alpha) {
		ImageElement imageElement = (ImageElement)image.getElement().cast();
		Context2d context = sceneCanvas.getContext2d();
		
		this.drawImageJSNI(imageElement, translateX, translateY, x, y, width, height, degrees, alpha, context);
	}
	
	/**
	 * 
	 * @param image
	 * @param translateX
	 * @param translateY
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param degrees
	 * @param xSize
	 * @param ySize
	 * @param alpha
	 * @param context
	 */
	private native void drawImageJSNI(ImageElement image, double translateX, double translateY, double x, double y, double width, double height, double degrees, double alpha, Context2d context) /*-{
		context.save();
		context.globalAlpha = alpha;
		context.translate(translateX, translateY);
		context.rotate(degrees * Math.PI / 180);
		context.drawImage(image, x, y, width, height);
		context.restore();
	}-*/;
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void clearCanvas() {
		
		long start = System.currentTimeMillis();
		
		Context2d context = sceneCanvas.getContext2d();
		context.save();
		context.setFillStyle(fillColor);
		context.fillRect(0, 0, this.sceneWidth, this.sceneHeight);
		context.restore();
		
		CatrobatDebug.on();
		CatrobatDebug.console("clearCanvas-execution needed " + (System.currentTimeMillis() - start) + "ms");
		CatrobatDebug.off();
	}
	
	//##########################################################################
	
	/**
	 * 
	 * @return Canvas canvas
	 */
	public Canvas getCanvas() {
		return sceneCanvas;
	}
	
	/**
	 * 
	 */
	public boolean isSceneCreated() {
		return isSceneCreated;
	}
	
	/**
	 * 
	 */
	public int getSceneWidth() {
		return this.sceneWidth;
	}
	
	/**
	 * 
	 */
	public int getSceneHeight() {
		return this.sceneHeight;
	}
	
	//##########################################################################
	
	/**
	 * FOR UNIT-TESING
	 */
	public void reset() {
		instance = null;
	}
	
	/**
	 * FOR TESING
	 */
	public void drawAxis() {
		Context2d ctx = sceneCanvas.getContext2d();
		
		ctx.beginPath();
		ctx.moveTo(getSceneWidth()/2, 0);
		ctx.lineTo(getSceneWidth()/2, getSceneHeight());
		ctx.stroke();
		ctx.closePath();
		
		ctx.beginPath();
		ctx.moveTo(0, getSceneHeight()/2);
		ctx.lineTo(getSceneWidth(), getSceneHeight()/2);
		ctx.stroke();
		ctx.closePath();
	}
}
