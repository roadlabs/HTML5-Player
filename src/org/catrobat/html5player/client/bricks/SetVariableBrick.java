/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.formulaeditor.UserVariable;


public class SetVariableBrick extends Brick {

    private Formula changeVariable;
    private UserVariable userVariable;

	public SetVariableBrick(String spriteName, Formula changeVariable, UserVariable userVariable) {
      super(spriteName);
      this.changeVariable = changeVariable;
      this.userVariable = userVariable;   
    }

	@Override
    protected boolean execute(Sprite sprite) {
		if (userVariable == null) {
			return false;
		}
		System.out.println("initial VAR SET VALUE: "+ userVariable.getValue());
		double value = changeVariable.interpretFloat(sprite);
		userVariable.setValue(value);
		System.out.println("after change SET VAR VALUE: "+ userVariable.getValue());
		//Stage.getInstance().getUserVariables().printProjectVariables();
		//System.out.println("after change SET VAR VALUE: "+ Stage.getInstance().getUserVariables().getUserVariable("testvar", null).getValue());
		return true;
	}

	public void setUserVariable(UserVariable userVariable) {
		this.userVariable = userVariable;
	}

	public void setChangeVariable(Formula changeVariable) {
		this.changeVariable = changeVariable;
	}

  public Formula getChangeVariable() {
    return changeVariable;
  }

  public UserVariable getUserVariable() {
    return userVariable;
  }

}
