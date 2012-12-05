package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.scripts.WhenScript;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;



public class BroadcastWaitBrickTest extends GWTTestCase{
	
	//for asynchronous testing
	private Sprite spriteBroadcaster = null;
	private Sprite sprite = null;
	private Sprite sprite2 = null;
	
	private CatThread broadCasterThread = null;
	//
	
	private Stage stage;
	private Scene scene;
	private SpriteManager spriteManager;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	private CatScheduler scheduler;
	
	public BroadcastWaitBrickTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
		spriteManager = stage.getSpriteManager();
		scheduler = CatScheduler.get();
		
		scheduler.clear();
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
		
		spriteBroadcaster = null;
		sprite = null;
		sprite2 = null;
		
		broadCasterThread = null;
		
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
		
		//sprite which script gets started via broadcast---------------------
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		
		sprite.addScript(broadcastScript);
		//-------------------------------------------------------------------
		
		//sprite which broadcasts -------------------------------------------
		String spriteNameBroadcaster = "spriteBroadCaster";
		spriteBroadcaster = spriteManager.getSprite(spriteNameBroadcaster, true);
		
		String startScriptName = "startScript";
		StartScript startScript = new StartScript(spriteBroadcaster, startScriptName);
		
		//create BroadcastWaitBrick
		BroadcastWaitBrick broadcastWaitBrick = new BroadcastWaitBrick(spriteName, message, startScript);
		startScript.addBrick(broadcastWaitBrick);
		
		HideBrick hideBrick = new HideBrick(spriteNameBroadcaster);
		startScript.addBrick(hideBrick);
		
		spriteBroadcaster.addScript(startScript);
		//-------------------------------------------------------------------
		
		broadCasterThread = new CatThread(spriteNameBroadcaster + startScript.getName(), startScript);
		CatScheduler.get().schedule(broadCasterThread);
		
		scheduler.execute(); //BroadcastBrick
		
		assertEquals(CatThread.SLEEPING, broadCasterThread.getStatus());
		assertEquals(2, CatScheduler.get().getThreadCount());
		
		assertNotNull("thread is null", CatScheduler.get().getThread(broadcastScript.getExecutor()));
		assertEquals(CatThread.READY, CatScheduler.get().getThread(broadcastScript.getExecutor()).getStatus());
		
		scheduler.execute(); //NextCostumeBrick
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getCostume().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getCostume().isVisible());
				
				finishTest();
			}
		};
		
		delayTestFinish(300);
		
		timer.schedule(200);
	}
	
	/**
	 * 
	 */
	public void testMultipleBroadcastScripts() {
		
		//sprite which script gets started via broadcast---------------------
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		
		sprite.addScript(broadcastScript);
		//-------------------------------------------------------------------
		
		//sprite which script gets started via broadcast---------------------
		String spriteName2 = "sprite2";
		sprite2 = spriteManager.getSprite(spriteName2, true);
		
		sprite2.addCostumeData(costumeData1);
		sprite2.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite2.getCostume().setCostumeData(costumeData2);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite2, broadcastScriptName2, message);
		
		NextCostumeBrick nextCostumeBrick2 = new NextCostumeBrick(spriteName2);
		broadcastScript2.addBrick(nextCostumeBrick2);
		
		sprite2.addScript(broadcastScript2);
		//-------------------------------------------------------------------
		
		//sprite which broadcasts -------------------------------------------
		String spriteNameBroadcaster = "spriteBroadCaster";
		spriteBroadcaster = spriteManager.getSprite(spriteNameBroadcaster, true);
		
		String startScriptName = "startScript";
		StartScript startScript = new StartScript(spriteBroadcaster, startScriptName);
		
		//create BroadcastWaitBrick
		BroadcastWaitBrick broadcastWaitBrick = new BroadcastWaitBrick(spriteName2, message, startScript);
		startScript.addBrick(broadcastWaitBrick);
		
		HideBrick hideBrick = new HideBrick(spriteNameBroadcaster);
		startScript.addBrick(hideBrick);
		
		spriteBroadcaster.addScript(startScript);
		//-------------------------------------------------------------------
		
		broadCasterThread = new CatThread(spriteNameBroadcaster + startScript.getName(), startScript);
		CatScheduler.get().schedule(broadCasterThread);
		
		scheduler.execute(); //BroadcastBrick
		
		assertEquals(CatThread.SLEEPING, broadCasterThread.getStatus());
		assertEquals(3, CatScheduler.get().getThreadCount());
		
		scheduler.execute(); //NextCostumeBrick sprite
		scheduler.execute();
		scheduler.execute(); //NextCostumeBrick sprite2
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
		assertEquals(costumeName1, sprite2.getCostume().getCostumeData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getCostume().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getCostume().isVisible());
				
				finishTest();
			}
		};
		
		delayTestFinish(400);
		
		timer.schedule(300);
	}
	
	/**
	 * Tests if the BroadcastWaitBrick resets the BroadcastScript (currentBrick)
	 */
	public void testMultipleExecution() {
		//sprite which script gets started via broadcast---------------------
		String costumeName1 = "costume1";
		CostumeData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		CostumeData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addCostumeData(costumeData1);
		sprite.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite.getCostume().setCostumeData(costumeData1);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextCostumeBrick nextCostumeBrick = new NextCostumeBrick(spriteName);
		broadcastScript.addBrick(nextCostumeBrick);
		
		sprite.addScript(broadcastScript);
		//-------------------------------------------------------------------
		
		//sprite which script gets started via broadcast---------------------
		String spriteName2 = "sprite2";
		sprite2 = spriteManager.getSprite(spriteName2, true);
		
		sprite2.addCostumeData(costumeData1);
		sprite2.addCostumeData(costumeData2);
		
		//simulate SetCostumeBrick
		sprite2.getCostume().setCostumeData(costumeData2);
		
		//create BroadcastScript which executes one NextCostumeBrick
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite2, broadcastScriptName2, message);
		
		NextCostumeBrick nextCostumeBrick2 = new NextCostumeBrick(spriteName2);
		broadcastScript2.addBrick(nextCostumeBrick2);
		
		sprite2.addScript(broadcastScript2);
		//-------------------------------------------------------------------
		
		//sprite which broadcasts -------------------------------------------
		String spriteNameBroadcaster = "spriteBroadCaster";
		spriteBroadcaster = spriteManager.getSprite(spriteNameBroadcaster, true);
		
		String startScriptName = "startScript";
		WhenScript whenScript = new WhenScript(spriteBroadcaster, startScriptName);
		
		//create BroadcastWaitBrick
		BroadcastWaitBrick broadcastWaitBrick = new BroadcastWaitBrick(spriteName2, message, whenScript);
		whenScript.addBrick(broadcastWaitBrick);
		
		HideBrick hideBrick = new HideBrick(spriteNameBroadcaster);
		whenScript.addBrick(hideBrick);
		
		spriteBroadcaster.addScript(whenScript);
		//-------------------------------------------------------------------
		
		broadCasterThread = new CatThread(spriteNameBroadcaster + whenScript.getName(), whenScript);
		CatScheduler.get().schedule(broadCasterThread);
		
		scheduler.execute(); //BroadcastBrick
		
		assertEquals(CatThread.SLEEPING, broadCasterThread.getStatus());
		assertEquals(3, CatScheduler.get().getThreadCount());
		
		scheduler.execute(); //NextCostumeBrick
		scheduler.execute();
		scheduler.execute(); //NextCostumeBrick
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
		assertEquals(costumeName1, sprite2.getCostume().getCostumeData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getCostume().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getCostume().isVisible());
				
				((WhenScript)spriteBroadcaster.getScript(0)).resetWhenScript();
				
				spriteBroadcaster.getCostume().show();
				
				CatThread broadCasterThread2 = new CatThread(spriteBroadcaster.getName() + spriteBroadcaster.getScript(0).getName(), spriteBroadcaster.getScript(0));
				CatScheduler.get().schedule(broadCasterThread2);
				
				scheduler.execute(); //BroadcastBrick
				
				assertEquals(CatThread.SLEEPING, broadCasterThread2.getStatus());
				assertEquals(3, CatScheduler.get().getThreadCount());
				
				scheduler.execute(); //NextCostumeBrick
				scheduler.execute();
				scheduler.execute(); //NextCostumeBrick
				
				assertEquals("costume1", sprite.getCostume().getCostumeData().getName());
				assertEquals("costume2", sprite2.getCostume().getCostumeData().getName());
				assertEquals(1, CatScheduler.get().getThreadCount());
				assertTrue(spriteBroadcaster.getCostume().isVisible());
				
				Timer timer2 = new Timer() {
					
					@Override
					public void run() {
						scheduler.execute();
						
						assertFalse(spriteBroadcaster.getCostume().isVisible());
						
						finishTest();
					}
				};
				
				timer2.schedule(300);
			}
		};
		
		delayTestFinish(1000);
		
		timer.schedule(300);
	}
	
}
