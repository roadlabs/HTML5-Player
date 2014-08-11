/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2014 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client.scripts;

import java.util.List;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.bricks.Brick;


public class WhenScript extends Script {

	public static final String SCRIPT_TYPE = "TouchScript";

	public WhenScript(Sprite sprite, String name) {
		super(sprite, SCRIPT_TYPE, name);
	}

	public WhenScript(Sprite sprite, String name, List<Brick> list) {
		super(sprite, SCRIPT_TYPE, name);
		if (list != null) {
			this.brickList.addAll(list);
		}
	}
	
	public void resetWhenScript() {
		CatrobatDebug.debug("<<< resetWhenScript - sprite: " + getSprite().getName() + " >>>");
		this.currentBrick = 0;
		this.resetWorkDone();
		this.scriptFinished = false;
	}

}
