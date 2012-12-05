package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.SetSizeToBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class SetSizeToBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	private float size = 70;


	public void testSize() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testSetSizeToSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertEquals("Unexpected initial sprite size value", 1d, sprite.getCostume().getSize());
	
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(spriteName, size);
		setSizeToBrick.execute();
		assertEquals("Incorrect sprite size value after SetSizeToBrick executed", (double) size / 100,
				sprite.getCostume().getSize());
	}

	public void testNullSprite() {
		SetSizeToBrick setSizeToBrick = new SetSizeToBrick(null, size);
		setSizeToBrick.execute();
	}

}
