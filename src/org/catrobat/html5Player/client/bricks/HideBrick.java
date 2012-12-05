package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class HideBrick extends Brick {

	public HideBrick(String sprite) {
		super(sprite);
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.hide();
		return true;
	}

}
