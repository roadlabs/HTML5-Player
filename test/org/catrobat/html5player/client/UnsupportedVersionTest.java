package org.catrobat.html5player.client;

import com.google.gwt.junit.client.GWTTestCase;

public class UnsupportedVersionTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	
	
	public void testXmlWithNoVersionIdentifier(){
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<Content.Project>" + 
				"<androidVersion>10</androidVersion>" +
				"<catroidVersionCode>820</catroidVersionCode>" +
				"<deviceName>GT-S5830</deviceName>" +
				"<projectName>test</projectName>" +
				"<screenHeight>200</screenHeight>" +
				"<screenWidth>100</screenWidth>"+
				"</Content.Project>";
		Parser parser = new Parser();
		Stage stage = Stage.getInstance();
		SpriteManager spriteManager = stage.getSpriteManager();
		parser.parseXML(spriteManager, xmlString);
		assertFalse(parser.isParsingComplete());
	}
	
	public void testXmlWithWrongVersion()
	{
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<Content.Project>" + 
				"<androidVersion>10</androidVersion>" +
				"<catroidVersionCode>820</catroidVersionCode>" +
				"<catroidVersionName>0.9.0beta-820-debug</catroidVersionName>" +
				"<deviceName>GT-S5830</deviceName>" +
				"<projectName>test</projectName>" +
				"<screenHeight>200</screenHeight>" +
				"<screenWidth>100</screenWidth>"+
				"</Content.Project>";
		Parser parser = new Parser();
		Stage stage = Stage.getInstance();
		SpriteManager spriteManager = stage.getSpriteManager();
		parser.parseXML(spriteManager, xmlString);
		assertFalse(parser.isParsingComplete());
	}
	
}
