package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;

public class SetYBrick extends Brick {

	public int yPosition;

	public SetYBrick(String sprite, int y) {
		super(sprite);
		yPosition = y;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();		
		int setYPosition = Stage.getInstance().getStageMiddleY() - yPosition;
//		System.out.println("XXX SetYBrick " + setYPosition +" for " + sprite.getName());
		costume.setYPosition(setYPosition);
		
		costume.setMiddleY(-yPosition);
		
		return true;
	}

}
