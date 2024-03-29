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

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.formulaeditor.Formula;

public class ChangeYByBrick extends Brick {

	private Formula deltaY;

	public ChangeYByBrick(String sprite, Formula deltaY) {
		super(sprite);
		this.deltaY = deltaY;//-deltaY; // calculation moved after interpretation of formula
	}

	@Override
	public boolean execute(Sprite sprite) {
	    int newDeltaY = -deltaY.interpretInteger(sprite) ;
		Look look = sprite.getLook();

		look.setMiddleY(look.getMiddleY() + newDeltaY);
		
		int yPosition = (int) look.getYPosition();
//		int tempDeltaY = deltaY;
//		
//		if (tempDeltaY == Integer.MIN_VALUE) {
//			tempDeltaY = Integer.MAX_VALUE;
//		} else if (tempDeltaY == Integer.MAX_VALUE) {
//			tempDeltaY = Integer.MIN_VALUE;
//		} 
//		
//		if (yPosition > 0 && tempDeltaY > 0 && yPosition + tempDeltaY < 0) {
//			yPosition = Integer.MAX_VALUE;
//		} 
//		else if (yPosition < 0 && tempDeltaY < 0 && yPosition + tempDeltaY > 0) {
//			yPosition = Integer.MIN_VALUE;
//		} 
//		else {
//			yPosition += tempDeltaY;
//		}
		
		yPosition += newDeltaY;
		
		look.setXYPosition(look.getXPosition(), yPosition);
		
		return true;
	}

}
