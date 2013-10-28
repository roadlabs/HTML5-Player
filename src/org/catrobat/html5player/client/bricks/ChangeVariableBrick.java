package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.formulaeditor.UserVariable;


public class ChangeVariableBrick extends Brick {

  public ChangeVariableBrick(String sprite, Formula changeVariable, UserVariable userVariable) {
    super(sprite);
    this.changeVariable = changeVariable;
    this.userVariable = userVariable;   
  }


  private Formula changeVariable;
  private UserVariable userVariable;

  public void setUserVariable(UserVariable userVariable) {
      this.userVariable = userVariable;
  }

  public void setChangeVariable(Formula changeVariable) {
      this.changeVariable = changeVariable;
  }


  @Override
  public boolean execute(Sprite sprite) {
      if (userVariable == null) {
          return true;
      }
      double originalValue = userVariable.getValue();
      double value = changeVariable.interpretFloat(sprite);
      userVariable.setValue(originalValue + value);
      return true;
  }

}
