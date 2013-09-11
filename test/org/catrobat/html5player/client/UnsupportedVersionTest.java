package org.catrobat.html5player.client;

import com.google.gwt.junit.client.GWTTestCase;

public class UnsupportedVersionTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	
	
	public void testXmlWithNoVersionIdentifier(){
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<program>" + 
				"<header>" + 
				"<androidVersion>10</androidVersion>" +
				"<catroidVersionCode>820</catroidVersionCode>" +
				"<deviceName>GT-S5830</deviceName>" +
				"<programName>test</programName>" +
				"<screenHeight>200</screenHeight>" +
				"<screenWidth>100</screenWidth>"+
				"</header>"+
				"</program>";
		Parser parser = new Parser();
		Stage stage = Stage.getInstance();
		SpriteManager spriteManager = stage.getSpriteManager();
		parser.parseXML(spriteManager, xmlString);
		assertFalse(parser.isParsingComplete());
	}
	
	public void testXmlWithWrongVersion()
	{
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<program>" + 
            "<header>" + 
            "<applicationVersion>0.1.0beta</applicationVersion>" +
            "<catrobatLanguageVersion>0.1</catrobatLanguageVersion>" +
            "<deviceName>GT-S5830</deviceName>" +
            "<programName>test</programName>" +
            "<screenHeight>200</screenHeight>" +
            "<screenWidth>100</screenWidth>"+
            "</header>"+
            "</program>";
		Parser parser = new Parser();
		Stage stage = Stage.getInstance();
		SpriteManager spriteManager = stage.getSpriteManager();
		parser.parseXML(spriteManager, xmlString);
		assertFalse(parser.isParsingComplete());
	}
	
}
