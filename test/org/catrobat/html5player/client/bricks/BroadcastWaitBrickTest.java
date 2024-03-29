/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.SpriteManager;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.scripts.WhenScript;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
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
		return "org.catrobat.html5player.html5player";
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
	 * @param name of Look
	 * @return LookData for costume
	 */
	private LookData createCostumeData(String name) {
		LookData data = new LookData();
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
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		
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
		
		scheduler.execute(); //NextLookBrick
		
		assertEquals(costumeName2, sprite.getLook().getLookData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getLook().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getLook().isVisible());
				
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
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		
		sprite.addScript(broadcastScript);
		//-------------------------------------------------------------------
		
		//sprite which script gets started via broadcast---------------------
		String spriteName2 = "sprite2";
		sprite2 = spriteManager.getSprite(spriteName2, true);
		
		sprite2.addLookData(costumeData1);
		sprite2.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite2.getLook().setLookData(costumeData2);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite2, broadcastScriptName2, message);
		
		NextLookBrick nextCostumeBrick2 = new NextLookBrick(spriteName2);
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
		
		scheduler.execute(); //NextLookBrick sprite
		scheduler.execute();
		scheduler.execute(); //NextLookBrick sprite2
		
		assertEquals(costumeName2, sprite.getLook().getLookData().getName());
		assertEquals(costumeName1, sprite2.getLook().getLookData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getLook().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getLook().isVisible());
				
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
		LookData costumeData1 = createCostumeData(costumeName1);
		
		String costumeName2 = "costume2";
		LookData costumeData2 = createCostumeData(costumeName2);
		
		String spriteName = "sprite1";
		sprite = spriteManager.getSprite(spriteName, true);
		
		sprite.addLookData(costumeData1);
		sprite.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite.getLook().setLookData(costumeData1);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName = "broadcastScript";
		String message = "NextCostume";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, broadcastScriptName, message);
		
		NextLookBrick nextLookBrick = new NextLookBrick(spriteName);
		broadcastScript.addBrick(nextLookBrick);
		
		sprite.addScript(broadcastScript);
		//-------------------------------------------------------------------
		
		//sprite which script gets started via broadcast---------------------
		String spriteName2 = "sprite2";
		sprite2 = spriteManager.getSprite(spriteName2, true);
		
		sprite2.addLookData(costumeData1);
		sprite2.addLookData(costumeData2);
		
		//simulate SetLookBrick
		sprite2.getLook().setLookData(costumeData2);
		
		//create BroadcastScript which executes one NextLookBrick
		String broadcastScriptName2 = "broadcastScript2";
		BroadcastScript broadcastScript2 = new BroadcastScript(sprite2, broadcastScriptName2, message);
		
		NextLookBrick nextCostumeBrick2 = new NextLookBrick(spriteName2);
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
		
		scheduler.execute(); //NextLookBrick
		scheduler.execute();
		scheduler.execute(); //NextLookBrick
		
		assertEquals(costumeName2, sprite.getLook().getLookData().getName());
		assertEquals(costumeName1, sprite2.getLook().getLookData().getName());
		assertEquals(1, CatScheduler.get().getThreadCount());
		assertTrue(spriteBroadcaster.getLook().isVisible());
		
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				scheduler.execute();
				
				assertFalse(spriteBroadcaster.getLook().isVisible());
				
				((WhenScript)spriteBroadcaster.getScript(0)).resetWhenScript();
				
				spriteBroadcaster.getLook().show();
				
				CatThread broadCasterThread2 = new CatThread(spriteBroadcaster.getName() + spriteBroadcaster.getScript(0).getName(), spriteBroadcaster.getScript(0));
				CatScheduler.get().schedule(broadCasterThread2);
				
				scheduler.execute(); //BroadcastBrick
				
				assertEquals(CatThread.SLEEPING, broadCasterThread2.getStatus());
				assertEquals(3, CatScheduler.get().getThreadCount());
				
				scheduler.execute(); //NextLookBrick
				scheduler.execute();
				scheduler.execute(); //NextLookBrick
				
				assertEquals("costume1", sprite.getLook().getLookData().getName());
				assertEquals("costume2", sprite2.getLook().getLookData().getName());
				assertEquals(1, CatScheduler.get().getThreadCount());
				assertTrue(spriteBroadcaster.getLook().isVisible());
				
				Timer timer2 = new Timer() {
					
					@Override
					public void run() {
						scheduler.execute();
						
						assertFalse(spriteBroadcaster.getLook().isVisible());
						
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
