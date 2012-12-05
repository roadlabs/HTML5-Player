
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.HideBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class HideBrickTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}

	public void testHide() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testHideSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		assertTrue("Unexpected default visibility", sprite.getCostume().isVisible());
		HideBrick hideBrick = new HideBrick(spriteName);
		hideBrick.execute();
		assertFalse("Sprite is still visible after HideBrick executed", sprite.getCostume().isVisible());
	}

	public void testNullSprite() {
		HideBrick hideBrick = new HideBrick(null);
		hideBrick.execute();
	}
}
