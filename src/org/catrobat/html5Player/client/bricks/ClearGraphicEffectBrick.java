package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class ClearGraphicEffectBrick extends Brick {
	
	public ClearGraphicEffectBrick(String spriteName) {
		super(spriteName);
	}

	@Override
	protected boolean execute(Sprite sprite) {
		
		Costume costume = sprite.getCostume();
		
		costume.setAlphaValue(1.0);
		costume.setBrightnessValue(1.0);
		
		return true;
	}

}
