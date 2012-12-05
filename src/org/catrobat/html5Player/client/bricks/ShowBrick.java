package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class ShowBrick extends Brick {

	public ShowBrick(String sprite) {
		super(sprite);
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.show();
		return true;
	}

}
