package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.scripts.Script;

public class RepeatBrick extends LoopBeginBrick {

	private int timesToRepeat;
	
	public RepeatBrick(String spriteName, int timesToRepeat) {
		super(spriteName);
		
		this.timesToRepeat = timesToRepeat;
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
		
		if(timesToRepeat <= 0) {
			Script script = loopEndBrick.getScript();
			script.setCurrentBrick(script.getBrickList().indexOf(loopEndBrick));
			return true;
		}
		
		loopEndBrick.setTimesToRepeat(timesToRepeat);
		
		return true;
	}

	
	/**
	 * FOR UNIT TESTING
	 */
	public int getTimesToRepeat() {
		return this.timesToRepeat;
	}
}
