/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
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
 package org.catrobat.html5Player.client;

import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.catrobat.html5Player.client.scripts.BroadcastScript;


public class MessageContainer {

	private TreeMap<String, Vector<BroadcastScript>> receiverMap = new TreeMap<String, Vector<BroadcastScript>>();

	public void addMessage(String message) {
		if (message == null || message.length() == 0) {
			return;
		}
		if (!receiverMap.containsKey(message)) {
			receiverMap.put(message, new Vector<BroadcastScript>());
		}
	}

	public void addMessage(String message, BroadcastScript script) {
		if (message == null || message.length() == 0 || script == null) {
			return;
		}
		if (receiverMap.containsKey(message)) {
			receiverMap.get(message).add(script);
		} else {
			Vector<BroadcastScript> receiverVec = new Vector<BroadcastScript>();
			receiverVec.add(script);
			receiverMap.put(message, receiverVec);
		}
	}

	public void deleteReceiverScript(String message, BroadcastScript script) {
		if (message == null || message.length() == 0 || script == null) {
			return;
		}
		if (receiverMap.containsKey(message)) {
			receiverMap.get(message).removeElement(script);
		}
	}

	public Vector<BroadcastScript> getReceiverOfMessage(String message) {
		if (message == null || message.length() == 0
				|| !receiverMap.containsKey(message)) {
			return new Vector<BroadcastScript>();
		}
		return receiverMap.get(message);
	}

	public Set<String> getMessages() {
		return receiverMap.keySet();
	}
	
	public void clear() {
		receiverMap.clear();
	}

}
