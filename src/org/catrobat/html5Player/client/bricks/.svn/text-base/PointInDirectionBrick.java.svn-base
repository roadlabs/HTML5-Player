package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.common.Costume;

public class PointInDirectionBrick extends Brick {

	public static final int DIRECTION_RIGHT = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_UP = 2;
	public static final int DIRECTION_DOWN = 3;

	//TODO: remove directions, also from the constructor and parser
	//
	private static final double[] directions = { 90.0, -90.0, 0.0, 180.0 };

	int direction;
	
	double degrees;

	public PointInDirectionBrick(String sprite, int direction, double degrees) {
		super(sprite);
		this.direction = direction;
		
		this.degrees = degrees;
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		
		double rotation = -degrees + 90.0;
		costume.setRotation(rotation);
		
		return true;
	}

}
