package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class ChangeYByBrick extends Brick {

	private int deltaY;

	public ChangeYByBrick(String sprite, int deltaY) {
		super(sprite);
		this.deltaY = -deltaY;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();

		costume.setMiddleY(costume.getMiddleY() + deltaY);
		
		int yPosition = (int) costume.getYPosition();
//		int tempDeltaY = deltaY;
//		
//		if (tempDeltaY == Integer.MIN_VALUE) {
//			tempDeltaY = Integer.MAX_VALUE;
//		} else if (tempDeltaY == Integer.MAX_VALUE) {
//			tempDeltaY = Integer.MIN_VALUE;
//		} 
//		
//		if (yPosition > 0 && tempDeltaY > 0 && yPosition + tempDeltaY < 0) {
//			yPosition = Integer.MAX_VALUE;
//		} 
//		else if (yPosition < 0 && tempDeltaY < 0 && yPosition + tempDeltaY > 0) {
//			yPosition = Integer.MIN_VALUE;
//		} 
//		else {
//			yPosition += tempDeltaY;
//		}
		
		yPosition += deltaY;
		
		costume.setXYPosition(costume.getXPosition(), yPosition);
		
		return true;
	}

}
