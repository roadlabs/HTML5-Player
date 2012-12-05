package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class GoNStepsBackBrick extends Brick {

	private int steps;

	public GoNStepsBackBrick(String sprite, int steps) {
		super(sprite);
		this.steps = steps;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();

		if(steps < 0)
			return false;
		
		if(costume.getZPosition() != Integer.MIN_VALUE) {
			costume.setZPosition(costume.getZPosition() - steps);
		}
		
		return true;
	}

}
