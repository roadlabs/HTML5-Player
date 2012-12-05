package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;

public class NoteBrick extends Brick {

	private String note = "";
	
	public NoteBrick(String spriteName) {
		super(spriteName);
	}
	
	public NoteBrick(String spriteName, String note) {
		super(spriteName);
		this.note = note;
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
		
		return true;
	}
	
	/**
	 * 
	 */
	public String getNote() {
		return this.note;
	}

}
