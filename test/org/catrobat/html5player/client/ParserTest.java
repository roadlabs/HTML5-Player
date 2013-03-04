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
				"<Content.Project>" + 
				"<androidVersion>10</androidVersion>" +
				"<catroidVersionCode>820</catroidVersionCode>" +
				"<catroidVersionName>0.6.0beta-820-debug</catroidVersionName>" +
				"<deviceName>GT-S5830</deviceName>" +
				"<projectName>" + projectName + "</projectName>" +
				"<screenHeight>" + screenHeight + "</screenHeight>" +
				"<screenWidth>" + screenWidth + "</screenWidth>";
		
		xmlStringRumpEnd = "</Content.Project>";
		
		
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
	 * @return xml string of <Common.CostumeData> Node
	 */
	public String costumeDataXMLString(String filename, String name) {
		return "<Common.CostumeData>" +
				"<fileName>" + filename + "</fileName>" +
				"<name>" + name + "</name>" +
				"</Common.CostumeData>";
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testParseXMLScreenResolution() {
		String xmlString = xmlStringRumpBegin + "<spriteList></spriteList>" +
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
	public void testParseXMLSpriteWithCostumes() {
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin + 
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList/>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);

		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		assertTrue(sprite instanceof Sprite);
		assertEquals(2, sprite.getLooks().size());
		
		Look costume1 = sprite.getLook(costumeName);
		Look costume2 = sprite.getLook(costumeName2);
		assertNotNull(costume1);
		assertNotNull(costume2);
		assertEquals(fileNameCostumeData, costume1.getLookData().getFilename());
		assertEquals(fileNameCostumeData2, costume2.getLookData().getFilename());
	}
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void testParseXMLStartScriptOneBrick() {
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.WhenScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</Content.WhenScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.WhenScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</Content.WhenScript>" +
			"<Content.WhenScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</Content.WhenScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.BroadcastScript>" +
			"<brickList>" +
			"<Bricks.HideBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.HideBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</Content.BroadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.BroadcastScript>" +
			"<brickList>" +
			"<Bricks.HideBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.HideBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</Content.BroadcastScript>" +
			"<Content.BroadcastScript>" +
			"<brickList>" +
			"<Bricks.HideBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.HideBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</Content.BroadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String fileNameCostumeData2 = "92382349283_costume2";
		String costumeName2 = "costume2";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"<Content.WhenScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<action>Tapped</action>" +
			"</Content.WhenScript>" +
			"<Content.BroadcastScript>" +
			"<brickList>" +
			"<Bricks.HideBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.HideBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"<receivedMessage>" + receivedMessage + "</receivedMessage>" +
			"</Content.BroadcastScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
	 * SetCostume					(o)			
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
	 * NextCostume					(o)
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
	public void testParseXMLSetCostumeBrick() {
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetCostumeBrick>" +
			"<costumeData reference=\"../../../../../costumeDataList/Common.CostumeData\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.WaitBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<timeToWaitInMilliSeconds>" + timeToWait + "</timeToWaitInMilliSeconds>" +
			"</Bricks.WaitBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PlaceAtBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<xPosition>" + xPosition + "</xPosition>" +
			"<yPosition>" + yPosition + "</yPosition>" +
			"</Bricks.PlaceAtBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeSizeByNBrick>" +
			"<size>" + size + "</size>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.ChangeSizeByNBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetYBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<yPosition>" + yPosition + "</yPosition>" +
			"</Bricks.SetYBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetXBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<xPosition>" + xPosition + "</xPosition>" +
			"</Bricks.SetXBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.HideBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.HideBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ShowBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.ShowBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.MoveNStepsBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<steps>" + steps + "</steps>" + 
			"</Bricks.MoveNStepsBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.GoNStepsBackBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<steps>" + steps + "</steps>" + 
			"</Bricks.GoNStepsBackBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ComeToFrontBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.ComeToFrontBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.GlideToBrick>" +
			"<durationInMilliSeconds>" + duration + "</durationInMilliSeconds>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<xDestination>" + xDestination + "</xDestination>" +
			"<yDestination>" + yDestination + "</yDestination>" +
			"</Bricks.GlideToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof GlideToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeXByBrick() {
		int xMovement = 100;
		
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeXByBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<xMovement>" + xMovement + "</xMovement>" +
			"</Bricks.ChangeXByBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeXByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeYByBrick() {
		int yMovement = 100;
		
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeYByBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<yMovement>" + yMovement + "</yMovement>" +
			"</Bricks.ChangeYByBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.TurnLeftBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</Bricks.TurnLeftBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.TurnRightBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</Bricks.TurnRightBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PointInDirectionBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<degrees>" + degrees + "</degrees>" +
			"</Bricks.PointInDirectionBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetSizeToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetVolumeToBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<volume>" + volume + "</volume>" +
			"</Bricks.SetVolumeToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof SetVolumeToBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLChangeVolumeByBrick() {
		double volume = 25.0f;
		
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeVolumeByBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<volume>" + volume + "</volume>" +
			"</Bricks.ChangeVolumeByBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
			xmlStringRumpEnd;
		
		Parser parser = new Parser();
		parser.parseXML(spriteManager, xmlString);
		
		Sprite sprite = spriteManager.getSprite(spriteName, false);
		
		assertTrue(sprite.getScript(0).getBrick(0) instanceof ChangeVolumeByBrick);
	}
	
	/**
	 * 
	 */
	public void testParseXMLNextCostumeBrick() {
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.NextCostumeBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.NextCostumeBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PlaySoundBrick>" +
			"<soundInfo>" +
			"<fileName>" + soundFileName + "</fileName>" +
			"<name>" + soundName + "</name>" +
			"</soundInfo>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.PlaySoundBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PlaySoundBrick>" +
			"<soundInfo>" +
			"<fileName>" + soundFileName + "</fileName>" +
			"<name>" + soundName + "</name>" +
			"</soundInfo>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.PlaySoundBrick>" +
			"<Bricks.PlaySoundBrick>" +
			"<soundInfo reference=\"../../Bricks.PlaySoundBrick/soundInfo\"/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.PlaySoundBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.StopAllSoundsBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.StopAllSoundsBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.BroadcastBrick>" +
			"<broadcastMessage>" + broadcastMessage + "</broadcastMessage>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.BroadcastBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.BroadcastWaitBrick>" +
			"<broadcastMessage>" + broadcastMessage + "</broadcastMessage>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.BroadcastWaitBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.NoteBrick>" +
			"<note>" + note + "</note>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.NoteBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.RepeatBrick>" +
			"<loopEndBrick>" +
			"<loopBeginBrick class=\"Bricks.RepeatBrick\" reference=\"../..\"/>" +
			"<sprite reference=\"../../../../../..\"/>" +
			"</loopEndBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<timesToRepeat>" + Integer.toString(timesToRepeat) + "</timesToRepeat>" +
			"</Bricks.RepeatBrick>" +
			"<Bricks.LoopEndBrick reference=\"../Bricks.RepeatBrick/loopEndBrick\"/>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ForeverBrick>" +
			"<loopEndBrick>" +
			"<loopBeginBrick class=\"Bricks.ForeverBrick\" reference=\"../..\"/>" +
			"<sprite reference=\"../../../../../..\"/>" +
			"</loopEndBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.ForeverBrick>" +
			"<Bricks.LoopEndBrick reference=\"../Bricks.RepeatBrick/loopEndBrick\"/>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		double transparency = 10.0;
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetGhostEffectBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<transparency>" + transparency + "</transparency>" +
			"</Bricks.SetGhostEffectBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
	public void testParseXMLChangeGhostEffectByBrick() {
		String spriteName = "TestSprite";
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		double changeGhostEffect = 0.5;
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeGhostEffectBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<changeGhostEffect>" + changeGhostEffect + "</changeGhostEffect>" +
			"</Bricks.ChangeGhostEffectBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.IfOnEdgeBounceBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.IfOnEdgeBounceBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNameCostumeData2 = "91223QE849283_costume2";
		String costumeName2 = "costume2";
		
		double size = 100.0;
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PointToBrick>" +
			"<pointedSprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetSizeToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</pointedSprite>" +
			"</Bricks.PointToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNameCostumeData2 = "91223QE849283_costume2";
		String costumeName2 = "costume2";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PointToBrick>" +
			"<pointedSprite reference=\"../../../../../../Content.Sprite[2]\">" +
			"<sprite reference=\"../../../../..\"/>" +
			"</pointedSprite>" +
			"</Bricks.PointToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ShowBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.ShowBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String xmlString = xmlStringRumpBegin +
				"<spriteList>"+
				"<Content.Sprite>" +
				"<costumeDataList>" +
				costumeDataXMLString(fileNameCostumeData, costumeName) +
				"</costumeDataList>" +
				"<name>" + spriteName + "</name>" +
				"<scriptList>" +
				"<Content.StartScript>" +
				"<brickList>" +
				"<Bricks.ClearGraphicEffectBrick>" +
				"<sprite reference=\"../../../../..\"/>" +
				"</Bricks.ClearGraphicEffectBrick>" +
				"</brickList>" +
				"<sprite reference=\"../../..\"/>" +
				"</Content.StartScript>" +
				"</scriptList>" +
				"<soundList/>" +
				"</Content.Sprite>" +
				"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		double brightness = 300.0;
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetBrightnessBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<brightness>" + brightness + "</brightness>" +
			"</Bricks.SetBrightnessBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		double brightness = -30.0;
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.ChangeBrightnessBrick>" +
			"<sprite reference=\"../../../../..\"/>" +
			"<brightness>" + brightness + "</brightness>" +
			"</Bricks.ChangeBrightnessBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"</spriteList>" +
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
		String fileNameCostumeData = "923QE849283_costume";
		String costumeName = "costume1";
		
		String pointedSpriteName = "TestSpritePointed";
		String fileNameCostumeData2 = "91223QE849283_costume2";
		String costumeName2 = "costume2";
		
		double size = 100.0;
		
		String reference="../Content.Sprite/scriptList/Content.StartScript/brickList/Bricks.PointToBrick/pointedSprite";
		
		String xmlString = xmlStringRumpBegin +
			"<spriteList>"+
			"<Content.Sprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData, costumeName) +
			"</costumeDataList>" +
			"<name>" + spriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.PointToBrick>" +
			"<pointedSprite>" +
			"<costumeDataList>" +
			costumeDataXMLString(fileNameCostumeData2, costumeName2) +
			"</costumeDataList>" +
			"<name>" + pointedSpriteName + "</name>" +
			"<scriptList>" +
			"<Content.StartScript>" +
			"<brickList>" +
			"<Bricks.SetSizeToBrick>" +
			"<size>" + size + "</size>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.SetSizeToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</pointedSprite>" +
			"<sprite reference=\"../../../../..\"/>" +
			"</Bricks.PointToBrick>" +
			"</brickList>" +
			"<sprite reference=\"../../..\"/>" +
			"</Content.StartScript>" +
			"</scriptList>" +
			"<soundList/>" +
			"</Content.Sprite>" +
			"<Content.Sprite reference=\"" + reference + "\"/>" +
			"</spriteList>" +
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
	
}