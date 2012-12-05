package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.bricks.ComeToFrontBrick;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.junit.client.GWTTestCase;


public class ComeToFrontBrickTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}

	public void testComeToFront() {
		
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String bottomSpriteName = new String("testComeToFrontSprite1");
		Sprite bottomSprite = new Sprite(bottomSpriteName);
		stage.getSpriteManager().addSprite(bottomSprite);
		
		assertEquals("Unexpected initial z position of bottomSprite", 0, bottomSprite.getCostume().getZPosition());

		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String topSpriteName = new String("testComeToFrontSprite2");
		Sprite topSprite = new Sprite(topSpriteName);
		stage.getSpriteManager().addSprite(topSprite);
		assertEquals("Unexpected initial z position of topSprite", 0, topSprite.getCostume().getZPosition());

		topSprite.getCostume().setZPosition(2);
		assertEquals("topSprite z position should now be 2", 2, topSprite.getCostume().getZPosition());
	
		ComeToFrontBrick comeToFrontBrick = new ComeToFrontBrick(bottomSpriteName);
		comeToFrontBrick.execute();
		assertEquals("bottomSprite z position should now be 3", bottomSprite.getCostume().getZPosition(), 3);
	}

	public void testNullSprite() {
		ComeToFrontBrick comeToFrontBrick = new ComeToFrontBrick(null);
		comeToFrontBrick.execute();
	}

	public void testBoundaries() {
		Stage stage = Stage.getInstance();
		Canvas canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(100);
		canvas.setCoordinateSpaceWidth(100);
		stage.setCanvas(canvas);
		String spriteName = new String("testComeToFrontSprite3");
		Sprite sprite = new Sprite(spriteName);
		stage.getSpriteManager().addSprite(sprite);
		
		sprite.getCostume().setZPosition(Integer.MAX_VALUE);

		ComeToFrontBrick brick = new ComeToFrontBrick(spriteName);
		brick.execute();

		assertEquals("An Integer overflow occured during ComeToFrontBrick Execution", Integer.MAX_VALUE,
				sprite.getCostume().getZPosition());
	}
}
