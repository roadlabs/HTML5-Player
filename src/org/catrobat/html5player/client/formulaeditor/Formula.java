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
package org.catrobat.html5player.client.formulaeditor;

import java.io.Serializable;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.formulaeditor.FormulaElement.ElementType;


public class Formula implements Serializable {

	private static final long serialVersionUID = 1L;
	private FormulaElement formulaTree;

	public Object readResolve() {

		if (formulaTree == null) {
			formulaTree = new FormulaElement(ElementType.NUMBER, "0 ", null);
		}


		return this;
	}

	public Formula(FormulaElement formEle) {
		formulaTree = formEle;

	}

	public Formula(Integer value) {
		formulaTree = new FormulaElement(ElementType.NUMBER, value.toString(), null);
	}

	public Formula(Double value) {
		formulaTree = new FormulaElement(ElementType.NUMBER, value.toString(), null);
	}

	public Formula(Float value) {
		formulaTree = new FormulaElement(ElementType.NUMBER, value.toString(), null);
	}

	public boolean interpretBoolean(Sprite sprite) {
		int result = interpretInteger(sprite);

		return result != 0 ? true : false;

	}

	public int interpretInteger(Sprite sprite) {
		Double interpretedValue = formulaTree.interpretRecursive(sprite);
		return interpretedValue.intValue();
	}

	public int interpretInteger(int minValue, int maxValue, Sprite sprite) {

		int interpretedIntValue = interpretInteger(sprite);

		if (minValue <= maxValue) {

			interpretedIntValue = Math.min(maxValue, interpretedIntValue);
			interpretedIntValue = Math.max(minValue, interpretedIntValue);
		}

		return interpretedIntValue;

	}

	public float interpretFloat(Sprite sprite) {
		Double interpretedValue = formulaTree.interpretRecursive(sprite);
		return interpretedValue.floatValue();
	}

	public float interpretFloat(float minValue, float maxValue, Sprite sprite) {

		float interpretedFloatValue = interpretFloat(sprite);

		if (minValue <= maxValue) {

			interpretedFloatValue = Math.min(maxValue, interpretedFloatValue);
			interpretedFloatValue = Math.max(minValue, interpretedFloatValue);
		}

		return interpretedFloatValue;
	}

	public void setRoot(FormulaElement formula) {
		formulaTree = formula;

	}

	public boolean containsElement(FormulaElement.ElementType elementType) {
		if (formulaTree.containsElement(elementType)) {
			return true;
		}

		return false;
	}

	public boolean isLogicalFormula() {
		return formulaTree.isLogicalOperator();
	}

	public boolean isSingleNumberFormula() {
		return formulaTree.isSingleNumberFormula();
	}

	@Override
	public Formula clone() {
		if (formulaTree != null) {
			return new Formula(formulaTree.clone());
		}

		return new Formula(0);
	}

}
