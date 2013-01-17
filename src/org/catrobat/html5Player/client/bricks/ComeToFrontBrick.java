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

import java.util.Collection;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Look;


public class ComeToFrontBrick extends Brick {

	public ComeToFrontBrick(String sprite) {
		super(sprite);
	}

	@Override
	public boolean execute(Sprite sprite) {
		Look look = sprite.getLook();
		Collection<Sprite> sprites = Stage.getInstance().getSpriteManager().getSpriteList();

		if (look.getZPosition() == Integer.MAX_VALUE) {
			return false;
		}
		int maxLayer = 0;
		for (Sprite sp : sprites) {
			int layer = sp.getLook().getZPosition();
			if (layer > maxLayer)
				maxLayer = layer;
		}

		int layer = maxLayer + 1;
		if (layer <= look.getZPosition()) {
			return false;
		}
		look.setZPosition(layer);
		return true;
	}

}
