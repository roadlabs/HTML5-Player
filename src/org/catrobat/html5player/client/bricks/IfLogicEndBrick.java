package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;

public class IfLogicEndBrick extends Brick {

  public IfLogicEndBrick(String spriteName) {
    super(spriteName);
  }

  @Override
  protected boolean execute(Sprite sprite) {
    return false;
  }

}
