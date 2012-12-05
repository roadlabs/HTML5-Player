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
