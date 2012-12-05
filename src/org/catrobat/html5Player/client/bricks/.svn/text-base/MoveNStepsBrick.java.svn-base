package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class MoveNStepsBrick extends Brick {

	private double steps;

	public MoveNStepsBrick(String sprite, double steps) {
		super(sprite);
		this.steps = steps;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		
		// the MINUS is important because canvas positive rotation is clockwise
		double radians = Math.toRadians(-costume.getRotation());

		int newXPosition = (int) Math.round(costume.getXPosition() + steps * Math.cos(radians));
		int newYPosition = (int) Math.round(costume.getYPosition() + steps * Math.sin(radians));

		costume.setXYPosition(newXPosition, newYPosition);

		
		costume.setMiddleX(Math.round(costume.getMiddleX() + steps * Math.cos(radians)));
		costume.setMiddleY(Math.round(costume.getMiddleY() + steps * Math.sin(radians)));
		
		return true;
	}

}
