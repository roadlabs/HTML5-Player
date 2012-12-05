package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.SpriteManager;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetCostumeBrickTest extends GWTTestCase {

	private Stage stage;
	private SpriteManager manager;
	
	/**
	 * 
	 */
	public SetCostumeBrickTest() {
		stage = Stage.getInstance();
		manager = stage.getSpriteManager();
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void gwtSetUp() {
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
	}
	
	public void gwtTearDown() {
		manager.reset();
	}
	
	/**
	 * 
	 */
	public void testCostumeDataNotInList() {
		
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		Costume spriteCostume = sprite.getCostume();
		
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
	}
	
	/**
	 * 
	 */
	public void testCostumeDataInList() {
		String spriteName = "testSprite";
		String costumeDataName = "testCostumeData";
		Sprite sprite = manager.getSprite(spriteName, true);

		CostumeData newCostumeData = new CostumeData();
		newCostumeData.setName(costumeDataName);
		sprite.addCostumeData(newCostumeData);
		
		Costume spriteCostume = sprite.getCostume();
		
		CostumeData spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(null, spriteCostumeData);
		
		SetCostumeBrick setCostumeBrick = new SetCostumeBrick(spriteName, costumeDataName);
		assertTrue(setCostumeBrick.execute());
		
		spriteCostumeData = spriteCostume.getCostumeData();
		assertEquals(costumeDataName, spriteCostumeData.getName());
	}

}
