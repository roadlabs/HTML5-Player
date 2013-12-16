/**
 * Catroid: An on-device visual programming system for Android devices Copyright (C) 2010-2013 The
 * Catrobat Team (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero General Public License, version 3,
 * is available at http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client;

import java.util.ArrayList;
import java.util.List;

import org.catrobat.html5player.client.bricks.*;

import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.common.SoundInfo;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.formulaeditor.UserVariable;

import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.Script;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.scripts.WhenScript;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Attr;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;
import com.mouchel.gwt.xpath.client.XPath;

public class Parser {

  private String currentXML;
  private SpriteManager manager;

  private boolean parsingComplete = false;
  
  private boolean ignoreUnimplementedBricks = false;

  public Parser() {

  }

  public void parseXML(SpriteManager spriteManager, String xmlString) {
    try {
      currentXML = xmlString;
      manager = spriteManager;

      Document messageDom = XMLParser.parse(currentXML);
      if (hasDomDocumentPageNotFoundError(messageDom)) {
        return;
      }
      if (!isVersionOK(messageDom)) {
        return;
      }
      
      parseScreenResolution(messageDom);
      parseUserVariableList(getChildElementByTagName(messageDom.getDocumentElement(),"variables"));
      if (parseAndCreateObjects(messageDom)) {
        parserFinished();
        CatrobatDebug.info("Parser finished");
      }
    } catch (DOMException e) {
      Window.alert("Could not parse XML document. " + e.getMessage());
    }
  }

  // ##########################################################################

  private boolean hasDomDocumentPageNotFoundError(Document messageDom) {
    NodeList errornodes = ((Element) messageDom.getFirstChild()).getElementsByTagName("error");
    if (errornodes != null && errornodes.getLength() != 0) {
      Element errorElement = (Element) errornodes.item(0);
      NodeList codes = errorElement.getElementsByTagName("code");
      if (codes != null && codes.getLength() != 0) {
        if (codes.item(0).toString().contains("ajax_request_page_not_found")) {
          Window
              .alert("Project specification not supported or no project found for the given project number.");
          return true;
        }
      }
    }
    return false;
  }

  private boolean isVersionOK(Document messageDom) {
    NodeList header =
        ((Element) messageDom.getFirstChild()).getElementsByTagName("header");
    if (header != null && header.getLength() != 0) {
      Element version = getChildElementByTagName(header.item(0),"catrobatLanguageVersion");
      if (version != null) {
        double v = Double.parseDouble(version.getFirstChild().toString());
        if (v >= 0.6) {
          return true;
        }
      }
    }
    Window.alert("Project version not supported!");
    return false;
  }

  public static Element getChildElementByTagName(Node context, String name) {
    if (context == null || context.getNodeType() != Node.ELEMENT_NODE) return null;
    NodeList children = context.getChildNodes();
    int childLength = children.getLength();
    for (int i = 0; i < childLength; i++) {
      Node child = children.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE
          && (((Element) child).getNodeName().equals(name) || ((Element) child).getTagName()
              .equals(name))) {
        return (Element) child;
      }
    }
    return null;
  }

  private List<Element> getChildElementsByTagName(Node context, String name) {
    List<Element> result = new ArrayList<Element>();
    if (context != null) {
      NodeList children = context.getChildNodes();
      int childLength = children.getLength();
      for (int i = 0; i < childLength; i++) {
        Node child = children.item(i);
        if (child.getNodeType() == Node.ELEMENT_NODE
            && ((Element) child).getNodeName().equals(name)) {
          result.add((Element) child);
        }
      }
    }
    return result;
  }

  private List<Element> getChildElements(Node context) {
    List<Element> result = new ArrayList<Element>();
    if (context != null) {
      NodeList children = context.getChildNodes();
      int childLength = children.getLength();
      for (int i = 0; i < childLength; i++) {
        Node child = children.item(i);
        if (child.getNodeType() == Node.ELEMENT_NODE) {
          result.add((Element) child);
        }
      }
    }
    return result;
  }

  private String getText(Node context) {
    if (context == null) return null;
    StringBuffer text = new StringBuffer();
    if (context.getNodeType() == Node.ELEMENT_NODE) {

      NodeList children = context.getChildNodes();
      int childLength = children.getLength();
      boolean wrap = false;
      for (int i = 0; i < childLength; i++) {
        Node child = children.item(i);
        if (child.getNodeType() == Node.TEXT_NODE) {
          if (wrap) {
            text.append("\n");
          }
          wrap = true;
          text.append(((Text) child).getData());
        }
      }
    } else if (context.getNodeType() == Node.TEXT_NODE) {
      text.append(((Text) context).getData());
    } else if (context.getNodeType() == Node.ATTRIBUTE_NODE) {
      text.append(((Attr) context).getValue());
    }
    return text.toString();
  }

  // ##########################################################################

  /**
   * Checks if a reference has the correct syntax for XPath to evaluate. If a reference refers to
   * the first element of a set of elements, than there must be square brackets with a 1 in between
   *
   * @param reference
   * @param substring Element to check as a String
   * @return A reference with correct syntax
   */
  private String checkReference(String reference, String substring) {
    String checkedReference = "";

    if (reference.matches("(.*)" + substring + "(\\[){1}(\\d){1,}(\\]){1}(.*)")) {
      CatrobatDebug.debug("reference (" + reference + ") contains square brackets");
      return reference;
    } else {
      CatrobatDebug.debug("reference (" + reference + ") does not contain square brackets");

      int offset = reference.lastIndexOf(substring) + substring.length();

      StringBuilder newReference = new StringBuilder(reference);
      newReference.insert(offset, "[1]");

      checkedReference = newReference.toString();
    }

    return checkedReference;
  }

  // ##########################################################################

  private void parseUserVariableList(Node tree)
  {
    if(tree == null)
      return;
    Element userVariables = getChildElementByTagName(tree,"programVariableList");
    if(userVariables == null)
      return;

    for(int i = 0; i < getChildElements(userVariables).size();i++)
    {
      Element var = getChildElements(userVariables).get(i);
      if (var.hasAttribute("reference")) {
        String objectReference = checkReference(var.getAttribute("reference"), "userVariable");
        Element userVariable = XPath.evaluateSingle(var, objectReference, Element.class);
        if(userVariable == null){
          return;
        }
        String name = "";
        double value = 0.0;
        Element nameEl = getChildElementByTagName(userVariable,"name");

        if(nameEl != null)
        {
          name = nameEl.getFirstChild().toString();
        }
        Element valueEl = getChildElementByTagName(userVariable,"value");
        if(nameEl != null)
        {
          value = Double.parseDouble(valueEl.getFirstChild().toString());
        }
        Stage.getInstance().getUserVariables().addProjectUserVariable(name, value);
      }
    }
  }

  private void parseScreenResolution(Document messageDom) {
    Node header = getChildElementByTagName(messageDom.getDocumentElement(), "header");
    Node nodeScreenHeight = getChildElementByTagName(header, Const.SCREEN_HEIGHT);
    Node nodeScreenWidth = getChildElementByTagName(header, Const.SCREEN_WIDTH);

    int dimX = 0;
    int dimY = 0;

    if (nodeScreenWidth != null) {
      dimX = Integer.valueOf(nodeScreenWidth.getFirstChild().getNodeValue());
    } else {
      CatrobatDebug.warn("Method parseScreenResolution: nodeScreenWidth is null");
    }

    if (nodeScreenHeight != null) {
      dimY = Integer.valueOf(nodeScreenHeight.getFirstChild().getNodeValue());
    } else {
      CatrobatDebug.warn("Method parseScreenResolution: nodeScreenHeight is null");
    }
    setRootCanvasSize(dimX, dimY);
  }

  private boolean parseAndCreateObjects(Document messageDom) {
    Element objectListNode = getChildElementByTagName(messageDom.getDocumentElement(), "objectList");
    List<Element> objectNodes = getChildElementsByTagName(objectListNode, "object");
    for (Element objectNode : objectNodes) {
      String name;
      Node lookList;
      Node scriptList;
      Node soundList;

      if (objectNode.hasAttribute("reference")) {
        String objectReference = checkReference(objectNode.getAttribute("reference"), "object");
        Element referencedObjectNode = XPath.evaluateSingle(objectNode, objectReference, Element.class);
        if(referencedObjectNode == null){
          List<Element> refs = XPath.evaluate(objectNode, objectReference+"[1]", Element.class);
          referencedObjectNode = refs.get(0);
        }
        objectNode = referencedObjectNode;
      }
      name = getText(getChildElementByTagName(objectNode, "name"));
      lookList = getChildElementByTagName(objectNode, "lookList");
      scriptList = getChildElementByTagName(objectNode, "scriptList");
      soundList = getChildElementByTagName(objectNode, "soundList");

      Sprite object = createObject(name, lookList, scriptList, soundList);
      if (object == null) {
        Window.alert("Could not parse XML document. There are unsupported elements!");
        return false;
      }
      manager.addSprite(object);
    }
    return true;
  }

  public Sprite createObject(String name, Node lookList, Node scriptList, Node soundList) {

    Sprite object = manager.getSprite(name, true);
    if(object == null){
      System.out.println("object creation of "+name+" failed");
    }

    if (name.equals(Const.BACK_GROUND_GER) || name.equals(Const.BACK_GROUND_ENG)) {
      object.setBackground(true);
    }
    List<Element> looks = getChildElementsByTagName(lookList, "look");
    if (looks == null || looks.isEmpty()) {
      System.out.println("look not found for object "+name +"!");
      looks = getChildElementsByTagName(lookList, "look");
    }

    for (Element look : looks) {
      LookData lookData = new LookData();
      String filename = getText(getChildElementByTagName(look, "fileName"));

      CatrobatDebug.debug("FileName: " + filename);

      lookData.setFilename(filename);
      String lookName = getText(getChildElementByTagName(look, "name"));
      lookData.setName(lookName);

      CatrobatDebug.debug("Name: " + lookName);

      lookData.setHeight(0);
      lookData.setWidth(0);

      object.addLookData(lookData);
      CatrobatDebug.debug("LookData: " + lookData.getName());
      CatrobatDebug.debug("File: " + lookData.getFilename() + " Height: " + lookData.getHeight()
          + " Width: " + lookData.getWidth());

      // add image name and url to ImageHandler
      // String url = Const.PROJECT_PATH + Stage.getInstance().getProjectNumber() + "/images/"+
      // filename;
      String url =
          "http://" + Window.Location.getHost() + "/Html5Player/fileupload?name=" + filename;
      ImageHandler.get().addImage(filename, url);
    }

    List<Element> scripts = getChildElements(scriptList);

    for (Element scriptElement : scripts) {
      Script script = checkScript(scriptElement, object);
      if(script == null)
      {
        System.out.println("error parsing script: "+scriptElement.toString());
        return null;
      }

      Element brickListElement = getChildElementByTagName(scriptElement, "brickList");


      if (brickListElement != null) {
        List<Element> brickElements = getChildElements(brickListElement);

        List<IfLogicBrick> openIfLogicBricks = new ArrayList<IfLogicBrick>();
        for (Element brickElement : brickElements) {
          Brick brick = null;
          try {
            brick = checkBrick(brickElement, object, script);
          } catch (Exception e) {
            System.out.println(brickElement);
            System.out.println("Brick Creation Exception: "+e.toString());
            return null;
          }
          if (brick != null && script != null) {
            if(openIfLogicBricks.size() > 0){
              if(brick instanceof IfLogicElseBrick){
                openIfLogicBricks.get(openIfLogicBricks.size()-1).setIfPartInitialized(true);
              } else if(brick instanceof IfLogicEndBrick){
                openIfLogicBricks.remove(openIfLogicBricks.size()-1);
              }else{
                openIfLogicBricks.get(openIfLogicBricks.size()-1).addAction(brick, object.getName());
                if(brick instanceof IfLogicBrick){
                  openIfLogicBricks.add((IfLogicBrick) brick);
                }
              }
            }
            else{
              script.addBrick(brick);
              if(brick instanceof IfLogicBrick){
                openIfLogicBricks.add((IfLogicBrick) brick);
              }
            }
          } else {
            CatrobatDebug.debug("Brick Parsing Problem: "+brickElement.toString() +"-"+ script.toString() +"-"+ object.toString());
            return null;
          }
        }
      }
      object.addScript(script);
    }
    return object;
  }

  private Brick checkBrick(Element brickNode, Sprite object, Script script) throws Exception {

    Element objectReference = getChildElementByTagName(brickNode, "object");

    String reference =
        objectReference != null ? objectReference.getAttribute("reference") : null;

    Element referencedObject =
        (objectReference != null && reference != null) ? XPath.evaluateSingle(
            objectReference, reference, Element.class) : null;

    String objName = object.getName();
    if (referencedObject != null && referencedObject.getNodeName().equals("object")) {
      String referenceName = getText(getChildElementByTagName(referencedObject, "name"));
      if (referenceName == null || referenceName.trim().length() == 0) {
        Stage.getInstance().log(
            "Invalid object reference: " + referencedObject.getNodeName() + " for " + reference);
      } else if (!objName.equals(referenceName)) {
        objName = referenceName;
      }
    } else {
      CatrobatDebug.debug("INVALID - checkBrick - objectElement: " + referencedObject);
    }
    if (brickNode.getNodeName().equals("setLookBrick")) {

      Element lookReferenceElement = getChildElementByTagName(brickNode, "look");
      String lookReference =
          lookReferenceElement != null ? checkReference(
              lookReferenceElement.getAttribute("reference"), "look") : null;
      Element lookElement =
          lookReferenceElement != null && lookReference != null ? XPath.evaluateSingle(
              lookReferenceElement, lookReference, Element.class) : null;

      String lookName = null;
      if (lookElement != null) {
        lookName = getText(getChildElementByTagName(lookElement, "name"));
      } else {
        System.out.println("Look is null");
      }
      return new SetLookBrick(objName, lookName);
    } else if (brickNode.getNodeName().equals("waitBrick")) {
      Formula waitTime = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "timeToWaitInSeconds"));
      return new WaitBrick(objName, waitTime, script);
    } else if (brickNode.getNodeName().equals("playSoundBrick")) {

      CatrobatDebug.debug("PlaySoundBrick:");

      Element soundInfoElement = getChildElementByTagName(brickNode, "sound");
      SoundInfo soundInfo = new SoundInfo();
      String soundId = null;
      String fileName = null;

      CatrobatDebug.debug("soundInfo: " + soundInfoElement);

      if (soundInfoElement != null) {
        if (soundInfoElement.hasAttribute("reference")) {
          String soundInfoReference =
              checkReference(soundInfoElement.getAttribute("reference"), "PlaySoundBrick");
          Element referencedSoundInfoElement =
              XPath.evaluateSingle(soundInfoElement, soundInfoReference, Element.class);

          CatrobatDebug.debug("PlaySoundBrick has a reference-attribute: " + soundInfoReference);
          CatrobatDebug.debug("referenced soundInfo: " + referencedSoundInfoElement);

          if (referencedSoundInfoElement != null) {
            soundInfoElement = referencedSoundInfoElement;
            CatrobatDebug.debug("soundInfoElement got set to the referenced soundInfo");
          } else {
            CatrobatDebug.warn("referenced soundInfo is null");
            return null;
          }
        }

        fileName = getText(getChildElementByTagName(soundInfoElement, "fileName"));
        CatrobatDebug.debug("Filename: " + fileName);
        soundInfo.setFileName(fileName);
        soundInfo.setTitle(getText(getChildElementByTagName(soundInfoElement, "name")));
        CatrobatDebug.debug("Title: "+ getText(getChildElementByTagName(soundInfoElement, "name")));
        soundId = fileName.split("_")[0];
        CatrobatDebug.debug("Sound ID: " + soundId);
        soundInfo.setId(soundId);
      }

      if (soundId == null || soundId.trim().length() == 0) {
        System.out.println("sound is null");
      } else {
        object.addSound(soundInfo);
      }
      return new PlaySoundBrick(objName, soundId);
    } else if (brickNode.getNodeName().equals("changeVolumeByNBrick")) {
      Formula volume = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "volume"));
      return new ChangeVolumeByBrick(objName, volume);
    } else if (brickNode.getNodeName().equals("setVolumeToBrick")) {
      Formula volume = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "volume"));
      return new SetVolumeToBrick(objName, volume);
    } else if (brickNode.getNodeName().equals("placeAtBrick")) {
      Formula xPosition = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "xPosition"));
      Formula yPosition = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "yPosition"));
      return new PlaceAtBrick(objName, xPosition, yPosition);
    } else if (brickNode.getNodeName().equals("changeSizeByNBrick")) {
      Formula size = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "size"));
      return new ChangeSizeByNBrick(objName, size);
    } else if (brickNode.getNodeName().equals("setYBrick")) {
      Formula yPosition = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "yPosition"));
      return new SetYBrick(objName, yPosition);
    } else if (brickNode.getNodeName().equals("setXBrick")) {
      Formula xPosition = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "xPosition"));
      return new SetXBrick(objName, xPosition);
    } else if (brickNode.getNodeName().equals("changeXByNBrick")) {
      Formula deltaX = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "xMovement"));
      return new ChangeXByBrick(objName, deltaX);
    } else if (brickNode.getNodeName().equals("changeYByNBrick")) {
      Formula deltaY = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "yMovement"));
      return new ChangeYByBrick(objName, deltaY);
    } else if (brickNode.getNodeName().equals("hideBrick")) {
      return new HideBrick(objName);
    } else if (brickNode.getNodeName().equals("showBrick")) {
      return new ShowBrick(objName);
    } else if (brickNode.getNodeName().equals("stopAllSoundsBrick")) {
      return new StopAllSoundsBrick(objName);
    } else if (brickNode.getNodeName().equals("turnLeftBrick")) {
      Formula degrees = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "degrees"));
      return new TurnLeftBrick(objName, degrees);
    } else if (brickNode.getNodeName().equals("turnRightBrick")) {
      Formula degrees = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "degrees"));
      return new TurnRightBrick(objName, degrees);
    } else if (brickNode.getNodeName().equals("pointInDirectionBrick")) {
      Formula degrees = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "degrees"));
      return new PointInDirectionBrick(objName, degrees);
    } else if (brickNode.getNodeName().equals("goNStepsBackBrick")) {
      Formula steps = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "steps"));
      return new GoNStepsBackBrick(objName, steps);
    } else if (brickNode.getNodeName().equals("comeToFrontBrick")) {
      return new ComeToFrontBrick(objName);
    } else if (brickNode.getNodeName().equals("glideToBrick")) {
      Formula duration = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "durationInSeconds"));
      Formula xDestination = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "xDestination"));
      Formula yDestination = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "yDestination"));
      return new GlideToBrick(objName, duration, xDestination, yDestination, script);
    } else if (brickNode.getNodeName().equals("setSizeToBrick")) {
      Formula size = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "size"));
      return new SetSizeToBrick(objName, size);
    } else if (brickNode.getNodeName().equals("broadcastBrick")) {
      String message = getText(getChildElementByTagName(brickNode, "broadcastMessage"));
      return new BroadcastBrick(objName, message);
    } else if (brickNode.getNodeName().equals("broadcastWaitBrick")) {
      String message = getText(getChildElementByTagName(brickNode, "broadcastMessage"));
      return new BroadcastWaitBrick(objName, message, script);
    } else if (brickNode.getNodeName().equals("moveNStepsBrick")) {
      Formula steps = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "steps"));
      return new MoveNStepsBrick(objName, steps);
    } else if (brickNode.getNodeName().equals("nextLookBrick")) {
      return new NextLookBrick(objName);
    } else if (brickNode.getNodeName().equals("repeatBrick")) {
      Formula timesToRepeat = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "timesToRepeat"));
      return new RepeatBrick(objName, timesToRepeat);
    } else if (brickNode.getNodeName().equals("foreverBrick")) {
      return new ForeverBrick(objName);
    } else if (brickNode.getNodeName().equals("loopEndBrick") || brickNode.getNodeName().equals("loopEndlessBrick")) {

      //TODO: check what to do with loopEndlessBrick
      LoopBeginBrick loopStartingBrick = script.getLastLoopBeginBrickWithoutLoopEndBrick();

      if (loopStartingBrick == null) {
        System.out.println("loopStartingBrick is null");
        return null;
      }
      LoopEndBrick loopEndBrick = new LoopEndBrick(objName, loopStartingBrick);
      loopStartingBrick.setLoopEndBrick(loopEndBrick);
      return loopEndBrick;
    } else if (brickNode.getNodeName().equals("noteBrick")) {

      Element noteNode = getChildElementByTagName(brickNode, "note");

      String note = getText(noteNode);

      if (note == null){
        return new NoteBrick(objName);
      } else{
        return new NoteBrick(objName, note);
      }
    } else if (brickNode.getNodeName().equals("setGhostEffectBrick")) {
      Formula ghostEffectValue = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "transparency"));
      return new SetGhostEffectBrick(objName, ghostEffectValue);
    } else if (brickNode.getNodeName().equals("changeGhostEffectByNBrick")) {
      Formula changeGhostEffect = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "changeGhostEffect"));
      return new ChangeGhostEffectByBrick(objName, changeGhostEffect);
    } else if (brickNode.getNodeName().equals("ifOnEdgeBounceBrick")) {
      return new IfOnEdgeBounceBrick(objName);
    } else if (brickNode.getNodeName().equals("pointToBrick")) {

      Element pointedSpriteNode = getChildElementByTagName(brickNode, "pointedObject");
      Element pointedSpriteNameNode = null;
      String pointedSpriteName = null;
      if (pointedSpriteNode.hasAttribute("reference")) {
        String pointedSpriteReference = pointedSpriteNode.getAttribute("reference");
        Element referencedSpriteNode =
            XPath.evaluateSingle(pointedSpriteNode, pointedSpriteReference, Element.class);
        pointedSpriteNameNode = getChildElementByTagName(referencedSpriteNode, "name");
      } else {
        pointedSpriteNameNode = getChildElementByTagName(pointedSpriteNode, "name");
      }

      if (pointedSpriteNameNode != null) {
        pointedSpriteName = getText(pointedSpriteNameNode);
      }
      CatrobatDebug.debug("Sprite " + objName + " has PointToBrick which points to Sprite "
          + pointedSpriteName);

      return new PointToBrick(objName, pointedSpriteName);
    } else if (brickNode.getNodeName().equals("clearGraphicEffectBrick")) {
      return new ClearGraphicEffectBrick(objName);
    } else if (brickNode.getNodeName().equals("setBrightnessBrick")) {
      Formula brightness = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "brightness"));
      return new SetBrightnessBrick(objName, brightness);
    } else if (brickNode.getNodeName().equals("changeBrightnessByNBrick")) {
      Formula changeBrightness = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "changeBrightness"));
      return new ChangeBrightnessBrick(objName, changeBrightness);
    } else if(brickNode.getNodeName().equals("setVariableBrick")){
      UserVariable userVar  = parseUserVariable(brickNode);     
      Formula formula = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "variableFormula"));
      return new SetVariableBrick(objName,formula, userVar);
    } else if(brickNode.getNodeName().equals("changeVariableBrick")){
      UserVariable userVar  = parseUserVariable(brickNode);
      Formula formula = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "variableFormula"));
      return new ChangeVariableBrick(objName,formula, userVar);
    } else if(brickNode.getNodeName().equals("ifLogicBeginBrick")){
      Formula formula;
      if(brickNode.hasAttribute("reference")){
        String r = checkReference(brickNode.getAttribute("reference"), "ifLogicBeginBrick");
        Element n = XPath.evaluateSingle(brickNode, r, Element.class);
        //System.out.println(n);
        //return new SequenceBrick(objName);
        formula = FormulaParser.parseFormula(getChildElementByTagName(n, "ifCondition"));
      }
      else{
        formula = FormulaParser.parseFormula(getChildElementByTagName(brickNode, "ifCondition"));
      }
      return new IfLogicBrick(objName, formula);
    } else if(brickNode.getNodeName().equals("ifLogicElseBrick")){
      return new IfLogicElseBrick(objName);
    } else if(brickNode.getNodeName().equals("ifLogicEndBrick")){
      return new IfLogicEndBrick(objName);
    }
    else {
      if(ignoreUnimplementedBricks){
        return new SequenceBrick(objName);
      } 
      CatrobatDebug.warn("Brick: " + brickNode.getNodeName() + " not implemented");
      Stage.getInstance().log("Brick not implemented:" + brickNode.getNodeName());
    }
    return null;
  }
  
  private Script checkScript(Element scriptElement, Sprite object) {

    Element objectElement = getChildElementByTagName(scriptElement, "object");
    String objectReference =
        objectElement != null ? objectElement.getAttribute("reference") : null;
    Element referencedObject =
        (objectElement != null && objectReference != null) ? XPath.evaluateSingle(
            objectElement, objectReference, Element.class) : null;

    Sprite scriptObject = object;

    if (referencedObject != null && referencedObject.getNodeName().equals("object")) {
      String name = getText(getChildElementByTagName(referencedObject, "name"));
      if (name == null || name.trim().length() == 0) {
        Stage.getInstance().log(
            "Invalid sprite reference: " + referencedObject.getNodeName() + " for " + objectReference);
      } else if (!object.getName().equals(name)) {
        scriptObject = manager.getSprite(name, true);
      }
    } else {
      //TODO: check what to do when object reference is null!
    }
    if (scriptElement.getNodeName().equals("startScript")) {
      return new StartScript(scriptObject, "startScript");
    } else if (scriptElement.getNodeName().equals("whenScript")) {
      return new WhenScript(scriptObject, "whenScript");
    } else if (scriptElement.getNodeName().equals("broadcastScript")) {
      String message = getText(getChildElementByTagName(scriptElement, "receivedMessage"));
      return new BroadcastScript(scriptObject, "broadcastScript", message);
    } else {
      Stage.getInstance().log("Script not implemented:" + scriptElement.getNodeName());
      return null;
    }
  }

  private void setRootCanvasSize(int width, int height) {
    Scene.get().setSceneMeasures(width, height);
  }

  public UserVariable parseUserVariable(Node tree){
    Element userVariable = getChildElementByTagName(tree,"userVariable");
    if(userVariable == null){
      return null;
    }
    Element nameEl = getChildElementByTagName(userVariable,"name");
    if(nameEl != null){
      String name = nameEl.getFirstChild().toString();
      return Stage.getInstance().getUserVariables().getUserVariable(name, null);
    }

    if (userVariable.hasAttribute("reference")) {
      String objectReference = checkReference(userVariable.getAttribute("reference"), "userVariable");
      userVariable = XPath.evaluateSingle(userVariable, objectReference, Element.class);
      if(userVariable == null){
        return null;
      }
      nameEl = getChildElementByTagName(userVariable,"name");
      if(nameEl != null)
      {
        String name = nameEl.getFirstChild().toString();
        return Stage.getInstance().getUserVariables().getUserVariable(name, null);
      }
    }
    return null;
  }
  public boolean isParsingComplete() {
    return parsingComplete;
  }

  private void parserFinished() {
    parsingComplete = true;
  }
}
