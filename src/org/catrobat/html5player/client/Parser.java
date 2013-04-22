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

import org.catrobat.html5player.client.bricks.Brick;
import org.catrobat.html5player.client.bricks.BroadcastBrick;
import org.catrobat.html5player.client.bricks.BroadcastWaitBrick;
import org.catrobat.html5player.client.bricks.ChangeBrightnessBrick;
import org.catrobat.html5player.client.bricks.ChangeGhostEffectByBrick;
import org.catrobat.html5player.client.bricks.ChangeSizeByNBrick;
import org.catrobat.html5player.client.bricks.ChangeVolumeByBrick;
import org.catrobat.html5player.client.bricks.ChangeXByBrick;
import org.catrobat.html5player.client.bricks.ChangeYByBrick;
import org.catrobat.html5player.client.bricks.ClearGraphicEffectBrick;
import org.catrobat.html5player.client.bricks.ComeToFrontBrick;
import org.catrobat.html5player.client.bricks.ForeverBrick;
import org.catrobat.html5player.client.bricks.GlideToBrick;
import org.catrobat.html5player.client.bricks.GoNStepsBackBrick;
import org.catrobat.html5player.client.bricks.HideBrick;
import org.catrobat.html5player.client.bricks.IfOnEdgeBounceBrick;
import org.catrobat.html5player.client.bricks.LoopBeginBrick;
import org.catrobat.html5player.client.bricks.LoopEndBrick;
import org.catrobat.html5player.client.bricks.MoveNStepsBrick;
import org.catrobat.html5player.client.bricks.NoteBrick;
import org.catrobat.html5player.client.bricks.PlaceAtBrick;
import org.catrobat.html5player.client.bricks.PlaySoundBrick;
import org.catrobat.html5player.client.bricks.PointInDirectionBrick;
import org.catrobat.html5player.client.bricks.PointToBrick;
import org.catrobat.html5player.client.bricks.RepeatBrick;
import org.catrobat.html5player.client.bricks.SetBrightnessBrick;
import org.catrobat.html5player.client.bricks.SetGhostEffectBrick;
import org.catrobat.html5player.client.bricks.SetLookBrick;
import org.catrobat.html5player.client.bricks.NextLookBrick;
import org.catrobat.html5player.client.bricks.SetSizeToBrick;
import org.catrobat.html5player.client.bricks.SetVolumeToBrick;
import org.catrobat.html5player.client.bricks.SetXBrick;
import org.catrobat.html5player.client.bricks.SetYBrick;
import org.catrobat.html5player.client.bricks.ShowBrick;
import org.catrobat.html5player.client.bricks.StopAllSoundsBrick;
import org.catrobat.html5player.client.bricks.TurnLeftBrick;
import org.catrobat.html5player.client.bricks.TurnRightBrick;
import org.catrobat.html5player.client.bricks.WaitBrick;

import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.common.SoundInfo;

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

      if (parseAndCreateObjects(messageDom)) {
        parserFinished();
        CatrobatDebug.console("Parser finished");
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
    NodeList versionNode =
        ((Element) messageDom.getFirstChild()).getElementsByTagName("catroidVersionName");
    if (versionNode != null && versionNode.getLength() != 0) {
      Element versionElement = (Element) versionNode.item(0);
      if (versionElement != null) {
        if (versionElement.toString().contains("0.6.0beta")) {
          return true;
        }
      }
    }
    Window.alert("Project version not supported!");
    return false;
  }

  private Element getChildElementByTagName(Node context, String name) {
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
      CatrobatDebug.console("reference (" + reference + ") cointains square brackets");
      return reference;
    } else {
      CatrobatDebug.console("reference (" + reference + ") does not cointain square brackets");

      int offset = reference.lastIndexOf(substring) + substring.length();

      StringBuilder newReference = new StringBuilder(reference);
      newReference.insert(offset, "[1]");

      checkedReference = newReference.toString();
    }

    return checkedReference;
  }

  // ##########################################################################

  private void parseScreenResolution(Document messageDom) {
    Node nodeScreenHeight =
        getChildElementByTagName(messageDom.getDocumentElement(), Const.SCREEN_HEIGHT);
    Node nodeScreenWidth =
        getChildElementByTagName(messageDom.getDocumentElement(), Const.SCREEN_WIDTH);

    int dimX = 0;
    int dimY = 0;

    if (nodeScreenWidth != null) {
      Node firstChild = nodeScreenWidth.getFirstChild();
      if (firstChild != null) {
        dimX = Integer.valueOf(firstChild.getNodeValue());
      } else {
        CatrobatDebug.console("firstChild is null");
      }
    } else {
      CatrobatDebug.console("Methode:parseScreenResolution nodeScreenWidth  is null");
    }

    if (nodeScreenHeight != null) {
      Node firstChild = nodeScreenHeight.getFirstChild();
      if (firstChild != null) {
        dimY = Integer.valueOf(firstChild.getNodeValue());
      } else {
        CatrobatDebug.console("firstChild is null");
      }
    } else {
      CatrobatDebug.console("Methode:parseScreenResolution  nodeScreenHeight  is null");
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

    if (name.equals(Const.BACK_GROUND_GER) || name.equals(Const.BACK_GROUND_ENG)) {
      object.setBackground(true);
    }
    List<Element> looks = getChildElementsByTagName(lookList, "look");
    if (looks == null || looks.isEmpty()) {
      System.out.println("look not found!");
      looks = getChildElementsByTagName(lookList, "look");
    }

    for (Element look : looks) {
      LookData lookData = new LookData();
      String filename = getText(getChildElementByTagName(look, "fileName"));

      CatrobatDebug.console("XXXXXXXXXXXXXXXXXXXXX fileName: " + filename
          + " XXXXXXXXXXXXXXXXXXXXX");

      lookData.setFilename(filename);
      String lookName = getText(getChildElementByTagName(look, "name"));
      lookData.setName(lookName);

      CatrobatDebug
          .console("XXXXXXXXXXXXXXXXXXXXX name: " + lookName + " XXXXXXXXXXXXXXXXXXXXX");

      lookData.setHeight(0);
      lookData.setWidth(0);

      object.addLookData(lookData);
      CatrobatDebug.console("LookData: " + lookData.getName());
      CatrobatDebug.console("File: " + lookData.getFilename() + " Height: " + lookData.getHeight()
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

      Element brickListElement = getChildElementByTagName(scriptElement, "brickList");

      if (brickListElement != null) {
        List<Element> brickElements = getChildElements(brickListElement);

        for (Element brickElement : brickElements) {
          CatrobatDebug.off();
          Brick brick = checkBrick(brickElement, object, script);
          CatrobatDebug.on();
          if (brick != null && script != null) {
            script.addBrick(brick);
          } else {
            return null;
          }
        }
      }

      object.addScript(script);
    }


    CatrobatDebug.off();

    // List<Element> sounds = getChildElementsByTagName(soundList,
    // "Common.SoundInfo");
    // for (Element soundInfoElement : sounds) {
    // SoundInfo soundInfo = new SoundInfo();
    // String id = getText(getChildElementByTagName(soundInfoElement,
    // "id"));
    // soundInfo.setId(id);
    // String title = getText(getChildElementByTagName(soundInfoElement,
    // "title"));
    // soundInfo.setTitle(title);
    // String filename = getText(getChildElementByTagName(soundInfoElement,
    // "fileName"));
    // soundInfo.setFileName(filename);
    // String paused = getText(getChildElementByTagName(soundInfoElement,
    // "isPaused"));
    // soundInfo.setPaused(Boolean.valueOf(paused));
    // String playing = getText(getChildElementByTagName(soundInfoElement,
    // "isPlaying"));
    // soundInfo.setPlaying(Boolean.valueOf(playing));
    // sprite.addSound(soundInfo);
    // CatrobatDebug.console("Sound Name: " + soundInfo.getFileName());
    //
    // }

    return object;
  }

  private Brick checkBrick(Element brickNode, Sprite object, Script script) {

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
      CatrobatDebug.console("INVALID - checkBrick - objectElement: " + referencedObject);
    }

    if (brickNode.getNodeName().equals("Bricks.SetCostumeBrick")) {

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

    } else if (brickNode.getNodeName().equals("Bricks.WaitBrick")) {
      String timeToWait = getText(getChildElementByTagName(brickNode, "timeToWaitInMilliSeconds"));
      int waitTime = Integer.valueOf(timeToWait);
      return new WaitBrick(objName, waitTime, script);
    } else if (brickNode.getNodeName().equals("Bricks.PlaySoundBrick")) {

      // CatrobatDebug.on();
      CatrobatDebug.console("PlaySoundBrick:");

      Element soundInfoElement = getChildElementByTagName(brickNode, "soundInfo");
      SoundInfo soundInfo = new SoundInfo();
      String soundId = null;
      String fileName = null;

      CatrobatDebug.console("soundInfo: " + soundInfoElement);

      if (soundInfoElement != null) {

        if (soundInfoElement.hasAttribute("reference")) {

          String soundInfoReference =
              checkReference(soundInfoElement.getAttribute("reference"), "PlaySoundBrick");
          Element referencedSoundInfoElement =
              XPath.evaluateSingle(soundInfoElement, soundInfoReference, Element.class);

          CatrobatDebug.console("PlaySoundBrick has a reference-attribute: " + soundInfoReference);
          CatrobatDebug.console("referenced soundInfo: " + referencedSoundInfoElement);

          if (referencedSoundInfoElement != null) {
            soundInfoElement = referencedSoundInfoElement;
            CatrobatDebug.console("soundInfoElement got set to the referenced soundInfo");
          } else {
            CatrobatDebug.console("referenced soundInfo is null");
            return null;
          }

        }

        fileName = getText(getChildElementByTagName(soundInfoElement, "fileName"));
        CatrobatDebug.console("Filename: " + fileName);

        soundInfo.setFileName(fileName);

        soundInfo.setTitle(getText(getChildElementByTagName(soundInfoElement, "name")));

        CatrobatDebug.console("Title: "
            + getText(getChildElementByTagName(soundInfoElement, "name")));

        soundId = fileName.split("_")[0];

        CatrobatDebug.console("Sound ID: " + soundId);

        soundInfo.setId(soundId);
      }

      if (soundId == null || soundId.trim().length() == 0) {
        System.out.println("sound is null");
      } else {
        object.addSound(soundInfo);
      }


      CatrobatDebug.off();

      return new PlaySoundBrick(objName, soundId);
    } else if (brickNode.getNodeName().equals("Bricks.ChangeVolumeByBrick")) {

      String volumeValue = getText(getChildElementByTagName(brickNode, "volume"));
      double volume = Double.parseDouble(volumeValue);
      return new ChangeVolumeByBrick(objName, volume);
    } else if (brickNode.getNodeName().equals("Bricks.SetVolumeToBrick")) {

      String volumeValue = getText(getChildElementByTagName(brickNode, "volume"));
      double volume = Double.parseDouble(volumeValue);
      return new SetVolumeToBrick(objName, volume);
    } else if (brickNode.getNodeName().equals("Bricks.PlaceAtBrick")) {
      String xValue = getText(getChildElementByTagName(brickNode, "xPosition"));
      int xPosition = Integer.parseInt(xValue);
      String yValue = getText(getChildElementByTagName(brickNode, "yPosition"));
      int yPosition = Integer.parseInt(yValue);
      return new PlaceAtBrick(objName, xPosition, yPosition);
    } else if (brickNode.getNodeName().equals("Bricks.ChangeSizeByNBrick")) {
      String sValue = getText(getChildElementByTagName(brickNode, "size"));
      double size = Double.parseDouble(sValue);
      return new ChangeSizeByNBrick(objName, size);
    } else if (brickNode.getNodeName().equals("Bricks.SetYBrick")) {
      String yValue = getText(getChildElementByTagName(brickNode, "yPosition"));
      int yPosition = Integer.parseInt(yValue);
      return new SetYBrick(objName, yPosition);
    } else if (brickNode.getNodeName().equals("Bricks.SetXBrick")) {
      String xValue = getText(getChildElementByTagName(brickNode, "xPosition"));
      int xPosition = Integer.parseInt(xValue);
      return new SetXBrick(objName, xPosition);
    } else if (brickNode.getNodeName().equals("Bricks.ChangeXByBrick")) {

      String deltaValue = getText(getChildElementByTagName(brickNode, "xMovement"));
      int deltaX = Integer.parseInt(deltaValue);
      return new ChangeXByBrick(objName, deltaX);

    } else if (brickNode.getNodeName().equals("Bricks.ChangeYByBrick")) {

      String deltaValue = getText(getChildElementByTagName(brickNode, "yMovement"));
      int deltaY = Integer.parseInt(deltaValue);
      return new ChangeYByBrick(objName, deltaY);

    } else if (brickNode.getNodeName().equals("Bricks.HideBrick")) {
      return new HideBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.ShowBrick")) {
      return new ShowBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.StopAllSoundsBrick")) {
      return new StopAllSoundsBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.TurnLeftBrick")) {

      String degreeValue = getText(getChildElementByTagName(brickNode, "degrees"));
      int degrees = new Double(degreeValue).intValue();
      return new TurnLeftBrick(objName, degrees);
    } else if (brickNode.getNodeName().equals("Bricks.TurnRightBrick")) {

      String degreeValue = getText(getChildElementByTagName(brickNode, "degrees"));
      int degrees = new Double(degreeValue).intValue();
      return new TurnRightBrick(objName, degrees);
    } else if (brickNode.getNodeName().equals("Bricks.PointInDirectionBrick")) {

      String directionValue = getText(getChildElementByTagName(brickNode, "degrees"));
      double direction = Double.parseDouble(directionValue);

      return new PointInDirectionBrick(objName, 0, direction);
    } else if (brickNode.getNodeName().equals("Bricks.GoNStepsBackBrick")) {

      String stepsValue = getText(getChildElementByTagName(brickNode, "steps"));
      int steps = Integer.parseInt(stepsValue);
      return new GoNStepsBackBrick(objName, steps);
    } else if (brickNode.getNodeName().equals("Bricks.ComeToFrontBrick")) {
      return new ComeToFrontBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.GlideToBrick")) {
      String durationValue = getText(getChildElementByTagName(brickNode, "durationInMilliSeconds"));
      int duration = Integer.parseInt(durationValue);
      String xValue = getText(getChildElementByTagName(brickNode, "xDestination"));
      int xDestination = Integer.parseInt(xValue);
      String yValue = getText(getChildElementByTagName(brickNode, "yDestination"));
      int yDestination = Integer.parseInt(yValue);
      return new GlideToBrick(objName, duration, xDestination, yDestination, script);
    } else if (brickNode.getNodeName().equals("Bricks.SetSizeToBrick")) {
      String sizeValue = getText(getChildElementByTagName(brickNode, "size"));
      double size = Double.parseDouble(sizeValue);
      return new SetSizeToBrick(objName, (float) size);
    } else if (brickNode.getNodeName().equals("Bricks.BroadcastBrick")) {
      String message = getText(getChildElementByTagName(brickNode, "broadcastMessage"));
      return new BroadcastBrick(objName, message);
    } else if (brickNode.getNodeName().equals("Bricks.BroadcastWaitBrick")) {
      String message = getText(getChildElementByTagName(brickNode, "broadcastMessage"));
      return new BroadcastWaitBrick(objName, message, script);
    } else if (brickNode.getNodeName().equals("Bricks.MoveNStepsBrick")) {
      String stepsValue = getText(getChildElementByTagName(brickNode, "steps"));
      double steps = Double.parseDouble(stepsValue);
      return new MoveNStepsBrick(objName, steps);
    } else if (brickNode.getNodeName().equals("Bricks.NextCostumeBrick")) {

      return new NextLookBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.RepeatBrick")) {

      Element timesToRepeatNode = getChildElementByTagName(brickNode, "timesToRepeat");

      int timesToRepeat = Integer.parseInt(getText(timesToRepeatNode));

      return new RepeatBrick(objName, timesToRepeat);

    } else if (brickNode.getNodeName().equals("Bricks.ForeverBrick")) {

      return new ForeverBrick(objName);

    } else if (brickNode.getNodeName().equals("Bricks.LoopEndBrick")) {

      LoopBeginBrick loopStartingBrick = script.getLastLoopBeginBrickWithoutLoopEndBrick();

      if (loopStartingBrick == null) {
        System.out.println("loopStartingBrick is null");
        return null;
      }

      LoopEndBrick loopEndBrick = new LoopEndBrick(objName, loopStartingBrick);

      loopStartingBrick.setLoopEndBrick(loopEndBrick);

      return loopEndBrick;
    } else if (brickNode.getNodeName().equals("Bricks.NoteBrick")) {

      Element noteNode = getChildElementByTagName(brickNode, "note");

      String note = getText(noteNode);

      if (note == null)
        return new NoteBrick(objName);
      else
        return new NoteBrick(objName, note);
    } else if (brickNode.getNodeName().equals("Bricks.SetGhostEffectBrick")) {
      Element transparencyNode = getChildElementByTagName(brickNode, "transparency");
      String transparency = getText(transparencyNode);

      double ghostEffectValue = Double.parseDouble(transparency);
      return new SetGhostEffectBrick(objName, ghostEffectValue);
    } else if (brickNode.getNodeName().equals("Bricks.ChangeGhostEffectBrick")) {
      Element changeGhostEffectNode = getChildElementByTagName(brickNode, "changeGhostEffect");
      String changeGhostEffect = getText(changeGhostEffectNode);

      double changeGhostEffectValue = Double.parseDouble(changeGhostEffect);
      return new ChangeGhostEffectByBrick(objName, changeGhostEffectValue);
    } else if (brickNode.getNodeName().equals("Bricks.IfOnEdgeBounceBrick")) {
      return new IfOnEdgeBounceBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.PointToBrick")) {

      Element pointedSpriteNode = getChildElementByTagName(brickNode, "pointedSprite");
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

      CatrobatDebug.on();
      CatrobatDebug.console("Sprite " + objName + " has PointToBrick which points to Sprite "
          + pointedSpriteName);
      CatrobatDebug.off();

      return new PointToBrick(objName, pointedSpriteName);
    } else if (brickNode.getNodeName().equals("Bricks.ClearGraphicEffectBrick")) {
      return new ClearGraphicEffectBrick(objName);
    } else if (brickNode.getNodeName().equals("Bricks.SetBrightnessBrick")) {
      Element brightnessNode = getChildElementByTagName(brickNode, "brightness");
      String brightnessValueAsString = getText(brightnessNode);

      double brightnessValue = Double.parseDouble(brightnessValueAsString);

      return new SetBrightnessBrick(objName, brightnessValue);
    } else if (brickNode.getNodeName().equals("Bricks.ChangeBrightnessBrick")) {

      // TODO: tag-name for brightness value unknown
      Element brightnessNode = getChildElementByTagName(brickNode, "brightness");
      String brightnessValueAsString = getText(brightnessNode);

      double brightnessValue = Double.parseDouble(brightnessValueAsString);

      return new ChangeBrightnessBrick(objName, brightnessValue);
    } else {
      CatrobatDebug.console("Brick: " + brickNode.getNodeName() + " not implemented");
      Stage.getInstance().log("Brick not implemented:" + brickNode.getNodeName());
    }
    return null;
  }
  //TODO: check what to do when object reference is null!
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

    }
    if (scriptElement.getNodeName().equals("Content.StartScript")) {

      return new StartScript(scriptObject, "startScript");

    } else if (scriptElement.getNodeName().equals("Content.WhenScript")) {
      return new WhenScript(scriptObject, "whenScript");
    } else if (scriptElement.getNodeName().equals("Content.BroadcastScript")) {
      String message = getText(getChildElementByTagName(scriptElement, "receivedMessage"));
      return new BroadcastScript(scriptObject, "broadcastScript", message);
    } else {
      Stage.getInstance().log("Script not implemented:" + scriptElement.getNodeName());
      return null;
    }
  }

  // ##########################################################################

  private void setRootCanvasSize(int width, int height) {
    Scene.get().setSceneMeasures(width, height);
  }

  // ##########################################################################

  public boolean isParsingComplete() {
    return parsingComplete;
  }

  private void parserFinished() {
    parsingComplete = true;
  }
}
