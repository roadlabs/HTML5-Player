package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.formulaeditor.Formula;

public class IfLogicBrick extends Brick {
  
  //private Action ifAction;
  //private Action elseAction;
  private Formula ifCondition;
  private boolean ifConditionValue;
  private boolean isInitialized = false;

  public IfLogicBrick(String spriteName) {
    super(spriteName);
  }

  @Override
  protected boolean execute(Sprite sprite) {
    // TODO Auto-generated method stub
    return false;
  }

}
