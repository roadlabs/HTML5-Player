package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;

public class SetXBrick extends Brick {

	public int xPosition;

	public SetXBrick(String sprite, int x) {
		super(sprite);
		xPosition = x;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
//		System.out.println("############# X = " + xPosition + " " + sprite.getName());
//		System.out.println("############ X Mitte = " + Stage.getInstance().getStageMiddleX());
		costume.setXPosition(xPosition + Stage.getInstance().getStageMiddleX());
		
		
		costume.setMiddleX(xPosition);
		
		return true;
	}

}
