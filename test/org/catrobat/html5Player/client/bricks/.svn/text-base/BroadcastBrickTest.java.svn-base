package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;



public class BroadcastBrickTest extends GWTTestCase{
	
	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private CatScheduler scheduler;
	
	public BroadcastBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
		scheduler = CatScheduler.get();
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
		spriteManager.reset();
		scheduler.clear();
		
		stage.getMessageContainer().clear();
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
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testSingleBroadcastScript() {
		
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName = "broadcastScript";
		String message = "Next";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		
		
		sprite.addScript(broadcastScript);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testMultipleBroadcastScripts() {
		
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String costumeName3 = "costume3";
		CostumeData costumeData3 = createCostumeData(costumeName3);
		
		String spriteName = "sprite2";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		sprite.addCostumeData(costumeData3);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScripts
		String message = "Next";
		
		String broadcastScriptName = "broadcastScript";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite, broadcastScriptName2, message);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		
		NextCostumeBrick nextCostumeBrick2 = new NextCostumeBrick(spriteName);
		broadcastScript2.addBrick(nextCostumeBrick2);
		
		
		sprite.addScript(broadcastScript);
		sprite.addScript(broadcastScript2);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		//execute BroadcastBrick
		broadcastBrick.execute();
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName3, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * Tests if the BroadcastBrick resets the BroadcastScript (currentBrick)
	 */
	public void testMultipleExecution() {
		
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		Sprite sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		
		Costume costume = sprite.getCostume();
		
		//simulate SetCostumeBrick
		costume.setCostumeData(costumeData1);
		
		costume.hide();
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName = "broadcastScript";
		String message = "Next";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		ShowBrick showBrick = new ShowBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		broadcastScript.addBrick(showBrick);
		
		
		sprite.addScript(broadcastScript);
		
		
		//create BroadcastBrick
		BroadcastBrick broadcastBrick = new BroadcastBrick(spriteName, message);
		
		
		//first execution
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName2, costume.getCostumeData().getName());
		assertTrue(costume.isVisible());
		
		costume.hide();
		assertFalse(costume.isVisible());
		
		//second execution
		broadcastBrick.execute();
		
		assertEquals(1, CatScheduler.get().getThreadCount());
		
		scheduler.execute();
		scheduler.execute();
		
		assertEquals(costumeName1, costume.getCostumeData().getName());
		assertTrue(costume.isVisible());
	}

}
