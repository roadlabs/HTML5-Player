/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.scripts.Script;

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
		
		CatrobatDebug.debug("loopEndBrick - timesToRepeat: " + timesToRepeat);
		
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
