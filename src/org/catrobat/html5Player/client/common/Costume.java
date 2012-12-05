package org.catrobat.html5Player.client.common;

import org.catrobat.html5Player.client.Const;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;

//This class is for compatibility with android catroid
public class Costume {

	private CostumeData costumeData;

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
	private double x;
	private double y;

	
	private Image image;

	public Costume(float xPosition, float yPosition, boolean show) {
		this.xPostition = xPosition;
		this.yPosition = yPosition;
		this.show = show;
	}

	public CostumeData getCostumeData() {
		return costumeData;
	}

	public void setCostumeData(CostumeData costumeData) {
		this.costumeData = costumeData;
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

	private void updateImage() {
		image = new Image(getImageBasePath() + this.costumeData.getFilename());
		image.setPixelSize(this.costumeData.getWidth(),	this.costumeData.getHeight());
		image.setVisible(false);
	}
	
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
		
		if(costumeData != null) {
			string += "name: " + costumeData.getName() +
					  ", width: " + costumeData.getWidth() + ", height: " + costumeData.getHeight() +
					  ", ";
		}
		
		string += "xPos: " + xPostition + ", yPos: " + yPosition + ", zPos: " + zPosition;
		string += ", rot: " + rotation + ", size: " + size + ", show: " + show;
		
		
		string += ", middleX: " + middleX + ", middleY: " + middleY;
		
		return string;
	}
	
}
