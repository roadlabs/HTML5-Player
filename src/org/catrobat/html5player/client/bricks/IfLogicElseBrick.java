package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;

public class IfLogicElseBrick extends Brick {

  public IfLogicElseBrick(String spriteName) {
    super(spriteName);
  }

  @Override
  protected boolean execute(Sprite sprite) {
    return false;
  }

}
