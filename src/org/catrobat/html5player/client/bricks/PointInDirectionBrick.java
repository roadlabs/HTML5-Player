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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.common.Look;

public class PointInDirectionBrick extends Brick {

	public static final int DIRECTION_RIGHT = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;

	int direction;
	
	double degrees;

	public PointInDirectionBrick(String sprite, int direction, double degrees) {
		super(sprite);
		this.direction = direction;
		
		this.degrees = degrees;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Look look = sprite.getLook();
		
		double rotation = -degrees + 90.0;
		look.setRotation(rotation);
		
		return true;
	}

}
