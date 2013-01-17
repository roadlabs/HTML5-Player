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

import java.util.ArrayList;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.CostumeData;

public class NextCostumeBrick extends Brick {

	public NextCostumeBrick(String spriteName) {
		super(spriteName);
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
		
		ArrayList<CostumeData> costumeDataList = sprite.getCostumeData();
		
		int costumeDataListSize = costumeDataList.size();
		
		if(costumeDataListSize > 0) {
			
			CostumeData currentCostumeData = sprite.getCostume().getCostumeData();
			
			if(currentCostumeData != null) {
			
				int currentIndex = costumeDataList.indexOf(currentCostumeData);
				int nextIndex = (currentIndex + 1) % costumeDataListSize;
				currentCostumeData = costumeDataList.get(nextIndex);
				
				sprite.getCostume().setCostumeData(currentCostumeData);
				
				sprite.showCostume(); //vertr�gt sich nicht mit unit-tests, auskommentieren beim testen
			}
		}
		else {
			// if there are no costumes do nothing
		}
	
		return true;
	}

}
