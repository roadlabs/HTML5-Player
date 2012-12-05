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
				
				sprite.showCostume(); //verträgt sich nicht mit unit-tests, auskommentieren beim testen
			}
		}
		else {
			// if there are no costumes do nothing
		}
	
		return true;
	}

}
