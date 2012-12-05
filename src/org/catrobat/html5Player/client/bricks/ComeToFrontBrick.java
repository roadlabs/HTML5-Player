package org.catrobat.html5Player.client.bricks;

import java.util.Collection;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;


public class ComeToFrontBrick extends Brick {

	public ComeToFrontBrick(String sprite) {
		super(sprite);
	}

	@Override
	public boolean execute(Sprite sprite) {
		Costume costume = sprite.getCostume();
		Collection<Sprite> sprites = Stage.getInstance().getSpriteManager().getSpriteList();

		if (costume.getZPosition() == Integer.MAX_VALUE) {
			return false;
		}
		int maxLayer = 0;
		for (Sprite sp : sprites) {
			int layer = sp.getCostume().getZPosition();
			if (layer > maxLayer)
				maxLayer = layer;
		}

		int layer = maxLayer + 1;
		if (layer <= costume.getZPosition()) {
			return false;
		}
		costume.setZPosition(layer);
		return true;
	}

}
