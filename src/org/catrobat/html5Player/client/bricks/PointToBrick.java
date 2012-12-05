package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;

public class PointToBrick extends Brick {

	private String pointedSpriteName;
	private double rotationDegrees = 0.0;
	
	public PointToBrick(String spriteName, String pointedSpriteName) {
		super(spriteName);
		this.pointedSpriteName = pointedSpriteName;
	}
	
	@Override
	protected boolean execute(Sprite sprite) {
	
		Sprite pointedSprite = Stage.getInstance().getSpriteManager().getSprite(pointedSpriteName, false);
		
		if(pointedSprite == null) {
			pointedSprite = sprite;
		}
		
		CatrobatDebug.on();
		CatrobatDebug.console("pointedSprite: " + pointedSprite);
		
		int spriteXPosition = (int)sprite.getCostume().getXPosition();
		int spriteYPosition = (int)sprite.getCostume().getYPosition();
		
		int pointedSpriteXPosition = (int)pointedSprite.getCostume().getXPosition();
		int pointedSpriteYPosition = (int)pointedSprite.getCostume().getYPosition();
		
		double base = 0.0;
		double height = 0.0;
		double value = 0.0;
		
		if(spriteXPosition == pointedSpriteXPosition && spriteYPosition == pointedSpriteYPosition) {
			rotationDegrees = 90.0;
		}
		else if(spriteXPosition == pointedSpriteXPosition || spriteYPosition == pointedSpriteYPosition) {
			if(spriteXPosition == pointedSpriteXPosition) {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 180.0;
				}
				else {
					rotationDegrees = 0.0;
				}
			}
			else {
				if(spriteXPosition > pointedSpriteXPosition) {
					rotationDegrees = 270.0;
				}
				else {
					rotationDegrees = 90.0;
				}
			}
		}
		else {
			
			base = Math.abs(spriteYPosition - pointedSpriteYPosition);
			height = Math.abs(spriteXPosition - pointedSpriteXPosition);
			value = Math.toDegrees(Math.atan(base/height));
			
			if(spriteXPosition < pointedSpriteXPosition) {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 90.0 + value;
				}
				else {
					rotationDegrees = 90.0 - value;
				}
			}
			else {
				if(spriteYPosition > pointedSpriteYPosition) {
					rotationDegrees = 270.0 - value;
				}
				else {
					rotationDegrees = 270.0 + value;
				}
			}
		}
		
		// the MINUS is important because canvas positive rotation is clockwise
		sprite.getCostume().setRotation( -(-rotationDegrees + 90.0) );
		
		CatrobatDebug.off();
		
		return true;
	}

}
