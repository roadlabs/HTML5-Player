package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class TurnLeftBrick extends Brick {

	private int degrees;

	public TurnLeftBrick(String sprite, int degrees) {
		super(sprite);
		this.degrees = degrees;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		costume.setRotation( (costume.getRotation() % 360) + (double) degrees);
		return false;
	}

}
