package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class PlaySoundBrick extends Brick {

	private String soundId;

	public PlaySoundBrick(String sprite, String soundId) {
		super(sprite);
		this.soundId = soundId;
	}

	@Override
	public boolean execute(Sprite sprite) {
		sprite.playSound(soundId);
		return true;
	}

}
