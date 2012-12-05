package org.catrobat.html5Player.client;

import org.catrobat.html5Player.client.bricks.NextCostumeBrick;
import org.catrobat.html5Player.client.bricks.SetCostumeBrick;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.scripts.WhenScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SpriteManagerTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	public SpriteManagerTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
//		Canvas canvas = Canvas.createIfSupported();
//		canvas.setCoordinateSpaceWidth(100);
//		canvas.setCoordinateSpaceHeight(100);
		
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
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
	 * TODO muss addSprite public sein?
	 */
	public void testAddSprite() {
		String testSpriteName = "testSprite";
		String testSprite2Name = "testSprite2";
		Sprite testSprite = new Sprite(testSpriteName);
		Sprite testSprite2 = new Sprite(testSprite2Name);
		
		spriteManager.addSprite(testSprite);
		spriteManager.addSprite(testSprite2);
		
		assertFalse(spriteManager.getSpriteList().isEmpty());
		assertEquals(testSprite, spriteManager.getSprite(testSpriteName, false));
		assertEquals(testSprite2, spriteManager.getSprite(testSprite2Name, false));
	}
	
	/**
	 * 
	 */
	public void testClearSpriteList() {
		
		String testSpriteName = "testSprite3";
		Sprite testSprite = new Sprite(testSpriteName);
		spriteManager.addSprite(testSprite);
		
		assertFalse("spritelist not empty", spriteManager.getSpriteList().isEmpty());
		
		spriteManager.reset();
		
		assertTrue("spritelist not empty", spriteManager.getSpriteList().isEmpty());
	}
	
	/**
	 * 
	 */
	public void testGetSpriteToCreateSprite() {
		
		spriteManager.reset();
		
		String newSpriteName = "newSprite";
		Sprite newSprite = spriteManager.getSprite(newSpriteName, true);
		
		assertEquals(newSpriteName, newSprite.getName());
		assertEquals(newSprite, spriteManager.getSprite(newSpriteName, false));
		assertEquals("A new sprite with the same name was created",
					 newSprite, spriteManager.getSprite(newSpriteName, true));
	}
	
	/**
	 * 
	 */
	public void testGetSpriteToGetSprites() {
		
		spriteManager.reset();
		
		String newSpriteName = "newSprite";
		String newSprite2Name = "newSprite2";
		String newSprite3Name = "newSprite3";
		Sprite newSprite = spriteManager.getSprite(newSpriteName, true);
		Sprite newSprite2 = spriteManager.getSprite(newSprite2Name, true);
		Sprite newSprite3 = spriteManager.getSprite(newSprite3Name, true);
		
		assertEquals(newSprite, spriteManager.getSprite(newSpriteName, false));
		assertEquals(newSprite2, spriteManager.getSprite(newSprite2Name, false));
		assertEquals(newSprite3, spriteManager.getSprite(newSprite3Name, false));
		
		assertEquals("A new sprite with the same name was created",
				 newSprite, spriteManager.getSprite(newSpriteName, true));
		assertEquals("A new sprite with the same name was created",
				 newSprite2, spriteManager.getSprite(newSprite2Name, true));
		assertEquals("A new sprite with the same name was created",
				 newSprite3, spriteManager.getSprite(newSprite3Name, true));
	}
	
	/**
	 * 
	 */
	public void testPlayCatroid() {
		
		spriteManager.reset();
		
		// Sprites
		String newSprite1Name = "newSprite";
		String newSprite2Name = "newSprite2";
		String newSprite3Name = "newSprite3";
		Sprite newSprite1 = spriteManager.getSprite(newSprite1Name, true);
		Sprite newSprite2 = spriteManager.getSprite(newSprite2Name, true);
		Sprite newSprite3 = spriteManager.getSprite(newSprite3Name, true);
		
		// Costumes
		String costume1Name = "custome1";
		String costume2Name = "custome2";
		String costume3Name = "custome3";
		CostumeData costumeData1 = createCostumeData(costume1Name);
		CostumeData costumeData2 = createCostumeData(costume2Name);
		CostumeData costumeData3 = createCostumeData(costume3Name);
		
		// Add Costumes
		newSprite1.addCostumeData(costumeData1);
		newSprite1.addCostumeData(costumeData2);
		newSprite1.addCostumeData(costumeData3);
		
		newSprite2.addCostumeData(costumeData1);
		newSprite2.addCostumeData(costumeData2);
		newSprite2.addCostumeData(costumeData3);
		
		newSprite3.addCostumeData(costumeData1);
		newSprite3.addCostumeData(costumeData2);
		newSprite3.addCostumeData(costumeData3);
		
		// Scripts
		String script1Name = "scriptForSprite1";
		String script2Name = "scriptForSprite2";
		String script3Name = "scriptForSprite3";
		StartScript scriptSprite1 = new StartScript(newSprite1, script1Name);
		StartScript scriptSprite2 = new StartScript(newSprite2, script2Name);
		WhenScript  scriptSprite3 = new WhenScript(newSprite3, script3Name);
		
		// Bricks
		SetCostumeBrick setCostumeBrickSprite1 = new SetCostumeBrick(newSprite1Name, costume1Name);
		SetCostumeBrick setCostumeBrickSprite2 = new SetCostumeBrick(newSprite2Name, costume1Name);
		
		NextCostumeBrick nextCostumeBrickSprite1 = new NextCostumeBrick(newSprite1Name);
		NextCostumeBrick nextCostumeBrickSprite2 = new NextCostumeBrick(newSprite2Name);
		NextCostumeBrick nextCostumeBrickSprite3 = new NextCostumeBrick(newSprite3Name);
		
		// Add Bricks
		scriptSprite1.addBrick(setCostumeBrickSprite1);
		scriptSprite1.addBrick(nextCostumeBrickSprite1);
		
		scriptSprite2.addBrick(setCostumeBrickSprite2);
		scriptSprite2.addBrick(nextCostumeBrickSprite2);
		scriptSprite2.addBrick(nextCostumeBrickSprite2);
		
		scriptSprite3.addBrick(nextCostumeBrickSprite3);
		
		// Add Scripts
		newSprite1.addScript(scriptSprite1);	
		newSprite2.addScript(scriptSprite2);
		newSprite3.addScript(scriptSprite3);
		
		// run
		spriteManager.playCatroid();
		
		assertEquals(2, CatScheduler.get().getThreadCount());
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		
		assertEquals(costume2Name, newSprite1.getCostume().getCostumeData().getName());
		assertEquals(costume3Name, newSprite2.getCostume().getCostumeData().getName());
		assertNull(newSprite3.getCostume().getCostumeData());
	}
	
	//TODO: tests for redrawScreen(), handleScreenClick()
	//
	//...
	
	/**
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
