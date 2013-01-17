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
package org.catrobat.html5Player.client;

//import org.catrobat.html5Player.client.bricks.SetCostumeBrick;
//import org.catrobat.html5Player.client.scripts.Script;
//import org.catrobat.html5Player.client.scripts.WhenScript;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.catrobat.html5Player.client.bricks.NextCostumeBrick;
import org.catrobat.html5Player.client.bricks.SetCostumeBrick;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.common.Sound;
import org.catrobat.html5Player.client.common.SoundInfo;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.Script;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.scripts.WhenScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
//import com.google.gwt.canvas.client.Canvas;
//import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.junit.client.GWTTestCase;

public class SpriteTest extends GWTTestCase {

//	public Canvas rootCanvas;
//	public Context2d rootContext;
//	public Sprite sprite;
//	public Sprite simpleCleanSprite;
	
	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public SpriteTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.getSpriteManager().reset();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * Helper
	 * @param name of Costume
	 * @return CostumeData for costume
	 */
	private CostumeData createCostumeData(String name) {
		CostumeData data = new CostumeData();
		data.setName(name);
		return data;
	}
	
	/**
	 * Helper
	 * @param id of SoundInfo
	 * @return CostumeData for costume
	 */
	private SoundInfo createSoundInfo(String id, String filename, String name) {
		SoundInfo info = new SoundInfo();
		info.setFileName(filename);
		info.setTitle(name);
		info.setId(id);
		return info;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testNewSprite() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		Costume startCostume = newSprite.getCostume();
		
		assertEquals(newSpriteName, newSprite.getName());
		assertFalse(newSprite.isBackground());
		assertEquals(70.0, newSprite.getVolume());
		assertTrue(newSprite.getCostumes().isEmpty());
		assertTrue(startCostume instanceof Costume);
		
		assertEquals((double)stage.getStageMiddleX(), startCostume.getXPosition());
		assertEquals((double)stage.getStageMiddleY(), startCostume.getYPosition());
		assertTrue(startCostume.isVisible());
	}
	
	/**
	 * 
	 */
	public void testAddCostumeDataGetCostume() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		
		assertEquals(costumeData1, newSprite.getCostume(costume1Name).getCostumeData());
		assertEquals(costumeData2, newSprite.getCostume(costume2Name).getCostumeData());
		assertNull(newSprite.getCostume("no real name"));
		assertNull(newSprite.getCostume(null));
	}
	
	/**
	 * 
	 */
	public void testGetCostumeDataByName() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		
		assertEquals(costumeData1, newSprite.getCostumeDataByName(costume1Name));
		assertEquals(costumeData2, newSprite.getCostumeDataByName(costume2Name));
		assertNull(newSprite.getCostumeDataByName("no real name"));
		assertNull(newSprite.getCostumeDataByName(null));
	}
	
	/**
	 * 
	 */
	public void testGetCostumeData() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		
		//get list
		ArrayList<CostumeData> costumeDataList = newSprite.getCostumeData();
		
		assertTrue(costumeDataList.isEmpty());
		
		//add costume data
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		
		//get list
		costumeDataList = newSprite.getCostumeData();
		
		assertFalse(costumeDataList.isEmpty());
		assertEquals(2, costumeDataList.size());
		assertTrue(costumeDataList.contains(costumeData1));
		assertTrue(costumeDataList.contains(costumeData2));
	}
	
	/**
	 * 
	 */
	public void testGetCostumeDataNames() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		
		//get list
		ArrayList<String> costumeDataList = newSprite.getCostumeDataNames();
		
		assertTrue(costumeDataList.isEmpty());
		
		//add costume data
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		
		//get list
		costumeDataList = newSprite.getCostumeDataNames();
		
		assertFalse(costumeDataList.isEmpty());
		assertEquals(2, costumeDataList.size());
		assertTrue(costumeDataList.contains(costume1Name));
		assertTrue(costumeDataList.contains(costume2Name));
	}
	
	/**
	 * 
	 */
	public void testAddCostumeData() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		
		//add costume data which is already in the list 
		newSprite.addCostumeData(costumeData1);
		
		//get list, must have size 2
		ArrayList<CostumeData> costumeDataList = newSprite.getCostumeData();
		assertEquals(2, costumeDataList.size());
		
		//try to add a null pointer
		newSprite.addCostumeData(null);
		
		//get list, must have size 2 again
		costumeDataList = newSprite.getCostumeData();
		assertEquals(2, costumeDataList.size());
	}
	
	/**
	 * 
	 */
	public void testAddGetScript() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		// scripts (all types)
		String startScriptName = "startScript";
		String whenScriptName = "whenScript";
		String broadCastScriptName = "broadCastScript";
		StartScript script1 = new StartScript(newSprite, startScriptName);
		WhenScript script2 = new WhenScript(newSprite, whenScriptName);
		BroadcastScript script3 = new BroadcastScript(newSprite, broadCastScriptName, "");
		
		// add scripts
		newSprite.addScript(script1);
		newSprite.addScript(script2);
		newSprite.addScript(script3);
		
		// get
		assertTrue(newSprite.getScript(script1.getName()) instanceof StartScript);
		assertTrue(newSprite.getScript(script2.getName()) instanceof WhenScript);
		assertTrue(newSprite.getScript(script3.getName()) instanceof BroadcastScript);
		
		// add/get null
		newSprite.addScript(null);
		assertNull(newSprite.getScript(null));
		
		// enter a not available name
		assertNull(newSprite.getScript("scriptName"));
	}
	
	/**
	 * 
	 */
	public void testAddGetSound() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		stage.setProjectNumber("393");
		
		//URL of Project with sound: 
		//http://catroid.org/resources/projects/393/projectcode.xml
		
		String soundInfo1Filename = "E6EE4E71FEB185DC70261885C5C3D6DA_Super Mario Bros Theme Song.mp3";
		String soundInfo2Filename = "A4A16FFE9972266AB24217FB89CE7732_Kleiner Junge spielt Schlagzeug.mp3";
		String soundInfo1ID = soundInfo1Filename.split("_")[0];
		String soundInfo2ID = soundInfo2Filename.split("_")[0];
		String soundInfo1Name = "Super Mario Bros Theme Song";
		String soundInfo2Name = "Kleiner Junge spielt Schlagzeug";
		SoundInfo soundInfo1 = createSoundInfo(soundInfo1ID, soundInfo1Filename,
																soundInfo1Name);
		SoundInfo soundInfo2 = createSoundInfo(soundInfo2ID, soundInfo2Filename,
																soundInfo2Name);
		
//		// add sound infos
//		newSprite.addSound(soundInfo1);
//		newSprite.addSound(soundInfo2);
//		
//		// get
//		assertTrue(newSprite.getSound(soundInfo1ID) instanceof Sound);
//		assertTrue(newSprite.getSound(soundInfo2ID) instanceof Sound);
//		
//		assertEquals(soundInfo1ID, newSprite.getSound(soundInfo1ID).getSoundInfo().getId());
//		assertEquals(soundInfo2ID, newSprite.getSound(soundInfo2ID).getSoundInfo().getId());
		
		assertNull(newSprite.getSound("notInSoundList"));
		assertNull(newSprite.getSound(null));
		
		stage.setProjectNumber(null);
		
		//TODO geht nicht wegen updateAudio() in Sound,
		//     wenn addSource aufgerufen wird
	}
	
	
	/**
	 * 
	 */
	public void testRun() {
		String newSpriteName = "newSprite";
		Sprite newSprite = stage.getSpriteManager().getSprite(newSpriteName, true);
		
		// Costumes
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		String costume3Name = "custome3";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		CostumeData costumeData3 = createCostumeData(costume3Name);
		
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		newSprite.addCostumeData(costumeData3);
		
		// Scripts
		String script1Name = "scriptToSetCoustme";
		String script2Name = "scriptToSetNextCoustme";
		String script3Name = "scriptShouldDoNothing";
		String script4Name = "scriptShouldDoNothing2";
		StartScript script1 = new StartScript(newSprite, script1Name);
		StartScript script2 = new StartScript(newSprite, script2Name);
		WhenScript  script3 = new WhenScript(newSprite, script3Name);
		BroadcastScript script4 = new BroadcastScript(newSprite, script4Name, "");
		
		// Bricks
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(newSpriteName, costume1Name);
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(newSpriteName);
		
		// Add Bricks
		script1.addBrick(setCostumeBrick);
		script2.addBrick(nextCostumeBrick);
		script2.addBrick(nextCostumeBrick);
		script3.addBrick(nextCostumeBrick);
		script4.addBrick(nextCostumeBrick);
		
		// Add Scripts
		newSprite.addScript(script1);
		newSprite.addScript(script2);
		newSprite.addScript(script3);
		newSprite.addScript(script4);
		
		// run
		newSprite.run(); //TODO muss SpriteManager übergeben werden?
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		
		assertEquals(costume3Name, newSprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testStartTapScripts() {
		String newSpriteName = "newSprite";
		Sprite newSprite = stage.getSpriteManager().getSprite(newSpriteName, true);
		
		// Costumes
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		String costume3Name = "custome3";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		CostumeData costumeData3 = createCostumeData(costume3Name);
		
		newSprite.addCostumeData(costumeData1);
		newSprite.addCostumeData(costumeData2);
		newSprite.addCostumeData(costumeData3);
		
		// Scripts
		String script1Name = "scriptShouldDoNothing";
		String script2Name = "scriptToSetCoustme";
		String script3Name = "scriptToSetNextCoustme";
		String script4Name = "scriptShouldDoNothing2";
		StartScript script1 = new StartScript(newSprite, script1Name);
		WhenScript  script2 = new WhenScript(newSprite, script2Name);
		WhenScript  script3 = new WhenScript(newSprite, script3Name);
		BroadcastScript script4 = new BroadcastScript(newSprite, script4Name, "");
		
		// Bricks
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(newSpriteName, costume1Name);
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(newSpriteName);
		
		// Add Bricks
		script1.addBrick(nextCostumeBrick);
		script2.addBrick(setCostumeBrick);
		script3.addBrick(nextCostumeBrick);
		script3.addBrick(nextCostumeBrick);
		script4.addBrick(nextCostumeBrick);
		
		// Add Scripts
		newSprite.addScript(script1);
		newSprite.addScript(script2);
		newSprite.addScript(script3);
		newSprite.addScript(script4);
		
		//run
		newSprite.startTapScripts();
		
		//important, because of BroadcastScript
		stage.getMessageContainer().clear(); 
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		
		assertEquals(costume3Name, newSprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testShowCostume() {
		String spriteName = "newSprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		//Project-URL:
		//http://catroid.org/resources/projects/409/projectcode.xml
		
		stage.setProjectNumber("409");
		
		String costumeName = "Banzai-Katze";
		CostumeData costumeData = createCostumeData(costumeName);
		costumeData.setFilename("416BA8FDFA47432EBF5520B7669D8A95_Banzai-Katze");
		costumeData.setWidth(0);
		costumeData.setHeight(0);
		sprite.addCostumeData(costumeData);
		
		sprite.getCostume().hide();
		
		//TODO es kommt zu einem Fehler:
		//(SCHWERWIEGEND: Job run failed with unexpected RuntimeException)...
		//wenn man mit dem rootCanvas in Stage arbeiten will (clearStage())
		//bei redrawScreen()!
		//
		//
//		sprite.showCostume(costumeName);
		
		stage.setProjectNumber(null);
	}
	
	/**
	 * 
	 */
	public void testGetScriptWithIndex() {
		String newSpriteName = "newSprite";
		Sprite newSprite = new Sprite(newSpriteName);
		
		// scripts (all types)
		String startScriptName = "startScript";
		String whenScriptName = "whenScript";
		String broadCastScriptName = "broadCastScript";
		StartScript script1 = new StartScript(newSprite, startScriptName);
		WhenScript script2 = new WhenScript(newSprite, whenScriptName);
		BroadcastScript script3 = new BroadcastScript(newSprite, broadCastScriptName, "");
		
		// add scripts
		newSprite.addScript(script1);
		newSprite.addScript(script2);
		newSprite.addScript(script3);
		
		// get
		assertTrue(newSprite.getScript(0) instanceof StartScript);
		assertTrue(newSprite.getScript(1) instanceof WhenScript);
		assertTrue(newSprite.getScript(2) instanceof BroadcastScript);
		
		// wrong index
		assertNull(newSprite.getScript(5));
	}
	
	//TODO: tests for playSound(), stopSound(), showCostume(), drawSprite(),
	//		processOnTouch(), 
	//
	//...
	
	/**
	 * 
	 */
	
	

//	public void gwtSetUp(){
//		rootCanvas = Canvas.createIfSupported();
//		rootContext = rootCanvas.getContext2d();
//		simpleCleanSprite = new Sprite(rootContext);
//		
//	}
	
//	public void testSprite(){
//		Sprite sprite = new Sprite(rootContext);
//		sprite.addCostume("name", "url");
//		Script script = new WhenScript();
//		script.addBrick(new SetCostumeBrick());
//		sprite.addScript(script);
//		sprite.addSound("someSound", "toUrl", "id");
//		sprite.drawSprite();
//		sprite.playSound(0);
//		sprite.playSound(3);
//		assertTrue(true);
//	}

}
