/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.Look;

public class IfOnEdgeBounceBrick extends Brick {

	public IfOnEdgeBounceBrick(String sprite) {
		super(sprite);
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
		
		int virtualScreenWidth = Stage.getInstance().getStageMiddleX();
		int virtualScreenHeight = Stage.getInstance().getStageMiddleY();
		
		Look look = sprite.getLook();
		
		double size = look.getSize();
		int width = (int)(look.getLookData().getWidth() * size);
		int height = (int)(look.getLookData().getHeight() * size);
		
		int xPosition = (int)look.getXPosition() - virtualScreenWidth;
		int yPosition = (int)look.getYPosition() - virtualScreenHeight;
		
		double rotationResult = -look.getRotation() + 90.0;
		
		
		CatrobatDebug.debug("IOEB - x: " + xPosition + ", y: " + yPosition + ", rot: " + look.getRotation());
		CatrobatDebug.debug("IOEB - w: " + width + ", h: " + height);
		CatrobatDebug.debug("IOEB - vSW: " + virtualScreenWidth + ", vSH: " + virtualScreenHeight);
		
		//check x-coordinate
		//
		if(xPosition < -virtualScreenWidth + width/2) { //left edge
			rotationResult = Math.abs(rotationResult);
			xPosition = -virtualScreenWidth + width/2;
			
			CatrobatDebug.debug("sprite on left edge");
		}
		else if(xPosition > virtualScreenWidth - width/2) { //right edge
			rotationResult = -Math.abs(rotationResult);
			xPosition = virtualScreenWidth - width/2;
			
			CatrobatDebug.debug("sprite on right edge");
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
			
			CatrobatDebug.debug("sprite on top edge");
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
			
			CatrobatDebug.debug("sprite on bottom edge");
		}
		
		
		look.setRotation(-rotationResult + 90);
		look.setXYPosition(xPosition + virtualScreenWidth, yPosition + virtualScreenHeight);
		
		CatrobatDebug.debug("IOEB - x: " + look.getXPosition() + ", y: " + look.getYPosition() + ", rot: " + look.getRotation());
		
		return true;
	}

}
