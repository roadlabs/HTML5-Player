package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class ChangeGhostEffectByBrick extends Brick {
	
	private double ghostEffectValue;

	public ChangeGhostEffectByBrick(String spriteName, double ghostEffectValue) {
		super(spriteName);
		this.ghostEffectValue = ghostEffectValue;
	}

	@Override
	protected boolean execute(Sprite sprite) {
		
		double alphaValue = (- this.ghostEffectValue / 100);
		
		sprite.getCostume().changeAlphaValueBy(alphaValue);
		
		return true;
	}

}
