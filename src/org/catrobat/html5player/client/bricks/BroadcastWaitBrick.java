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

import java.util.Vector;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.Script;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;
import org.catrobat.html5player.client.threading.WaitCount;


public class BroadcastWaitBrick extends Brick {

	private String message = "";
	private Script script;

	public BroadcastWaitBrick(String sprite, String message, Script script) {
		super(sprite);
		this.message = message;
		this.script = script;
	}

	@Override
	public boolean execute(Sprite sprite) {
		final Vector<BroadcastScript> receiver = Stage.getInstance().getMessageContainer().getReceiverOfMessage(message);

		CatrobatDebug.debug("BroadcastWait - message: " + message);
		
		if (receiver == null) {
			CatrobatDebug.debug("receiver is null");
			return false;
		}
		
		if (receiver.size() == 0) {
			CatrobatDebug.debug("no receivers");
			return true;
		}

		CatThread executiveThread = CatScheduler.get().getThread(script.getExecutor());
		
		WaitCount signaler = new WaitCount(executiveThread);
		
		for (BroadcastScript receiverScript : receiver) {
			
			receiverScript.resetBroadcastScript();
			
			receiverScript.executeBroadcastWait(signaler);
			
			CatrobatDebug.debug("BroadcastWait - receiver, script: " + receiverScript.getName() + ", sprite: " + receiverScript.getSprite().getName());
			
		}
		
		executiveThread.awaitSignal(signaler);
		
		return true;
	}

}
