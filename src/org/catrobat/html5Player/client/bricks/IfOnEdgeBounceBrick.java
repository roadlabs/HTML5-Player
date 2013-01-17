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
