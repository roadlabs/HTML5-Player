package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class SetGhostEffectBrick extends Brick {

	private double ghostEffectValue;
	
	public SetGhostEffectBrick(String spriteName, double ghostEffectValue) {
		super(spriteName);
		this.ghostEffectValue = ghostEffectValue;
	}

	@Override
	protected boolean execute(Sprite sprite) {
		
		double newAlphaValue = (100. - ghostEffectValue) / 100;
		
		sprite.getCostume().setAlphaValue(newAlphaValue);
		return true;
	} 
	
	
}
