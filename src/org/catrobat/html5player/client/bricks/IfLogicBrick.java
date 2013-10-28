package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.formulaeditor.Formula;


public class IfLogicBrick extends Brick {

  private Brick ifAction = null;
  private Brick elseAction = null;
  private Formula ifCondition;
  private boolean ifConditionValue;
  private boolean isInitialized = false;
  private boolean isIfPartInitialized = false;

  public IfLogicBrick(String spriteName, Formula ifCondition) {
    super(spriteName);
    this.ifCondition = ifCondition;
  }

  @Override
  protected boolean execute(Sprite sprite) {
    if (!isInitialized) {
      begin(sprite);
      isInitialized = true;
    }
    CatrobatDebug.debug("iflogic executed with conditionvalue: " + ifConditionValue +" "+ ifAction.getClass());
    if (ifConditionValue) {
      if (ifAction == null) {
        return true;
      }
      return ifAction.execute(sprite);
    } else {
      if (elseAction == null) {
        return true;
      }
      return elseAction.execute(sprite);
    }
  }

  protected void begin(Sprite sprite) {
    ifConditionValue = ifCondition.interpretBoolean(sprite);
  }

  public void setIfAction(Brick ifAction) {
    this.ifAction = ifAction;
  }

  public void setElseAction(Brick elseAction) {
    this.elseAction = elseAction;
  }

  public void setIfCondition(Formula ifCondition) {
    this.ifCondition = ifCondition;
  }

  public void addAction(Brick b, String spriteName) {
    if (!this.isIfPartInitialized) {
      if (this.ifAction == null) {
        this.ifAction = b;
      } else if (this.ifAction instanceof SequenceBrick) {
        ((SequenceBrick) this.ifAction).getBrickList().add(b);
      } else {
        SequenceBrick sb = new SequenceBrick(spriteName);
        sb.getBrickList().add(this.ifAction);
        sb.getBrickList().add(b);
        this.ifAction = sb;
      }
    } else {
      if (this.elseAction == null) {
        this.elseAction = b;
      } else if (this.elseAction instanceof SequenceBrick) {
        ((SequenceBrick) this.elseAction).getBrickList().add(b);
      } else {
        SequenceBrick sb = new SequenceBrick(spriteName);
        sb.getBrickList().add(this.elseAction);
        sb.getBrickList().add(b);
        this.elseAction = sb;
      }
    }
  }

  public boolean isIfPartInitialized() {
    return isIfPartInitialized;
  }

  public void setIfPartInitialized(boolean isIfPartInitialized) {
    this.isIfPartInitialized = isIfPartInitialized;
  }
}
