package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Scene;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class RepeatBrickTest extends GWTTestCase {

	private Stage stage;
	private Scene scene;
	int canvasCoordinateSpaceWidth = 100;
	int canvasCoordinateSpaceHeight = 100;
	
	public RepeatBrickTest() {
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
		stage.getSpriteManager().reset();
		CatScheduler.get().clear();
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
	public void testExecute() {
		int timesToRepeat = 2;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		repeatBrick.execute();
		
		assertEquals(2, loopEndBrick.getTimesToRepeat());
	}
	
	/**
	 * 
	 */
	public void testTimesToRepeatEqualsZero() {
		int timesToRepeat = 0;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(repeatBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		repeatBrick.execute();
		
		assertEquals(1, startScript.getCurrentBrick());
	}
	
	/**
	 * 
	 */
	public void testTimesToRepeatLesserThanZero() {
		int timesToRepeat = -1;
		
		String spriteName = "spriteName";
		Sprite sprite = stage.getSpriteManager().getSprite(spriteName, true);
		
		String scriptName = "scriptName";
		StartScript startScript = new StartScript(sprite, scriptName);
		
		String costumeName1 = "coustme1";
		String costumeName2 = "coustme2";
		String costumeName3 = "coustme3";
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeName1);
		
		RepeatBrick repeatBrick = new RepeatBrick(spriteName, timesToRepeat);
		NextCostumeBrick nexCostumeBrick = new NextCostumeBrick(spriteName);
		LoopEndBrick loopEndBrick = new LoopEndBrick(spriteName, repeatBrick);
		
		repeatBrick.setLoopEndBrick(loopEndBrick);
		
		startScript.addBrick(setCostumeBrick);
		startScript.addBrick(repeatBrick);
		startScript.addBrick(nexCostumeBrick);
		startScript.addBrick(loopEndBrick);
		
		sprite.addScript(startScript);
		
		sprite.addCostumeData(createCostumeData(costumeName1));
		sprite.addCostumeData(createCostumeData(costumeName2));
		sprite.addCostumeData(createCostumeData(costumeName3));
		
		//hide costume, so drawSprite() in redrawScreen() does nothing
		sprite.getCostume().hide();
		//
		
		sprite.run();
		
		CatScheduler.get().execute();
		CatScheduler.get().execute();
		
		assertEquals(costumeName1, sprite.getCostume().getCostumeData().getName());
		
		assertEquals(0, CatScheduler.get().getThreadCount());
	}

}
