/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.html5player.client.formulaeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;


public class UserVariablesContainer {

	private  List<UserVariable> projectVariables;
	private  Map<Sprite, List<UserVariable>> spriteVariables;

	public UserVariablesContainer() {
		projectVariables = new ArrayList<UserVariable>();
		spriteVariables = new HashMap<Sprite, List<UserVariable>>();
	}


	public UserVariable getUserVariable(String userVariableName, Sprite sprite) {
		UserVariable var;
		var = findUserVariable(userVariableName, getOrCreateVariableListForSprite(sprite));
		if (var == null) {
			var = findUserVariable(userVariableName, projectVariables);
		}
		return var;
	}

	public void addSpriteUserVariable(String userVariableName, Double userVariableValue) {
		Sprite currentSprite = Stage.getInstance().getCurrentSprite();
		UserVariable userVariableToAdd = new UserVariable(userVariableName, userVariableValue);
		List<UserVariable> varList = getOrCreateVariableListForSprite(currentSprite);
		varList.add(userVariableToAdd);
	}

	public void addProjectUserVariable(String userVariableName, Double userVariableValue) {
		UserVariable userVariableToAdd = new UserVariable(userVariableName, userVariableValue);
		projectVariables.add(userVariableToAdd);
	}

	public void deleteUserVariableByName(String userVariableName) {
		Sprite currentSprite = Stage.getInstance().getCurrentSprite();
		UserVariable variableToDelete;
		List<UserVariable> spriteVariables = getOrCreateVariableListForSprite(currentSprite);
		variableToDelete = findUserVariable(userVariableName, spriteVariables);
		if (variableToDelete != null) {
			spriteVariables.remove(variableToDelete);
		}

		variableToDelete = findUserVariable(userVariableName, projectVariables);
		if (variableToDelete != null) {
			projectVariables.remove(variableToDelete);
		}
	}

	public List<UserVariable> getOrCreateVariableListForSprite(Sprite sprite) {
		List<UserVariable> vars = spriteVariables.get(sprite);
		if (vars == null) {
			vars = new ArrayList<UserVariable>();
			spriteVariables.put(sprite, vars);
		}
		return vars;
	}

	private UserVariable findUserVariable(String name, List<UserVariable> variables) {
		if (variables == null) {
			return null;
		}
		for (UserVariable variable : variables) {
			if (variable.getName().equals(name)) {
				return variable;
			}
		}
		return null;
	}

  public void printProjectVariables() {
    for (UserVariable uv : projectVariables) {
      System.out.println("UVAR: " + uv.getName() + ": " + uv.getValue());
    }
  }

}
