package org.catrobat.html5player.client;

import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.formulaeditor.FormulaElement;
import org.catrobat.html5player.client.formulaeditor.FormulaElement.ElementType;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class FormulaParser {

  
  public static Formula parseFormula(Node n) throws Exception{
    Element formula = Parser.getChildElementByTagName(n,"formulaTree");
    if(formula == null){
      return new Formula(Double.parseDouble(n.getFirstChild().toString()));
    }
    FormulaElement fe = parseRecursive(formula, null);
    return new Formula(fe);
  }
  
  public static FormulaElement parseRecursive(Node element, FormulaElement parent) throws Exception{
    if(element == null){
      throw new Exception("formulaTree exception no root element");
    }
    Element value = Parser.getChildElementByTagName(element,"value");
    if(value == null){
      throw new Exception("formulaTree exception no value");
    }
    String valueStr = value.getFirstChild().toString();
    Element typeEl = Parser.getChildElementByTagName(element,"type");
    if(typeEl == null){
      throw new Exception("formulaTree exception no type");
    }
    
    ElementType type = parseElementType(typeEl);
    FormulaElement formulaElement = new FormulaElement(type,valueStr,parent);
    Element rightChild = Parser.getChildElementByTagName(element,"rightChild");
    if(rightChild != null){
      formulaElement.setRightChild(parseRecursive(rightChild, formulaElement));
    }
    Element leftChild = Parser.getChildElementByTagName(element,"leftChild");
    if(leftChild != null){
      formulaElement.setLeftChild(parseRecursive(leftChild, formulaElement));
    }
    return formulaElement;
    
  }
  public static ElementType parseElementType(Node node) throws Exception{

    return FormulaElement.ElementType.valueOf(node.getFirstChild().toString());
  }
  
}
