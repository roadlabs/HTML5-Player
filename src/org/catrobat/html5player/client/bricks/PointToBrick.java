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

public class PointToBrick extends Brick {

	private String pointedSpriteName;
	private double rotationDegrees = 0.0;
	
	public PointToBrick(String spriteName, String pointedSpriteName) {
		super(spriteName);
		this.pointedSpriteName = pointedSpriteName;
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
	
		Sprite pointedSprite = Stage.getInstance().getSpriteManager().getSprite(pointedSpriteName, false);
		
		if(pointedSprite == null) {
			pointedSprite = sprite;
		}
		
		CatrobatDebug.debug("pointedSprite: " + pointedSprite);
		
		int spriteXPosition = (int)sprite.getLook().getXPosition();
		int spriteYPosition = (int)sprite.getLook().getYPosition();
		
		int pointedSpriteXPosition = (int)pointedSprite.getLook().getXPosition();
		int pointedSpriteYPosition = (int)pointedSprite.getLook().getYPosition();
		
		double base = 0.0;
		double height = 0.0;
		double value = 0.0;
		
		if(spriteXPosition == pointedSpriteXPosition && spriteYPosition == pointedSpriteYPosition) {
			rotationDegrees = 90.0;
		}
		else if(spriteXPosition == pointedSpriteXPosition || spriteYPosition == pointedSpriteYPosition) {
			if(spriteXPosition == pointedSpriteXPosition) {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 180.0;
				}
				else {
					rotationDegrees = 0.0;
				}
			}
			else {
				if(spriteXPosition > pointedSpriteXPosition) {
					rotationDegrees = 270.0;
				}
				else {
					rotationDegrees = 90.0;
				}
			}
		}
		else {
			
			base = Math.abs(spriteYPosition - pointedSpriteYPosition);
			height = Math.abs(spriteXPosition - pointedSpriteXPosition);
			value = Math.toDegrees(Math.atan(base/height));
			
			if(spriteXPosition < pointedSpriteXPosition) {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 90.0 + value;
				}
				else {
					rotationDegrees = 90.0 - value;
				}
			}
			else {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 270.0 - value;
				}
				else {
					rotationDegrees = 270.0 + value;
				}
			}
		}
		
		// the MINUS is important because canvas positive rotation is clockwise
		sprite.getLook().setRotation( -(-rotationDegrees + 90.0) );
		
		return true;
	}

}
