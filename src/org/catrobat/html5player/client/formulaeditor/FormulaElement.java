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

import java.io.Serializable;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;


public class FormulaElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum ElementType {
		OPERATOR, FUNCTION, NUMBER, SENSOR, USER_VARIABLE, BRACKET
	}

	public static final Double NOT_EXISTING_USER_VARIABLE_INTERPRETATION_VALUE = 0d;

	private ElementType type;
	private String value;
	private FormulaElement leftChild = null;
	private FormulaElement rightChild = null;
	private transient FormulaElement parent = null;

	public FormulaElement(ElementType type, String value, FormulaElement parent) {
		this.type = type;
		this.value = value;
		this.parent = parent;
	}

	public FormulaElement(ElementType type, String value, FormulaElement parent, FormulaElement leftChild,
			FormulaElement rightChild) {
		this.type = type;
		this.value = value;
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;

		if (leftChild != null) {
			this.leftChild.parent = this;
		}
		if (rightChild != null) {
			this.rightChild.parent = this;
		}

	}

	public String getValue() {
		return value;
	}

	public FormulaElement getRoot() {
		FormulaElement root = this;
		while (root.getParent() != null) {
			root = root.getParent();
		}
		return root;
	}

	public Double interpretRecursive(Sprite sprite) {

		Double returnValue = 0d;

		switch (type) {
			case BRACKET:
				returnValue = rightChild.interpretRecursive(sprite);
				break;
			case NUMBER:
				returnValue = Double.parseDouble(value);
				break;
			case OPERATOR:
				Operators operator = Operators.getOperatorByValue(value);
				returnValue = interpretOperator(operator, sprite);
				break;
			case FUNCTION:
				Functions function = Functions.getFunctionByValue(value);
				returnValue = interpretFunction(function, sprite);
				break;
			case SENSOR:

				returnValue = GetResultFromSensor(value, sprite);

				break;
			case USER_VARIABLE:
				UserVariablesContainer userVariables = Stage.getInstance().getUserVariables();
				UserVariable userVariable = userVariables.getUserVariable(value, sprite);
				if (userVariable == null) {
					returnValue = NOT_EXISTING_USER_VARIABLE_INTERPRETATION_VALUE;
					break;
				}
				returnValue = userVariable.getValue();
				break;

		}

		returnValue = checkDegeneratedDoubleValues(returnValue);

		return returnValue;

	}

	private double GetResultFromSensor(String sensorValue, Sprite sprite){

		double result = 0.0F;
		Sensors sensor = Sensors.getSensorByValue(value);
		if (sensor.isLookSensor) {
			result = interpretLookSensor(sensor, sprite);
		}
		else{

			switch (sensor) {
			case COMPASS_DIRECTION:
				result = interpretCompass(sensor, sprite);
				break;
			default:
				break;
			}
		}

		return result;
	}

	private double interpretCompass(Sensors sensor,Sprite sprite)
	{

		return 0.0F;
	}

	private Double interpretFunction(Functions function, Sprite sprite) {
		Double left = null;

		if (leftChild != null) {
			left = leftChild.interpretRecursive(sprite);
		}

		switch (function) {
			case SIN:
				return java.lang.Math.sin(Math.toRadians(left));

			case COS:
				return java.lang.Math.cos(Math.toRadians(left));

			case TAN:
				return java.lang.Math.tan(Math.toRadians(left));

			case LN:
				return java.lang.Math.log(left);

			case LOG:
				return java.lang.Math.log10(left);

			case SQRT:
				return java.lang.Math.sqrt(left);

			case RAND:
				Double right = rightChild.interpretRecursive(sprite);
				Double minimum;
				Double maximum;

				if (right > left) {
					minimum = left;
					maximum = right;
				} else {
					minimum = right;
					maximum = left;
				}

				Double randomDouble = minimum + (java.lang.Math.random() * (maximum - minimum));

				if (isInteger(minimum) && isInteger(maximum)
						&& !(rightChild.type == ElementType.NUMBER && rightChild.value.contains("."))
						&& !(leftChild.type == ElementType.NUMBER && leftChild.value.contains("."))) {

					if ((Math.abs(randomDouble) - (int) Math.abs(randomDouble)) >= 0.5) {
						return Double.valueOf(randomDouble.intValue()) + 1;
					} else {
						return Double.valueOf(randomDouble.intValue());
					}

				} else {
					return randomDouble;
				}

			case ABS:
				return java.lang.Math.abs(left);

			case ROUND:
				return (double) java.lang.Math.round(left);

			case PI:
				return java.lang.Math.PI;
		}

		return 0d;
	}

	private Double interpretOperator(Operators operator, Sprite sprite) {

		if (leftChild != null) {// binär operator
			Double left = leftChild.interpretRecursive(sprite);
			Double right = rightChild.interpretRecursive(sprite);

			switch (operator) {
				case PLUS:
					return left + right;
				case MINUS:
					return left - right;
				case MULT:
					return left * right;
				case DIVIDE:
					return left / right;
				case POW:
					return java.lang.Math.pow(left, right);
				case EQUAL:
					return left.equals(right) ? 1d : 0d; //TODO Double equality, may round first?
				case NOT_EQUAL:
					return left.equals(right) ? 0d : 1d;//TODO Double equality, may round first?
				case GREATER_THAN:
					return left.compareTo(right) > 0 ? 1d : 0d;
				case GREATER_OR_EQUAL:
					return left.compareTo(right) >= 0 ? 1d : 0d;
				case SMALLER_THAN:
					return left.compareTo(right) < 0 ? 1d : 0d;
				case SMALLER_OR_EQUAL:
					return left.compareTo(right) <= 0 ? 1d : 0d;
				case LOGICAL_AND:
					return (left * right) != 0d ? 1d : 0d;
				case LOGICAL_OR:
					return left != 0d || right != 0d ? 1d : 0d;
        default: break;
			}

		} else {//unary operators
			Double right = rightChild.interpretRecursive(sprite);

			switch (operator) {
				case MINUS:
					return -right;
				case LOGICAL_NOT:
					return right == 0d ? 1d : 0d;
        default:
          break;
			}

		}

		return 0d;
	}

	private Double interpretLookSensor(Sensors sensor, Sprite sprite) {
		Double returnValue = 0d;
		switch (sensor) {
//			case LOOK_BRIGHTNESS:
//				returnValue = (double) sprite.look.getBrightnessInUserInterfaceDimensionUnit();
//				break;
//			case LOOK_GHOSTEFFECT:
//				returnValue = (double) sprite.look.getGhostEffectInUserInterfaceDimensionUnit();
//				break;
//			case LOOK_LAYER:
//				returnValue = (double) sprite.look.getZIndex();
//				break;
			case LOOK_ROTATION:
				SensorController.GetController().ActivateSensor(sensor);

				//returnValue = (double) sprite.look.getRotationInUserInterfaceDimensionUnit();
				break;
//			case LOOK_SIZE:
//				returnValue = (double) sprite.look.getSizeInUserInterfaceDimensionUnit();
//				break;
//			case LOOK_X:
//				returnValue = (double) sprite.look.getXInUserInterfaceDimensionUnit();
//				break;
//			case LOOK_Y:
//				returnValue = (double) sprite.look.getYInUserInterfaceDimensionUnit();
//				break;
		default:
			break;
		}
		return returnValue;
	}

	private Double checkDegeneratedDoubleValues(Double valueToCheck) {
		if (valueToCheck.doubleValue() == Double.NEGATIVE_INFINITY) {
			return -Double.MAX_VALUE;
		}
		if (valueToCheck.doubleValue() == Double.POSITIVE_INFINITY) {
			return Double.MAX_VALUE;
		}
		if (valueToCheck.isNaN()) {
			return 1.0;
		}

		return valueToCheck;
	}

	public FormulaElement getParent() {
		return parent;
	}

	public void setRightChild(FormulaElement rightChild) {
		this.rightChild = rightChild;
		this.rightChild.parent = this;
	}

	public void setLeftChild(FormulaElement leftChild) {
		this.leftChild = leftChild;
		this.leftChild.parent = this;
	}

	public void replaceElement(FormulaElement current) {
		parent = current.parent;
		leftChild = current.leftChild;
		rightChild = current.rightChild;
		value = current.value;
		type = current.type;

		if (leftChild != null) {
			leftChild.parent = this;
		}
		if (rightChild != null) {
			rightChild.parent = this;
		}
	}

	public void replaceElement(ElementType type, String value) {
		this.value = value;
		this.type = type;
	}

	public void replaceWithSubElement(String operator, FormulaElement rightChild) {

		FormulaElement cloneThis = new FormulaElement(ElementType.OPERATOR, operator, this.getParent(), this,
				rightChild);

		cloneThis.parent.rightChild = cloneThis;
	}

	private boolean isInteger(double value) {

		if ((Math.abs(value) - (int) Math.abs(value) < Double.MIN_VALUE)) {
			return true;
		}

		return false;
	}

	public boolean isLogicalOperator() {
		if (type == ElementType.OPERATOR) {
			return Operators.getOperatorByValue(value).isLogicalOperator;
		}
		return false;
	}

	public boolean containsElement(ElementType elementType) {
		if (type.equals(elementType)) {
			return true;
		}

		if (leftChild != null && leftChild.containsElement(elementType)) {
			return true;
		}

		if (rightChild != null && rightChild.containsElement(elementType)) {
			return true;
		}
		return false;
	}

	public boolean isSingleNumberFormula() {
		if (type == ElementType.OPERATOR) {
			Operators operator = Operators.getOperatorByValue(value);
			if (operator == Operators.MINUS && leftChild == null) {
				return rightChild.isSingleNumberFormula();
			}
			return false;
		} else if (type == ElementType.NUMBER) {
			return true;
		}
		return false;
	}

	public FormulaElement clone() {
		FormulaElement leftChildClone = leftChild == null ? null : leftChild.clone();
		FormulaElement rightChildClone = rightChild == null ? null : rightChild.clone();
		return new FormulaElement(type, new String(value == null ? "" : value), null, leftChildClone, rightChildClone);
	}

}
