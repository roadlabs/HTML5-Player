package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;

public class IfOnEdgeBounceBrick extends Brick {

	public IfOnEdgeBounceBrick(String sprite) {
		super(sprite);
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
		
		CatrobatDebug.off();
		
		int virtualScreenWidth = Stage.getInstance().getStageMiddleX();
		int virtualScreenHeight = Stage.getInstance().getStageMiddleY();
		
		Costume costume = sprite.getCostume();
		
		double size = costume.getSize();
		int width = (int)(costume.getCostumeData().getWidth() * size);
		int height = (int)(costume.getCostumeData().getHeight() * size);
		
		int xPosition = (int)costume.getXPosition() - virtualScreenWidth;
		int yPosition = (int)costume.getYPosition() - virtualScreenHeight;
		
		double rotationResult = -costume.getRotation() + 90.0;
		
		
		CatrobatDebug.console("IOEB - x: " + xPosition + ", y: " + yPosition + ", rot: " + costume.getRotation());
		CatrobatDebug.console("IOEB - w: " + width + ", h: " + height);
		CatrobatDebug.console("IOEB - vSW: " + virtualScreenWidth + ", vSH: " + virtualScreenHeight);
		
		//check x-coordinate
		//
		if(xPosition < -virtualScreenWidth + width/2) { //left edge
			rotationResult = Math.abs(rotationResult);
			xPosition = -virtualScreenWidth + width/2;
			
			CatrobatDebug.console("sprite on left edge");
		}
		else if(xPosition > virtualScreenWidth - width/2) { //right edge
			rotationResult = -Math.abs(rotationResult);
			xPosition = virtualScreenWidth - width/2;
			
			CatrobatDebug.console("sprite on right edge");
		}
		
		//check y-coordinate
		//
		if(yPosition < -virtualScreenHeight + height/2) { //top edge
			if(Math.abs(rotationResult) > 90) {
				if(rotationResult < 0) {
					rotationResult = -180 - rotationResult;
				}
				else {
					rotationResult = 180 - rotationResult;
				}
			}
			
			yPosition = -virtualScreenHeight + height/2;
			
			CatrobatDebug.console("sprite on top edge");
		}
		else if(yPosition > virtualScreenHeight - height/2) { //bottom edge
			if(Math.abs(rotationResult) < 90) {
				if(rotationResult < 0) {
					rotationResult = -180 - rotationResult;
				}
				else {
					rotationResult = 180 - rotationResult;
				}
			}
			
			yPosition = virtualScreenHeight - height/2;
			
			CatrobatDebug.console("sprite on bottom edge");
		}
		
		
		costume.setRotation(-rotationResult + 90);
		costume.setXYPosition(xPosition + virtualScreenWidth, yPosition + virtualScreenHeight);
		
		CatrobatDebug.console("IOEB - x: " + costume.getXPosition() + ", y: " + costume.getYPosition() + ", rot: " + costume.getRotation());
		
		CatrobatDebug.off();
		
		return true;
	}

}
