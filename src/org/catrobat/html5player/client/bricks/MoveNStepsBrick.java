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

public class MoveNStepsBrick extends Brick {

	private Formula steps;

	public MoveNStepsBrick(String sprite, Formula steps) {
		super(sprite);
		this.steps = steps;
	}

	@Override
	public boolean execute(Sprite sprite) {
	    double steps = this.steps.interpretFloat(sprite);
		Look look = sprite.getLook();
		
		// the MINUS is important because canvas positive rotation is clockwise
		double radians = Math.toRadians(-look.getRotation());

		int newXPosition = (int) Math.round(look.getXPosition() + steps * Math.cos(radians));
		int newYPosition = (int) Math.round(look.getYPosition() + steps * Math.sin(radians));

		look.setXYPosition(newXPosition, newYPosition);

		
		look.setMiddleX(Math.round(look.getMiddleX() + steps * Math.cos(radians)));
		look.setMiddleY(Math.round(look.getMiddleY() + steps * Math.sin(radians)));
		
		return true;
	}

}
