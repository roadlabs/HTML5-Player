package org.catrobat.html5Player.client;

import junit.framework.TestCase;

public class ConstTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testProjectPath() {		
		assertEquals("http://catroid.org/resources/projects/", Const.PROJECT_PATH);
		assertEquals("projectcode.xml", Const.PROJECT_FILE);	
	}
	
	public void testBackroundConst() {		
//		assertEquals("Hintergrund", Const.BACK_GROUND);	
		
		assertEquals("Hintergrund", Const.BACK_GROUND_GER);	
		assertEquals("Background", Const.BACK_GROUND_ENG);	
	}
	
	public void testScreeConst() {		
		assertEquals("screenHeight", Const.SCREEN_HEIGHT);
		assertEquals("screenWidth", Const.SCREEN_WIDTH);	
	}
	
	public void testServerConst() {		
		assertEquals("html5player/", Const.SERVER);		
	}

}
