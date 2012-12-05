package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class SetVolumeToBrick extends Brick {
	private double volume;

	public SetVolumeToBrick(String sprite, double volume) {
		super(sprite);
		this.volume = volume;
	}

	@Override
	public boolean execute(Sprite sprite) {
		
		if(this.volume < 0.0) {
			this.volume = 0.0;
		}
		else if(this.volume > 100.0) {
			this.volume = 100.0;
		}
		
		sprite.setVolume(volume);
		
		return true;
	}

}
