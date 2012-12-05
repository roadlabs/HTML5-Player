package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class SetSizeToBrick extends Brick {

	private float size;

	public SetSizeToBrick(String sprite, float size) {
		super(sprite);
		this.size = size;
	}

	@Override
	protected boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.setSize(size / 100.0);
		
		return true;
	}

}
