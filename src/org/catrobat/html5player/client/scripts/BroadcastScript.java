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

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.MessageContainer;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.threading.WaitCount;

public class BroadcastScript extends Script {

	public static final String SCRIPT_TYPE = "BroadcastScript";
	
	private String selectedMessage = "";
	
	public BroadcastScript(Sprite sprite, String name, String message) {
		super(sprite, SCRIPT_TYPE, name);
		this.selectedMessage = message;
		if (selectedMessage != null && selectedMessage.length() != 0) {
			Stage.getInstance().getMessageContainer()
					.addMessage(selectedMessage, this);
		}
	}
	
	public void executeBroadcast() {
		sprite.startScriptBroadcast(this);
	}

	public void executeBroadcastWait(WaitCount signaler) {
		sprite.startScriptBroadcastWait(this, signaler);
	}
	
	public void setBroadcastMessage(String selectedMessage) {
		MessageContainer messageContainer = Stage.getInstance()
				.getMessageContainer();
		messageContainer.deleteReceiverScript(this.selectedMessage, this);
		this.selectedMessage = selectedMessage;
		messageContainer.addMessage(this.selectedMessage, this);
	}

	public String getBroadcastMessage() {
		return this.selectedMessage;
	}
	
	public void resetBroadcastScript() {
		CatrobatDebug.debug("<<< resetBroadcastScript - sprite: " + getSprite().getName() + " >>>");
		this.currentBrick = 0;
		this.resetWorkDone();
		this.scriptFinished = false;
	}

}
