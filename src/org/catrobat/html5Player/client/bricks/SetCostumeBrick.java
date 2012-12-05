package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class SetCostumeBrick extends Brick {

	private String costumeName;

	public SetCostumeBrick(String spriteName, String costumeName) {
		super(spriteName);
		this.costumeName = costumeName;
	}

	public boolean execute(Sprite sprite) {

		if(sprite != null && sprite.getCostumeDataNames().contains(costumeName)) {
			sprite.getCostume().setCostumeData(sprite.getCostumeDataByName(costumeName));
			sprite.showCostume(); //verträgt sich nicht mit unit-tests, auskommentieren beim testen
		}
		
		return true;
	}

}
