package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class ChangeSizeByNBrick extends Brick {

	private double deltaSize;

	public ChangeSizeByNBrick(String sprite, double newDeltaSize) {
		super(sprite);
		this.deltaSize = newDeltaSize;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.setSize(costume.getSize() + deltaSize / 100) ;
		return true;
	}

}
