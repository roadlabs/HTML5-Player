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
package org.catrobat.html5player.client;

import java.util.ArrayList;

import org.catrobat.html5player.client.scripts.BroadcastScript;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class MessageContainerTest extends GWTTestCase {

	Stage stage;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public MessageContainerTest() {
		stage = Stage.getInstance();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.getSpriteManager().reset(); //important
		stage.getMessageContainer().clear(); //important
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testAddOnlyMessageGetReceiverOfMessage() {
		MessageContainer messageContainer = new MessageContainer();
		String message = "message for the container";
		messageContainer.addMessage(message);
		
		assertTrue(messageContainer.getReceiverOfMessage(message).isEmpty());
	}
	
	/**
	 * 
	 */
	public void testAddOnlyMessagesGetMessages() {
		MessageContainer messageContainer = new MessageContainer();
		String message1 = "first message for the container";
		String message2 = "second message";
		String message3 = "third message";
		
		messageContainer.addMessage(message1);
		messageContainer.addMessage(message2);
		messageContainer.addMessage(message3);
	
		ArrayList<String> messages = new ArrayList<String>(messageContainer.getMessages());
		
		assertEquals(3, messages.size());
		assertEquals(message1, messages.get(0));
		assertEquals(message2, messages.get(1));
		assertEquals(message3, messages.get(2));
	}
	
	/**
	 * 
	 */
	public void testAddMessages() {
		MessageContainer messageContainer = new MessageContainer();
		String message1 = "first message for the container";
		String message2 = null;
		String message3 = "";
		
		messageContainer.addMessage(message1);
		messageContainer.addMessage(message2);
		messageContainer.addMessage(message3);
	
		ArrayList<String> messages = new ArrayList<String>(messageContainer.getMessages());
		
		assertEquals(1, messages.size());
		assertEquals(message1, messages.get(0));
	}
	
	/**
	 * 
	 */
	public void testAddMessageWithScriptGetReceiverOfMessage() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript script = new BroadcastScript(sprite, scriptName, message);
		
		messageContainer.addMessage(message, script);

		assertEquals(script, messageContainer.getReceiverOfMessage(message).get(0));
	}
	
	/**
	 * 
	 */
	public void testAddMessageWithScriptsGetReceiverOfMessage() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String message = "broadcast-script message!";
		String scriptName1 = "BroadCastScript1";
		String scriptName2 = "BroadCastScript2";
		String scriptName3 = "BroadCastScript3";
		BroadcastScript script1 = new BroadcastScript(sprite, scriptName1, message);
		BroadcastScript script2 = new BroadcastScript(sprite, scriptName2, message);
		BroadcastScript script3 = new BroadcastScript(sprite, scriptName3, message);
		
		messageContainer.addMessage(message, script1);
		messageContainer.addMessage(message, script2);
		messageContainer.addMessage(message, script3);

		assertEquals(3, messageContainer.getReceiverOfMessage(message).size());
		assertEquals(script1, messageContainer.getReceiverOfMessage(message).get(0));
		assertEquals(script2, messageContainer.getReceiverOfMessage(message).get(1));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message).get(2));
	}
	
	/**
	 * 
	 */
	public void testAddMessagesWithScriptsGetReceiverOfMessage() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String message = "broadcast-script message!";
		String message2 = "another broadcast-script message!";
		String scriptName1 = "BroadCastScript1";
		String scriptName2 = "BroadCastScript2";
		String scriptName3 = "BroadCastScript3";
		BroadcastScript script1 = new BroadcastScript(sprite, scriptName1, message);
		BroadcastScript script2 = new BroadcastScript(sprite, scriptName2, message2);
		BroadcastScript script3 = new BroadcastScript(sprite, scriptName3, message2);
		
		messageContainer.addMessage(message, script1);
		messageContainer.addMessage(message2, script2);
		messageContainer.addMessage(message2, script3);

		assertEquals(1, messageContainer.getReceiverOfMessage(message).size());
		assertEquals(script1, messageContainer.getReceiverOfMessage(message).get(0));
		assertEquals(2, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script2, messageContainer.getReceiverOfMessage(message2).get(0));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(1));
	}
	
	/**
	 * 
	 */
	public void testAddInvalidMessagesWithScripts() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String message = "";
		String message2 = null;
		String scriptName1 = "BroadCastScript1";
		String scriptName2 = "BroadCastScript2";
		String scriptName3 = "BroadCastScript3";
		BroadcastScript script1 = new BroadcastScript(sprite, scriptName1, message);
		BroadcastScript script2 = new BroadcastScript(sprite, scriptName2, message2);
		BroadcastScript script3 = new BroadcastScript(sprite, scriptName3, message2);
		
		messageContainer.addMessage(message, script1);
		messageContainer.addMessage(message2, script2);
		messageContainer.addMessage(message2, script3);

		assertTrue(messageContainer.getReceiverOfMessage(message).isEmpty());
		assertTrue(messageContainer.getReceiverOfMessage(message2).isEmpty());
	}
	
	/**
	 * 
	 */
	public void testDeleteReceiverOfMessage() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String message = "broadcast-script message!";
		String message2 = "another broadcast-script message!";
		String scriptName1 = "BroadCastScript1";
		String scriptName2 = "BroadCastScript2";
		String scriptName3 = "BroadCastScript3";
		BroadcastScript script1 = new BroadcastScript(sprite, scriptName1, message);
		BroadcastScript script2 = new BroadcastScript(sprite, scriptName2, message2);
		BroadcastScript script3 = new BroadcastScript(sprite, scriptName3, message2);
		
		messageContainer.addMessage(message, script1);
		messageContainer.addMessage(message2, script2);
		messageContainer.addMessage(message2, script3);
		
		//
		messageContainer.deleteReceiverScript(message, script1);

		assertTrue(messageContainer.getReceiverOfMessage(message).isEmpty());
		assertEquals(2, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script2, messageContainer.getReceiverOfMessage(message2).get(0));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(1));
		
		//
		messageContainer.deleteReceiverScript(message2, script2);
		
		assertEquals(1, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(0));
		
		//
		messageContainer.deleteReceiverScript(message2, script3);
		
		assertEquals(2, messageContainer.getMessages().size());
	}
	
	/**
	 * 
	 */
	public void testDeleteReceiverOfMessageInvalidParameters() {
		MessageContainer messageContainer = new MessageContainer();
		
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String message = "broadcast-script message!";
		String message2 = "another broadcast-script message!";
		String scriptName1 = "BroadCastScript1";
		String scriptName2 = "BroadCastScript2";
		String scriptName3 = "BroadCastScript3";
		BroadcastScript script1 = new BroadcastScript(sprite, scriptName1, message);
		BroadcastScript script2 = new BroadcastScript(sprite, scriptName2, message2);
		BroadcastScript script3 = new BroadcastScript(sprite, scriptName3, message2);
		
		messageContainer.addMessage(message, script1);
		messageContainer.addMessage(message2, script2);
		messageContainer.addMessage(message2, script3);
		
		//
		messageContainer.deleteReceiverScript(null, script1);
		messageContainer.deleteReceiverScript("", script1);

		assertEquals(1, messageContainer.getReceiverOfMessage(message).size());
		assertEquals(2, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script2, messageContainer.getReceiverOfMessage(message2).get(0));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(1));
		
		//message and script don't belong together
		messageContainer.deleteReceiverScript(message2, script1);
		
		assertEquals(1, messageContainer.getReceiverOfMessage(message).size());
		assertEquals(2, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script2, messageContainer.getReceiverOfMessage(message2).get(0));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(1));
		
		//
		messageContainer.deleteReceiverScript(message2, null);
		
		assertEquals(1, messageContainer.getReceiverOfMessage(message).size());
		assertEquals(2, messageContainer.getReceiverOfMessage(message2).size());
		assertEquals(script2, messageContainer.getReceiverOfMessage(message2).get(0));
		assertEquals(script3, messageContainer.getReceiverOfMessage(message2).get(1));
	}
	
}
