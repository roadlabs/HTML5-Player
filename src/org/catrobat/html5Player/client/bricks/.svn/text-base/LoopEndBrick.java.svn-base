package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.scripts.Script;

public class LoopEndBrick extends Brick {

	public static final int FOREVER = -1;

	private Sprite sprite;
	private LoopBeginBrick loopBeginBrick;
	private int timesToRepeat;
	
	public LoopEndBrick(String spriteName, LoopBeginBrick loopStartingBrick) {
		super(spriteName);
		sprite = getSprite();
		this.loopBeginBrick = loopStartingBrick;
	}
	
	@Override
	protected boolean execute(Sprite sprite) {

		if(timesToRepeat == FOREVER) {
			Script script = getScript();
			script.setCurrentBrick(script.getBrickList().indexOf(loopBeginBrick));
		} 
		else if (--timesToRepeat > 0) {
			Script script = getScript();
			script.setCurrentBrick(script.getBrickList().indexOf(loopBeginBrick));
		}
		
		CatrobatDebug.on();
		CatrobatDebug.console("loopEndBrick - timesToRepeat: " + timesToRepeat);
		CatrobatDebug.off();
		
		return true;
	}
	
	//##########################################################################
	
	/**
	 * 
	 * @return
	 */
	public Script getScript() {
		for(int i = 0; i < sprite.getNumberOfScripts(); i++) {
			Script script = sprite.getScript(i);
			
			if(script.getBrickList().contains(this))
				return script;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param timesToRepeat
	 */
	public void setTimesToRepeat(int timesToRepeat) {
		this.timesToRepeat = timesToRepeat;
	}
	
	/**
	 * 
	 * @return
	 */
	public LoopBeginBrick getLoopBeginBrick() {
		return this.loopBeginBrick;
	}
	
	//##########################################################################
	
	/**
	 * FOR UNIT TESTING
	 */
	public int getTimesToRepeat() {
		return this.timesToRepeat; 
	}

}
