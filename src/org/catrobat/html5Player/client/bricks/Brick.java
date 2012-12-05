package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

public abstract class Brick {

	private final String spriteName;

	protected Brick(String spriteName) {
		this.spriteName = spriteName;
	}

	public final Sprite getSprite() {
		return Stage.getInstance().getSpriteManager().getSprite(spriteName, false);
	}

	public final boolean execute() {

		Sprite sprite = getSprite();
		if (sprite != null) {
			return execute(sprite);
		}

		return false;
	}

	protected abstract boolean execute(Sprite sprite);

}
