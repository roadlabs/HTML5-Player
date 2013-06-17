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

import org.catrobat.html5player.client.bricks.*;
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.scripts.WhenScript;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.TextArea;

public class ParserTest extends GWTTestCase {
	
	private Stage stage;
	private Scene scene;
	
	private String xmlStringRumpBegin;
	private String xmlStringRumpEnd;
	private String projectName = "TestProject";
	private int screenHeight = 480;
	private int screenWidth = 320;
	
	SpriteManager spriteManager;
	
	public ParserTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		
		spriteManager = stage.getSpriteManager();
		
		xmlStringRumpBegin = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<program>" + 
				"<header>" +
			    "<applicationBuildName></applicationBuildName>" +
			    "<applicationBuildNumber>0</applicationBuildNumber>" +
			    "<applicationName>Catroid</applicationName>" +
			    "<applicationVersion>0.7.0beta</applicationVersion>" +
			    "<catrobatLanguageVersion>0.6</catrobatLanguageVersion>" +
			    "<deviceName>GT-S5830</deviceName>" +
				"<programName>" + projectName + "</programName>" +
				"<screenHeight>" + screenHeight + "</screenHeight>" +
				"<screenWidth>" + screenWidth + "</screenWidth>"+
				"</header>";
		
		xmlStringRumpEnd = "</program>";
		
		
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	public void gwtSetUp() {		
		scene.createScene(100, 100);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
		
		TextArea logBox = new TextArea();
		logBox.setSize("400px", "10px");
		logBox.setVisible(false);
		stage.setLogBox(logBox);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.setLogBox(null);
		
		spriteManager.reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * Helper
	 * @param filename
	 * @param name
	 * @return xml string of <look> Node
	 */
	public String lookXMLString(String filename, String name) {
		return "<look>" +
				"<fileName>" + filename + "</fileName>" +
				"<name>" + name + "</name>" +
				"</look>";
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testParseXMLScreenResolution() {
		String xmlString = xmlStringRumpBegin + "<objectList></objectList>" +
					       xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Canvas rootCanvas = stage.getCanvas();
		
		assertEquals(screenHeight, rootCanvas.getCoordinateSpaceHeight());
		assertEquals(screenWidth, rootCanvas.getCoordinateSpaceWidth());
	}
	
	/**
	 * 
	 */
	public void testParseXMLSpriteWithlooks() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin + 
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList/>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);

		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		assertTrue(sprite instanceof Sprite);
		assertEquals(2, sprite.getLooks().size());
		
		Look look1 = sprite.getLook(lookName);
		Look look2 = sprite.getLook(lookName2);
		assertNotNull(look1);
		assertNotNull(look2);
		assertEquals(fileNamelook, look1.getLookData().getFilename());
		assertEquals(fileNamelook2, look2.getLookData().getFilename());
	}
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void testParseXMLStartScriptOneBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(1, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof StartScript);
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLMultipleStartScriptsWithOneBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"<startScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(2, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof StartScript);
		assertTrue(sprite.getScript(1) instanceof StartScript);
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		assertEquals(1, sprite.getScript(1).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
		assertTrue(sprite.getScript(1).getBrick(0) instanceof SetLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLWhenScriptOneBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<whenScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</whenScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(1, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof WhenScript);
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLMultipleWhenScriptsWithOneBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<whenScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</whenScript>" +
			"<whenScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</whenScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(2, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof WhenScript);
		assertTrue(sprite.getScript(1) instanceof WhenScript);
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		assertEquals(1, sprite.getScript(1).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
		assertTrue(sprite.getScript(1).getBrick(0) instanceof SetLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLBroadcastScriptWithOneBrick() {
		
		String receivedMessage = "Next";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<broadcastScript>" +
			"<brickList>" +
			"<hideBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</hideBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</broadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(1, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof BroadcastScript);
		
		BroadcastScript broadcastScript = (BroadcastScript)sprite.getScript(0);
		
		assertEquals(receivedMessage, broadcastScript.getBroadcastMessage());
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof HideBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLMultipleBroadcastScriptsWithOneBrick() {
		
		String receivedMessage = "Next";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<broadcastScript>" +
			"<brickList>" +
			"<hideBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</hideBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</broadcastScript>" +
			"<broadcastScript>" +
			"<brickList>" +
			"<hideBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</hideBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</broadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(2, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof BroadcastScript);
		assertTrue(sprite.getScript(1) instanceof BroadcastScript);
		
		BroadcastScript broadcastScript = (BroadcastScript)sprite.getScript(0);
		BroadcastScript broadcastScript2 = (BroadcastScript)sprite.getScript(1);
		
		assertEquals(receivedMessage, broadcastScript.getBroadcastMessage());
		assertEquals(receivedMessage, broadcastScript2.getBroadcastMessage());
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		assertEquals(1, sprite.getScript(1).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof HideBrick);
		assertTrue(sprite.getScript(1).getBrick(0) instanceof HideBrick);
	}
	
	/**
	 *
	 */
	public void testParseXMLAllScripts() {
		
		String receivedMessage = "Next";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String fileNamelook2 = "92382349283_look2";
		String lookName2 = "look2";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"<whenScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</whenScript>" +
			"<broadcastScript>" +
			"<brickList>" +
			"<hideBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</hideBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</broadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertEquals(3, sprite.getNumberOfScripts());
		
		assertTrue(sprite.getScript(0) instanceof StartScript);
		assertTrue(sprite.getScript(1) instanceof WhenScript);
		assertTrue(sprite.getScript(2) instanceof BroadcastScript);
		
		assertEquals(1, sprite.getScript(0).getBrickList().size());
		assertEquals(1, sprite.getScript(1).getBrickList().size());
		assertEquals(1, sprite.getScript(2).getBrickList().size());
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
		assertTrue(sprite.getScript(1).getBrick(0) instanceof SetLookBrick);
		assertTrue(sprite.getScript(2).getBrick(0) instanceof HideBrick);
	}
	
	//##########################################################################
	
	/*
	  * Parser:
	 *  - Bricks:
	 *  	(x) no test
	 *  	(o) tested
	 *  	(-) not in parser
	 * 
	 * Setlook					(o)			
	 * WaitBrick					(o)					
	 * PlaySound					(x)
	 * ChangeVolumeBy				(o)
	 * SetVolumeTo					(o)	
	 * PlaceAt						(o)
	 * ChangeSizeByN				(o)
	 * SetY							(o)
	 * SetX							(o)
	 * ChangeXBy					(o)
	 * ChangeYBy					(o)
	 * Hide							(o)
	 * Show							(o)
	 * StopAllSounds				(o)
	 * TurnLeft						(o)
	 * TurnRight					(o)
	 * PointInDirection				(o)
	 * GoNStepsBack					(o)
	 * ComeToFront					(o)
	 * GlideTo						(o)
	 * SetSizeTo					(o)
	 * Broadcast					(o)
	 * BroadcastWait				(o)
	 * MoveNSteps					(o)
	 * Nextlook					(o)
	 * Repeat						(o)
	 * Forever						(o)
	 * LoopEnd						(o)
	 * Note							(o)
	 * SetGhostEffect				(o)
	 * ChangeGhostEffect			(o)
	 * IfOnEdgeBounce				(o)
	 * PointTo						(o)
	 * ClearGraphicEffect			(o)
	 * SetBrightness				(o)
	 * ChangeBrightness				(o)
	 * 
	 */
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void testParseXMLsetLookBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setLookBrick>" +
			"<look reference=\"../../../../../lookList/look\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</setLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLWaitBrick() {
		int timeToWait = 1000;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<waitBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<timeToWaitInSeconds>" + timeToWait + "</timeToWaitInSeconds>" +
			"</waitBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof WaitBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPlaceAtBrick() {
		int xPosition = 0;
		int yPosition = 0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<placeAtBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<xPosition>" + xPosition + "</xPosition>" +
			"<yPosition>" + yPosition + "</yPosition>" +
			"</placeAtBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof PlaceAtBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeSizeByNBrick() {
		int size = 10;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeSizeByNBrick>" +
			"<size>" + size + "</size>" +
			"<object reference=\"../../../../..\"/>" +
			"</changeSizeByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeSizeByNBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetYBrick() {
		int yPosition = 10;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setYBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<yPosition>" + yPosition + "</yPosition>" +
			"</setYBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetYBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetXBrick() {
		int xPosition = 10;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setXBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<xPosition>" + xPosition + "</xPosition>" +
			"</setXBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetXBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLHideBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<hideBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</hideBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof HideBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLShowBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<showBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</showBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ShowBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLMoveNStepsBrick() {
		
		double steps = 50.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<moveNStepsBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<steps>" + steps + "</steps>" + 
			"</moveNStepsBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof MoveNStepsBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLGoNStepsBackBrick() {
		
		int steps = 3;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<goNStepsBackBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<steps>" + steps + "</steps>" + 
			"</goNStepsBackBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof GoNStepsBackBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLComeToFrontBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<comeToFrontBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</comeToFrontBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ComeToFrontBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLGlideToBrick() {
		int duration = 100;
		int xDestination = 800;
		int yDestination = 0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<glideToBrick>" +
			"<durationInSeconds>" + duration + "</durationInSeconds>" +
			"<object reference=\"../../../../..\"/>" +
			"<xDestination>" + xDestination + "</xDestination>" +
			"<yDestination>" + yDestination + "</yDestination>" +
			"</glideToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof GlideToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeXByNBrick() {
		int xMovement = 100;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeXByNBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<xMovement>" + xMovement + "</xMovement>" +
			"</changeXByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeXByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeYByNBrick() {
		int yMovement = 100;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeYByNBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<yMovement>" + yMovement + "</yMovement>" +
			"</changeYByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeYByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLTurnLeftBrick() {
		double degrees = 10.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<turnLeftBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</turnLeftBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof TurnLeftBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLTurnRightBrick() {
		double degrees = 10.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<turnRightBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</turnRightBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof TurnRightBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPointInDirectionBrick() {
		double degrees = 10.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<pointInDirectionBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</pointInDirectionBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof PointInDirectionBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetSizeToBrick() {
		double size = 100.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<object reference=\"../../../../..\"/>" +
			"</setSizeToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetSizeToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetVolumeToBrick() {
		double volume = 10.0;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setVolumeToBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<volume>" + volume + "</volume>" +
			"</setVolumeToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetVolumeToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeVolumeByNBrick() {
		double volume = 25.0f;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeVolumeByNBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<volume>" + volume + "</volume>" +
			"</changeVolumeByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeVolumeByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLNextlookBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<nextLookBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</nextLookBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		assertTrue(sprite.getScript(0).getBrick(0) instanceof NextLookBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPlaySoundBrick() {
/*		String soundFileName = "68223C25ABEFABA96FD2BEC8C44D5A12_Aufnahme.mp3";
		String soundName = "Aufnahme";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<playSoundBrick>" +
			"<soundInfo>" +
			"<fileName>" + soundFileName + "</fileName>" +
			"<name>" + soundName + "</name>" +
			"</soundInfo>" +
			"<object reference=\"../../../../..\"/>" +
			"</playSoundBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();*/
		
		//geht bei Unit-Test nicht, wegen addSound(...)
//		parser.parseXML(spriteManager, xmlString);
//		
//		Sprite sprite = spriteManager.getSprite(spriteName, false);
//		
//		assertTrue(sprite.getScript(0).getBrick(0) instanceof PlaySoundBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPlaySoundBrickWithReference() {
		
		/*String soundFileName = "68223C25ABEFABA96FD2BEC8C44D5A12_Aufnahme.mp3";
		String soundName = "Aufnahme";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<playSoundBrick>" +
			"<soundInfo>" +
			"<fileName>" + soundFileName + "</fileName>" +
			"<name>" + soundName + "</name>" +
			"</soundInfo>" +
			"<object reference=\"../../../../..\"/>" +
			"</playSoundBrick>" +
			"<playSoundBrick>" +
			"<soundInfo reference=\"../../playSoundBrick/soundInfo\"/>" +
			"<object reference=\"../../../../..\"/>" +
			"</playSoundBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();*/
		
		//geht bei Unit-Test nicht, wegen addSound(...)
//		parser.parseXML(spriteManager, xmlString);
//		
//		Sprite sprite = spriteManager.getSprite(spriteName, false);
//		
//		assertTrue(sprite.getScript(0).getBrick(0) instanceof PlaySoundBrick);
//		assertTrue(sprite.getScript(0).getBrick(1) instanceof PlaySoundBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLStopAllSoundsBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<stopAllSoundsBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</stopAllSoundsBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof StopAllSoundsBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLBroadcastBrick() {
		
		String broadcastMessage = "Next";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<broadcastBrick>" +
			"<broadcastMessage>" + broadcastMessage + "</broadcastMessage>" +
			"<object reference=\"../../../../..\"/>" +
			"</broadcastBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof BroadcastBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLBroadcastWaitBrick() {
		
		String broadcastMessage = "Next";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<broadcastWaitBrick>" +
			"<broadcastMessage>" + broadcastMessage + "</broadcastMessage>" +
			"<object reference=\"../../../../..\"/>" +
			"</broadcastWaitBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof BroadcastWaitBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLNoteBrick() {
		
		String note = "Correct";
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<noteBrick>" +
			"<note>" + note + "</note>" +
			"<object reference=\"../../../../..\"/>" +
			"</noteBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof NoteBrick);
		
		NoteBrick noteBrick = (NoteBrick)sprite.getScript(0).getBrick(0);
		
		assertEquals(note, noteBrick.getNote());
	}
	
	/**
	 * 
	 */
	public void testParseXMLRepeatBrick() {
		int timesToRepeat = 3;
		
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<repeatBrick>" +
			"<loopEndBrick>" +
			"<loopBeginBrick class=\"repeatBrick\" reference=\"../..\"/>" +
			"<object reference=\"../../../../../..\"/>" +
			"</loopEndBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<timesToRepeat>" + Integer.toString(timesToRepeat) + "</timesToRepeat>" +
			"</repeatBrick>" +
			"<loopEndBrick reference=\"../repeatBrick/loopEndBrick\"/>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof RepeatBrick);
		
		RepeatBrick repeatBrick = (RepeatBrick)startScript.getBrick(0);
		
		assertEquals(timesToRepeat, repeatBrick.getTimesToRepeat());
		assertNotNull(repeatBrick.getLoopEndBrick());
		assertEquals(repeatBrick.getLoopEndBrick(), startScript.getBrick(1));
	}
	
	/**
	 * 
	 */
	public void testParseXMLForeverBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<foreverBrick>" +
			"<loopEndBrick>" +
			"<loopBeginBrick class=\"foreverBrick\" reference=\"../..\"/>" +
			"<object reference=\"../../../../../..\"/>" +
			"</loopEndBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</foreverBrick>" +
			"<loopEndBrick reference=\"../repeatBrick/loopEndBrick\"/>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof ForeverBrick);
		
		ForeverBrick repeatBrick = (ForeverBrick)startScript.getBrick(0);
		
		assertNotNull(repeatBrick.getLoopEndBrick());
		assertEquals(repeatBrick.getLoopEndBrick(), startScript.getBrick(1));
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetGhostEffectBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		double transparency = 10.0;
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setGhostEffectBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<transparency>" + transparency + "</transparency>" +
			"</setGhostEffectBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof SetGhostEffectBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeGhostEffectByNBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		double changeGhostEffect = 0.5;
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeGhostEffectByNBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<changeGhostEffect>" + changeGhostEffect + "</changeGhostEffect>" +
			"</changeGhostEffectByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof ChangeGhostEffectByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLIfOnEdgeBounceBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<ifOnEdgeBounceBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</ifOnEdgeBounceBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof IfOnEdgeBounceBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPointToBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNamelook2 = "91223QE849283_look2";
		String lookName2 = "look2";
		
		double size = 100.0;
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<pointToBrick>" +
			"<pointedObject>" +
			"<lookList>" +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<object reference=\"../../../../..\"/>" +
			"</setSizeToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"<object reference=\"../../../../..\"/>" +
			"</pointedObject>" +
			"</pointToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof PointToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLPointToBrickWithReference() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNamelook2 = "91223QE849283_look2";
		String lookName2 = "look2";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<pointToBrick>" +
			"<pointedObject reference=\"../../../../../../object[2]\">" +
			"<object reference=\"../../../../..\"/>" +
			"</pointedObject>" +
			"</pointToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<showBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"</showBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof PointToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLClearGraphicEffectBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String xmlString = xmlStringRumpBegin +
				"<objectList>"+
				"<object>" +
				"<lookList>" +
				lookXMLString(fileNamelook, lookName) +
				"</lookList>" +
				"<name>" + spriteName + "</name>" +
				"<scriptList>" +
				"<startScript>" +
				"<brickList>" +
				"<clearGraphicEffectBrick>" +
				"<object reference=\"../../../../..\"/>" +
				"</clearGraphicEffectBrick>" +
				"</brickList>" +
				"<object reference=\"../../..\"/>" +
				"</startScript>" +
				"</scriptList>" +
				"<soundList/>" +
				"</object>" +
				"</objectList>" +
				xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof ClearGraphicEffectBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLSetBrightnessBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		double brightness = 300.0;
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setBrightnessBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<brightness>" + brightness + "</brightness>" +
			"</setBrightnessBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof SetBrightnessBrick);
	}
	
	/**
	 * TODO: tag-name for brightness value unknown
	 */
	public void testParseXMLChangeBrightnessBrick() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		double brightness = -30.0;
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<changeBrightnessByNBrick>" +
			"<object reference=\"../../../../..\"/>" +
			"<changeBrightness>" + brightness + "</changeBrightness>" +
			"</changeBrightnessByNBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof ChangeBrightnessBrick);
	}
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void testParseXMLSpriteReference() {
		String spriteName = "TestSprite";
		String fileNamelook = "923QE849283_look";
		String lookName = "look1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNamelook2 = "91223QE849283_look2";
		String lookName2 = "look2";
		
		double size = 100.0;
		
		String reference="../object/scriptList/startScript/brickList/pointToBrick/pointedObject";
		
		String xmlString = xmlStringRumpBegin +
			"<objectList>"+
			"<object>" +
			"<lookList>" +
			lookXMLString(fileNamelook, lookName) +
			"</lookList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<pointToBrick>" +
			"<pointedObject>" +
			"<lookList>" +
			lookXMLString(fileNamelook2, lookName2) +
			"</lookList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<startScript>" +
			"<brickList>" +
			"<setSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<object reference=\"../../../../..\"/>" +
			"</setSizeToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</pointedObject>" +
			"<object reference=\"../../../../..\"/>" +
			"</pointToBrick>" +
			"</brickList>" +
			"<object reference=\"../../..\"/>" +
			"</startScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</object>" +
			"<object reference=\"" + reference + "\"/>" +
			"</objectList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		Sprite pointedSprite = spriteManager.getSprite(pointedSpriteName, false);
		
		StartScript startScript = (StartScript)sprite.getScript(0);
		StartScript pointedStartScript = (StartScript)pointedSprite.getScript(0);
		
		assertTrue(startScript.getBrick(0) instanceof PointToBrick);
		assertTrue(pointedStartScript.getBrick(0) instanceof SetSizeToBrick);
	}
	
	
    public void testSetVariableBrick() {
      String spriteName = "TestSprite";
      String fileNamelook = "923QE849283_look";
      String lookName = "look1";
      
      double oldValue = 10.0;
      double targetValue = 30.0;
      
      String xmlString = xmlStringRumpBegin +
          "<objectList>"+
          "<object>" +
          "<lookList>" +
          lookXMLString(fileNamelook, lookName) +
          "</lookList>" +
          "<name>" + spriteName + "</name>" +
          "<scriptList>" +
          "<startScript>" +
          "<brickList>" +
          "<setVariableBrick>"+
              "<object reference=\"../../../../..\"/>"+
              "<userVariable>"+
                "<name>testname</name>"+
                "<value>"+oldValue+"</value>"+
              "</userVariable>"+
              "<variableFormula>"+
                "<formulaTree>"+
                  "<type>NUMBER</type>"+
                 "<value>"+targetValue+"</value>"+
                "</formulaTree>"+
              "</variableFormula>"+
            "</setVariableBrick>"+
          "</brickList>" +
          "<object reference=\"../../..\"/>" +
          "</startScript>" +
          "</scriptList>" +
          "<soundList/>" +
          "</object>" +
          "</objectList>" +
          "<variables>"+
          "<objectVariableList/>" +
          "<programVariableList>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[2]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[3]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[4]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[6]/userVariable\"/>" +
          "</programVariableList>" +
        "</variables>" +
          xmlStringRumpEnd;
      
      Parser parser = new Parser();
      parser.parseXML(spriteManager, xmlString);
      
      Sprite sprite = spriteManager.getSprite(spriteName, false);
      
      StartScript startScript = (StartScript)sprite.getScript(0);
      startScript.getBrick(0).execute();
      assertTrue(startScript.getBrick(0) instanceof SetVariableBrick);
      assertTrue(stage.getUserVariables().getUserVariable("testname", null).getValue() == targetValue);
  }
    
    public void testSimpleFormulaParsing() {
      String spriteName = "TestSprite";
      String fileNamelook = "923QE849283_look";
      String lookName = "look1";
      
      double oldValue = 123.5;
      double targetValue = oldValue*13.5;
      
      String xmlString = xmlStringRumpBegin +
          "<objectList>"+
          "<object>"+
          "<lookList>" +
          lookXMLString(fileNamelook, lookName) +
          "</lookList>" +
            "<name>Background</name>" +
            "<scriptList>" +
             "<startScript>" +
                "<brickList>" +
                  "<setLookBrick>" +
                    "<object reference=\"../../../../..\"/>" +
                    "<look reference=\"../../../../../lookList/look\"/>" +
                  "</setLookBrick>" +
                "</brickList>" +
                "<object reference=\"../../..\"/>" +
              "</startScript>" +
            "</scriptList>" +
            "<soundList/>" +
          "</object>" +
          "<object>" +
          "<lookList>" +
          lookXMLString(fileNamelook, lookName) +
          "</lookList>" +
          "<name>" + spriteName + "</name>" +
          "<scriptList>" +
          "<startScript>" +
          "<brickList>" +
          "<setVariableBrick>"+
              "<object reference=\"../../../../..\"/>"+
              "<userVariable>"+
                "<name>testname</name>"+
                "<value>"+oldValue+"</value>"+
              "</userVariable>"+
              "<variableFormula>"+
                "<formulaTree>"+
                "<leftChild>"+
                  "<type>NUMBER</type>"+
                  "<value>13.5</value>"+
                "</leftChild>"+
                "<rightChild>"+
                  "<type>USER_VARIABLE</type>"+
                  "<value>testname</value>"+
                "</rightChild>"+
                "<type>OPERATOR</type>"+
                "<value>MULT</value>"+
                "</formulaTree>"+
              "</variableFormula>"+
            "</setVariableBrick>"+
          "</brickList>" +
          "<object reference=\"../../..\"/>" +
          "</startScript>" +
          "</scriptList>" +
          "<soundList/>" +
          "</object>" +
          "</objectList>" +
          "<variables>"+
          "<objectVariableList/>" +
          "<programVariableList>" +
            "<userVariable reference=\"../../../objectList/object[2]/scriptList/startScript/brickList/setVariableBrick/userVariable\"/>" +
          "</programVariableList>" +
        "</variables>" +
          xmlStringRumpEnd;
      
      Parser parser = new Parser();
      parser.parseXML(spriteManager, xmlString);
      
      Sprite sprite = spriteManager.getSprite(spriteName, false);
      
      StartScript startScript = (StartScript)sprite.getScript(0);
      startScript.getBrick(0).execute();
      //assertEquals(targetValue, stage.getUserVariables().getUserVariable("testname", null).getValue());
      assertEquals(targetValue, ((SetVariableBrick)startScript.getBrick(0)).getUserVariable().getValue());
  }
    
  public void testFormulaParsing() {
      String spriteName = "TestSprite";
      String fileNamelook = "923QE849283_look";
      String lookName = "look1";
      
      double oldValue = 42.0;
      double targetValue = Math.round(((oldValue*479) / 2.0));
      
      String xmlString = xmlStringRumpBegin +
          "<objectList>"+
          "<object>" +
          "<lookList>" +
          lookXMLString(fileNamelook, lookName) +
          "</lookList>" +
          "<name>" + spriteName + "</name>" +
          "<scriptList>" +
          "<startScript>" +
          "<brickList>" +
          "<setVariableBrick>"+
              "<object reference=\"../../../../..\"/>"+
              "<userVariable>"+
                "<name>testname</name>"+
                "<value>"+oldValue+"</value>"+
              "</userVariable>"+
              "<variableFormula>"+
                "<formulaTree>"+
                  "<leftChild>"+
                  "<leftChild>"+
                    "<leftChild>"+
                      "<type>NUMBER</type>"+
                      "<value>479</value>"+
                    "</leftChild>"+
                    "<rightChild>"+
                      "<type>USER_VARIABLE</type>"+
                      "<value>testname</value>"+
                    "</rightChild>"+
                    "<type>OPERATOR</type>"+
                    "<value>MULT</value>"+
                  "</leftChild>"+
                  "<rightChild>"+
                   "<type>NUMBER</type>"+
                    "<value>2</value>"+
                  "</rightChild>"+
                  "<type>OPERATOR</type>"+
                  "<value>DIVIDE</value>"+
                "</leftChild>"+
                "<type>FUNCTION</type>"+
                "<value>ROUND</value>"+
                "</formulaTree>"+
              "</variableFormula>"+
            "</setVariableBrick>"+
          "</brickList>" +
          "<object reference=\"../../..\"/>" +
          "</startScript>" +
          "</scriptList>" +
          "<soundList/>" +
          "</object>" +
          "</objectList>" +
          "<variables>"+
          "<objectVariableList/>" +
          "<programVariableList>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[2]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[3]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[4]/userVariable\"/>" +
            "<userVariable reference=\"../../../objectList/object/scriptList/startScript/brickList/setVariableBrick[6]/userVariable\"/>" +
          "</programVariableList>" +
        "</variables>" +
          xmlStringRumpEnd;
      
      Parser parser = new Parser();
      parser.parseXML(spriteManager, xmlString);
      
      Sprite sprite = spriteManager.getSprite(spriteName, false);
      
      StartScript startScript = (StartScript)sprite.getScript(0);
      startScript.getBrick(0).execute();
      assertEquals(targetValue, stage.getUserVariables().getUserVariable("testname", null).getValue());
  }
	
}