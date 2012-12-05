package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;

public class PlaceAtBrick extends Brick {

	public int xPosition;
	public int yPosition;

	public PlaceAtBrick(String sprite, int x, int y) {
		super(sprite);
		xPosition = x;
		yPosition = y;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		// TODO fix this with the coordinate system, refactor whole program
		
		costume.setXPosition(xPosition + Stage.getInstance().getStageMiddleX());
		costume.setYPosition(-yPosition + Stage.getInstance().getStageMiddleY());
		
		
		costume.setMiddleX(xPosition);
		costume.setMiddleY(-yPosition);
		
		return true;
	}

}
