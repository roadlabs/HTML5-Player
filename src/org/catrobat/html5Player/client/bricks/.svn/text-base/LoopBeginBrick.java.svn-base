package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public abstract class LoopBeginBrick extends Brick {

	protected Sprite sprite;
	protected LoopEndBrick loopEndBrick = null;
	
	protected LoopBeginBrick(String spriteName) {
		super(spriteName);
	}
	
	//##########################################################################
	
	@Override
	protected abstract boolean execute(Sprite sprite);
	
	//##########################################################################
	
	/**
	 * 
	 * @return
	 */
	public LoopEndBrick getLoopEndBrick() {
		return this.loopEndBrick;
	}
	
	/**
	 * 
	 */
	public void setLoopEndBrick(LoopEndBrick loopEndBrick) {
		this.loopEndBrick = loopEndBrick;
	}
}
