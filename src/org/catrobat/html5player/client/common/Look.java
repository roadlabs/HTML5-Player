/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.html5player.client.common;

import org.catrobat.html5player.client.Const;
import org.catrobat.html5player.client.Stage;
import com.google.gwt.user.client.ui.Image;

//This class is for compatibility with android catroid
public class Look {

	private LookData lookData;

	private double xPostition;
	private double yPosition;
	private int zPosition = 0;
	private boolean show;
	private double rotation = 0;
	private double size = 1;
	
	private double alphaValue = 1.0;
	private double brightnessValue = 1.0;
	
	
	private double middleX;
	private double middleY;

	
	private Image image;

	public Look(float xPosition, float yPosition, boolean show) {
		this.xPostition = xPosition;
		this.yPosition = yPosition;
		this.show = show;
	}

	public LookData getLookData() {
		return lookData;
	}

	public void setLookData(LookData lookData) {
		this.lookData = lookData;
//		updateImage();
	}

	public void setXYPosition(double setXPosition, double setYPosition) {
		this.xPostition = setXPosition;
		this.yPosition = setYPosition;
	}

	public double getXPosition() {
		return xPostition;
	}

	public void setXPosition(double setXPosition) {
		this.xPostition = setXPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public void setYPosition(double setYPosition) {
		this.yPosition = setYPosition;
	}

	public int getZPosition() {
		return zPosition;
	}

	public void setZPosition(int zPosition) {
		this.zPosition = zPosition;
	}

	public void hide() {
		this.show = false;
	}

	public void show() {
		this.show = true;
	}

	public boolean isVisible() {
		return show;
	}

	public double getRotation() {
		return this.rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public double getAlphaValue() {
		return this.alphaValue;
	}
	
	public void setAlphaValue(double newAlphaValue) {
		this.alphaValue = newAlphaValue;
	}
	
	public void changeAlphaValueBy(double value) {
		this.alphaValue += value;
		
		if(this.alphaValue < 0.0) {
			this.alphaValue = 0.0;
		}
		else if(this.alphaValue > 1.0) {
			this.alphaValue = 1.0;
		}
	}

	public double getBrightnessValue() {
		return this.brightnessValue;
	}
	
	public void setBrightnessValue(double newBrightnessValue) {
		this.brightnessValue = newBrightnessValue;
	}
	
	public void changeBrightnessValueBy(double value) {
		this.brightnessValue += value;
		
		if(this.brightnessValue < 0.0) {
			this.brightnessValue = 0.0;
		}
	}
	
	
	
	
	public Image getImage() {
		return image;
	}

	public static String getImageBasePath() {
		return Const.PROJECT_PATH + Stage.getInstance().getProjectNumber()+ "/images/";
	}

//	private void updateImage() {
//		image = new Image(getImageBasePath() + this.lookData.getFilename());
//		image.setPixelSize(this.lookData.getWidth(),	this.lookData.getHeight());
//		image.setVisible(false);
//	}
	
	public double getMiddleX() {
		return this.middleX;
	}
	
	public double getMiddleY() {
		return this.middleY;
	}

	public void setMiddleX(double newMiddleX) {
		this.middleX = newMiddleX;
	}
	
	public void setMiddleY(double newMiddleY) {
		this.middleY = newMiddleY;
	}
	
	
	
	
	
	
//	public void setX(double newX) {
//		this.x = newX;
//	}
//	
//	public void setY(double newY) {
//		this.y = newY;
//	}
//	
//	public double getX() {
//		return this.x;
//	}
//	
//	public double getY() {
//		return this.y;
//	}
	
	
	
	public String debug() {
		
		String string = "";
		
		if(lookData != null) {
			string += "name: " + lookData.getName() +
					  ", width: " + lookData.getWidth() + ", height: " + lookData.getHeight() +
					  ", ";
		}
		
		string += "xPos: " + xPostition + ", yPos: " + yPosition + ", zPos: " + zPosition;
		string += ", rot: " + rotation + ", size: " + size + ", show: " + show;
		
		
		string += ", middleX: " + middleX + ", middleY: " + middleY;
		
		return string;
	}
	
}
