package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;



public class ChangeSizeByNBrickTest extends GWTTestCase{
	
	int bigger = 20;
	int smaller = -30;
	
	int veryBig = 20;
	int negative = -230;
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void testNormalBehaviour(){
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(0);
		canvas.setCoordinateSpaceWidth(0);
		stage.setCanvas(canvas);
		String spriteName = new String("testChangeSizeByNSprite1");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("ChangeSizeByNBrick initial value wrong", 1,
				(int) sprite.getCostume().getSize());
		
		ChangeSizeByNBrick changeBrick = new ChangeSizeByNBrick(spriteName, bigger);
		changeBrick.execute();
		
		assertEquals("ChangeSizeByNBrick initial value wrong", 1.2,
				 sprite.getCostume().getSize());
		
		
		
	}
	
	public void testNullSprite() {
		ChangeSizeByNBrick changeSizeByNBrick = new ChangeSizeByNBrick(null, bigger);
		changeSizeByNBrick.execute();
	}

}
