package org.catrobat.html5Player.client.scripts;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.LoopEndBrick;
import org.catrobat.html5Player.client.bricks.NextCostumeBrick;
import org.catrobat.html5Player.client.bricks.RepeatBrick;
import org.catrobat.html5Player.client.bricks.SetCostumeBrick;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class BroadcastScriptTest extends GWTTestCase {

	private Sprite sprite_ = null; //for asynchronous testing
	
	private Stage stage;
	private Scene scene;
	private int canvasCoordinateSpaceWidth = 100;
	private int canvasCoordinateSpaceHeight = 100;
	
	public BroadcastScriptTest() {
		stage = Stage.getInstance();
		scene = Scene.get();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
//		Canvas canvas = Canvas.createIfSupported();
//		canvas.setCoordinateSpaceWidth(canvasCoordinateSpaceWidth);
//		canvas.setCoordinateSpaceHeight(canvasCoordinateSpaceHeight);
		
		scene.createScene(canvasCoordinateSpaceWidth, canvasCoordinateSpaceHeight);
		Canvas canvas = scene.getCanvas();
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		stage.setCanvas(null);
		stage.getSpriteManager().reset(); //important
		stage.getMessageContainer().clear(); //important
		sprite_ = null;
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
	public void testNewScriptWithMessage() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		String scriptName = "BroadcastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		assertEquals(spriteName, broadcastScript.getSprite().getName());
		assertEquals("BroadcastScript", broadcastScript.getType());
		assertTrue(broadcastScript.getBrickList().isEmpty());
		
		assertEquals(broadcastScript, 
		  stage.getMessageContainer().getReceiverOfMessage(message).get(0));
		assertEquals(message, broadcastScript.getBroadcastMessage());
	}
	
	/**
	 * 
	 */
	public void testNewScriptWithoutMessage() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = null;
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		assertEquals(spriteName, broadcastScript.getSprite().getName());
		assertEquals("BroadcastScript", broadcastScript.getType());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(message).isEmpty());
		assertNull(broadcastScript.getBroadcastMessage());
	}
	
	/**
	 * 
	 */
	public void testNewScriptWithEmptyString() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = "";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		assertEquals(spriteName, broadcastScript.getSprite().getName());
		assertEquals("BroadcastScript", broadcastScript.getType());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(message).isEmpty());
		assertEquals(message, broadcastScript.getBroadcastMessage());
	}
	
	/**
	 * 
	 */
	public void testAddBrickGetBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.addBrick(setBrick);
		
		int position = 0;
		broadcastScript.addBrick(nextBrick, position);
		
		assertEquals(2, broadcastScript.getBrickList().size());
		assertTrue(broadcastScript.getBrick(position) instanceof NextCostumeBrick);
		assertTrue(broadcastScript.getBrick(position+1) instanceof SetCostumeBrick);
		
		broadcastScript.addBrick(null);
		assertEquals(2, broadcastScript.getBrickList().size());
		
		broadcastScript.addBrick(null, position);
		assertEquals(2, broadcastScript.getBrickList().size());
		assertTrue(broadcastScript.getBrick(position) instanceof NextCostumeBrick);
	}
	
	/**
	 * 
	 */
	public void testAddBrickMultipleTimes() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.addBrick(setBrick);
		broadcastScript.addBrick(nextBrick);
		broadcastScript.addBrick(nextBrick);
		
		assertEquals(3, broadcastScript.getBrickList().size());
		assertTrue(broadcastScript.getBrick(0) instanceof SetCostumeBrick);
		assertTrue(broadcastScript.getBrick(1) instanceof NextCostumeBrick);
		assertTrue(broadcastScript.getBrick(2) instanceof NextCostumeBrick);
	}
	
	/**
	 * 
	 */
	public void testDeleteBrick() {
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, "costumeName");
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.addBrick(setBrick);
		
		int position = 0;
		broadcastScript.addBrick(nextBrick, position);
		
		assertEquals(2, broadcastScript.getBrickList().size());
		assertTrue(broadcastScript.getBrick(position) instanceof NextCostumeBrick);
		assertTrue(broadcastScript.getBrick(position+1) instanceof SetCostumeBrick);
		
		broadcastScript.deleteBrick(position);
		assertEquals(1, broadcastScript.getBrickList().size());
		assertTrue(broadcastScript.getBrick(position) instanceof SetCostumeBrick);
		
		broadcastScript.deleteBrick(position);
		assertTrue(broadcastScript.getBrickList().isEmpty());
		
		//startScript.deleteBrick(position+3); <-- IndexOutOfBoundsException TODO
	}
	
	/**
	 * 
	 */
	public void testRun() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";		
		String spriteName = "Sprite";
		
		Sprite sprite = new Sprite(spriteName);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//

		stage.getSpriteManager().addSprite(sprite);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, costumeName1);
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.addBrick(setBrick);
		broadcastScript.addBrick(nextBrick);
		
		broadcastScript.run();
		broadcastScript.run();
		
		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testRunWithNoBricks() {	
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.run();
		
		assertNull(sprite.getCostume().getCostumeData());
	}
	
	/**
	 * 
	 */
	public void testSetBroadcastMessageValidNewMessage() {	
		
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		String newMessage = "new broadcast-script message!";
		broadcastScript.setBroadcastMessage(newMessage);
		
		assertEquals(newMessage, broadcastScript.getBroadcastMessage());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(message).isEmpty());
		assertEquals(broadcastScript, 
		   stage.getMessageContainer().getReceiverOfMessage(newMessage).get(0));
		
	}
	
	/**
	 * 
	 */
	public void testSetBroadcastMessageEmptyNewMessage() {	
		
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		String newMessage = "";
		broadcastScript.setBroadcastMessage(newMessage);
		
		assertNotSame(message, broadcastScript.getBroadcastMessage());
		assertEquals(newMessage, broadcastScript.getBroadcastMessage());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(message).isEmpty());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(newMessage).isEmpty());
	}
	
	/**
	 * 
	 */
	public void testSetBroadcastMessageNull() {		
		
		String spriteName = "Sprite";
		Sprite sprite = new Sprite(spriteName);

		String scriptName = "BroadCastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		String newMessage = null;
		broadcastScript.setBroadcastMessage(newMessage);
		
		assertNull(broadcastScript.getBroadcastMessage());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(message).isEmpty());
		assertTrue(stage.getMessageContainer().getReceiverOfMessage(newMessage).isEmpty());
	}
	
//	/**
//	 * 
//	 */
//	public void testRunPauseManualResume() {	
//		String costumeName1 = "costume1";
//		String costumeName2 = "costume2";		
//		String spriteName = "Sprite";
//		
//		Sprite sprite = new Sprite(spriteName);
//		
//		sprite.addCostumeData(createCostumeData(costumeName1));
//		sprite.addCostumeData(createCostumeData(costumeName2));
//		
//		//hide costume, so drawSprite() in redrawScreen() does nothing
//		sprite.getCostume().hide();
//		//
//
//		stage.getSpriteManager().addSprite(sprite);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, costumeName1);
//		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
//		
//		String scriptName = "BroadcastScript";
//		String message = "broadcast-script message!";
//		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
//		
//		broadcastScript.addBrick(setBrick);
//		broadcastScript.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", broadcastScript);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		broadcastScript.pause(1000);
//		
//		CatScheduler.get().execute();
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
//		
//		//resume
//		broadcastScript.resume();
//		
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName2, sprite.getCostume().getCostumeData().getName());
//	}
//	
//	/**
//	 * 
//	 */
//	public void testRunPauseResume() {	
//		String costumeName1 = "costume1";
//		String costumeName2 = "costume2";		
//		String spriteName = "Sprite";
//		
//		sprite_ = new Sprite(spriteName);
//		
//		sprite_.addCostumeData(createCostumeData(costumeName1));
//		sprite_.addCostumeData(createCostumeData(costumeName2));
//		
//		//hide costume, so drawSprite() in redrawScreen() does nothing
//		sprite_.getCostume().hide();
//		//
//
//		stage.getSpriteManager().addSprite(sprite_);
//		
//		SetCostumeBrick2 setBrick = new SetCostumeBrick2(spriteName, costumeName1);
//		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
//		
//		String scriptName = "BroadcastScript";
//		String message = "broadcast-script message!";
//		BroadcastScript broadcastScript = new BroadcastScript(sprite_, scriptName, message);
//		
//		broadcastScript.addBrick(setBrick);
//		broadcastScript.addBrick(nextBrick);
//		
//		//run script as a thread
//		CatThread thread = new CatThread("thread", broadcastScript);
//		CatScheduler.get().schedule(thread);
//		
//		CatScheduler.get().execute();
//		
//		//pause
//		broadcastScript.pause(500);
//		
//		CatScheduler.get().execute();
//		CatScheduler.get().execute();
//		
//		assertEquals(costumeName1, sprite_.getCostume().getCostumeData().getName());
//		
//		Timer timer = new Timer() {
//		    public void run() {
//		    	
//		    	CatScheduler.get().execute();
//				
//				assertEquals("costume2", sprite_.getCostume().getCostumeData().getName());
//
//		    	// tell the test system the test is now done
//		    	finishTest();
//		    }
//		  };
//
//		  // Set a delay period significantly longer than the
//		  // event is expected to take
//		  delayTestFinish(1000);
//
//		  // Schedule the event and return control to the test system
//		  timer.schedule(600);
//	}
	
	/**
	 * 
	 */
	public void testSetCurrentBrickIndex() {
		String costumeName1 = "costume1";
		String costumeName2 = "costume2";		
		String spriteName = "Sprite";
		
		Sprite sprite = new Sprite(spriteName);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//

		stage.getSpriteManager().addSprite(sprite);
		
		SetCostumeBrick setBrick = new SetCostumeBrick(spriteName, costumeName1);
		SetCostumeBrick setBrick2 = new SetCostumeBrick(spriteName, costumeName2);
		NextCostumeBrick nextBrick = new NextCostumeBrick(spriteName);
		
		String scriptName = "BroadcastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		broadcastScript.addBrick(setBrick);
		broadcastScript.addBrick(setBrick2);
		broadcastScript.addBrick(nextBrick);
		
		int currentBrickIndex = 1;
		broadcastScript.setCurrentBrick(currentBrickIndex);
		assertEquals(currentBrickIndex, broadcastScript.getCurrentBrick());
		
		broadcastScript.run();
		broadcastScript.run();
		
		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
	}
	
	/**
	 * 
	 */
	public void testGetLastLoopBeginBrickWithoutLoopEndBrick() {
		String spriteName = "Sprite";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "BroadcastScript";
		String message = "broadcast-script message!";
		BroadcastScript broadcastScript = new BroadcastScript(sprite, scriptName, message);
		
		sprite.addScript(broadcastScript);
		
		int timesToRepeatOuter = 3;
		RepeatBrick repeatBrickOuter = new RepeatBrick(spriteName, timesToRepeatOuter);
		
		int timesToRepeatInner = 1;
		RepeatBrick repeatBrickInner = new RepeatBrick(spriteName, timesToRepeatInner);
		
		broadcastScript.addBrick(repeatBrickOuter);
		broadcastScript.addBrick(repeatBrickInner);
		
		assertEquals(repeatBrickInner, broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickInner = new LoopEndBrick(spriteName, broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(loopEndBrickInner);
		
		assertEquals(repeatBrickOuter, broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		LoopEndBrick loopEndBrickOuter = new LoopEndBrick(spriteName, broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick());
		broadcastScript.getLastLoopBeginBrickWithoutLoopEndBrick().setLoopEndBrick(loopEndBrickOuter);
		
		assertEquals(loopEndBrickOuter, repeatBrickOuter.getLoopEndBrick());
		assertEquals(loopEndBrickInner, repeatBrickInner.getLoopEndBrick());
	}
	
	//TODO: runWait() - is same as run() in the meantime
	//
	//...
	
	/**
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
