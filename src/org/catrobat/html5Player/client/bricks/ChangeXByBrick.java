package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class ChangeXByBrick extends Brick {

	private float deltaX;

	public ChangeXByBrick(String spriteName, float newDeltaX) {
		super(spriteName);
		this.deltaX = newDeltaX;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.setXPosition(costume.getXPosition() + deltaX);
		
		
		costume.setMiddleX(costume.getMiddleX() + deltaX);
		
		return true;
	}

}
