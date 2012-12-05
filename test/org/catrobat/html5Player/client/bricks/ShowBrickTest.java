package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ShowBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;

public class ShowBrickTest extends GWTTestCase {
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	public void testShow() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testShowSprite");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		sprite.getCostume().hide();
		assertFalse("Sprite is still visible after calling hide", sprite.getCostume().isVisible());

		ShowBrick showBrick = new ShowBrick(spriteName);
		showBrick.execute();
		assertTrue("Sprite is not visible after ShowBrick executed", sprite.getCostume().isVisible());
	}

	public void testNullSprite() {
		ShowBrick showBrick = new ShowBrick(null);
		showBrick.execute();
	}
}
