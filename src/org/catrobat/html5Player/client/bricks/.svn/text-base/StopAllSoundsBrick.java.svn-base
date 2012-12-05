package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

public class StopAllSoundsBrick extends Brick {

	public StopAllSoundsBrick(String sprite) {
		super(sprite);
	}

	@Override
	public boolean execute(Sprite sprite) {
//		sprite.stopSound();
		Stage.getInstance().getSpriteManager().stopAllSounds();
		
		return true;
	}

}
