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

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class MoveNStepsBrick extends Brick {

	private double steps;

	public MoveNStepsBrick(String sprite, double steps) {
		super(sprite);
		this.steps = steps;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		
		// the MINUS is important because canvas positive rotation is clockwise
		double radians = Math.toRadians(-costume.getRotation());

		int newXPosition = (int) Math.round(costume.getXPosition() + steps * Math.cos(radians));
		int newYPosition = (int) Math.round(costume.getYPosition() + steps * Math.sin(radians));

		costume.setXYPosition(newXPosition, newYPosition);

		
		costume.setMiddleX(Math.round(costume.getMiddleX() + steps * Math.cos(radians)));
		costume.setMiddleY(Math.round(costume.getMiddleY() + steps * Math.sin(radians)));
		
		return true;
	}

}
