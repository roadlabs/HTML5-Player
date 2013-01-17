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
package org.catrobat.html5Player.client.bricks;

import java.util.Vector;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;


public class BroadcastBrick extends Brick {

	private String message = "";

	public BroadcastBrick(String sprite, String message) {
		super(sprite);
		this.message = message;
	}

	@Override
	public boolean execute(Sprite sprite) {
		final Vector<BroadcastScript> receiver = Stage.getInstance().getMessageContainer().getReceiverOfMessage(message);
		
		if (receiver == null) {
			System.out.println("receiver == null");
			return false;
		}
		if (receiver.size() == 0) {
			System.out.println("no receivers for message: " + message);
			return true;
		}
		for (BroadcastScript receiverScript : receiver) {
			
			receiverScript.resetBroadcastScript(); ///TODO ???????
			
			receiverScript.executeBroadcast();
		}
		
		return true;
	}

}
