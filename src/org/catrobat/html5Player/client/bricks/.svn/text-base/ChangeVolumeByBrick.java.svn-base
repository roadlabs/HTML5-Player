package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class ChangeVolumeByBrick extends Brick {

	private double volume;

	public ChangeVolumeByBrick(String sprite, double volume) {
		super(sprite);
		this.volume = volume;
	}

	@Override
	public boolean execute(Sprite sprite) {
		
		double currentSpriteVolume = sprite.getVolume();
		currentSpriteVolume += this.volume;
		
		if(currentSpriteVolume < 0.0) {
			currentSpriteVolume = 0.0;
		}
		else if(currentSpriteVolume > 100.0) {
			currentSpriteVolume = 100.0;
		}
		
		sprite.setVolume(currentSpriteVolume);
		return true;
	}

}
