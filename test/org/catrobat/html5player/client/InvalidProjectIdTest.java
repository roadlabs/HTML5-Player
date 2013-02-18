package org.catrobat.html5player.client;

import com.google.gwt.junit.client.GWTTestCase;

public class InvalidProjectIdTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	
	
	public void testParseForInvalidProjectNumber(){
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
							+ "<array>"
							+"<numberOfPages><![CDATA[10]]></numberOfPages>"
							+"<task><![CDATA[newestProjects]]></task>"
							+"<pageNr><![CDATA[1]]></pageNr>"
							+"<searchQuery />"
							+"<error>"
							+"<type><![CDATA[viewer]]></type>"
							+"<code><![CDATA[ajax_request_page_not_found]]></code>"
							+"<extra />"
							+"</error>"
							+"</array>";
		Parser parser = new Parser();
		Stage stage = Stage.getInstance();
		SpriteManager spriteManager = stage.getSpriteManager();
		parser.parseXML(spriteManager, xmlString);
		assertFalse(parser.isParsingComplete());
	}
}
