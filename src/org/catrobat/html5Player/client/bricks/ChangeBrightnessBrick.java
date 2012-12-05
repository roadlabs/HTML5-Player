package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class ChangeBrightnessBrick extends Brick {
	
	private double brightness;

	public ChangeBrightnessBrick(String spriteName, double brightness) {
		super(spriteName);
		this.brightness = brightness / 100.0;
	}

	@Override
	protected boolean execute(Sprite sprite) {
		
		sprite.getCostume().changeBrightnessValueBy(this.brightness);
		
		return true;
	}

}
