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
package org.catrobat.html5player.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Html5Player implements EntryPoint {

	private FlowPanel mainPanel = new FlowPanel();
	private Button playButton = new Button("Play");
	private Button showLogButton = new Button("ShowLogBox");
	private Button rotateLeftButton = new Button("rotateLeft");
	private Button rotateRightButton = new Button("rotateRight");
	private Button rePlayButton = new Button("RePlay");
	private Button zoomInButton = new Button("+");
	private Button zoomOutButton = new Button("-");
	private TextArea logBox = new TextArea();
	private VerticalPanel screenPanel = new VerticalPanel();
	private Label uploadLabel = new Label("Upload a project file:");

	private Canvas rootCanvas;

	private ServerConnectionCalls server;
	//private ListBox projectListBox = new ListBox();
	private static int rotationAngle = 0;
	Button uploadButton = new Button("Upload File");
	final FormPanel form = new FormPanel();

	//##########################################################################

	public void onModuleLoad() {
		mainPanel.add(rotateLeftButton);
		mainPanel.add(rotateRightButton);
		final String projectFileUrl = Window.Location.getParameter("projectfileurl");
		final String projectNumber = Window.Location.getParameter("projectnumber");
		if(projectFileUrl != null)
		{
			mainPanel.add(rePlayButton);
			mainPanel.add(zoomInButton);
			mainPanel.add(zoomOutButton);
			mainPanel.add(screenPanel);
		}
		else
		{



	//		String projectPath = Window.Location.getParameter("projectpath");
	//		if(projectPath != null && !projectPath.equals(""))
	//		{
	//			Const.PROJECT_PATH = projectPath;
	//		}



			if(projectNumber == null)
			{

	//			mainPanel.add(playButton);
	//			playButton.ensureDebugId("playButton");
	//
	//			mainPanel.add(projectListBox);
	//			playButton.ensureDebugId("projectListBox");
	//
	//			mainPanel.add(showLogButton);
	//			showLogButton.ensureDebugId("showLogBox");
	//
	//			mainPanel.add(screenPanel);
	//			screenPanel.add(logBox);
				mainPanel.add(rePlayButton);
				mainPanel.add(zoomInButton);
				mainPanel.add(zoomOutButton);
				VerticalPanel panel = new VerticalPanel();

			      //create a file upload widget
			      final FileUpload fileUpload = new FileUpload();
			      //create upload button

			      //pass action to the form to point to service handling file
			      //receiving operation.
			      form.setAction(GWT.getModuleBaseURL()+"fileupload");
			      // set form to use the POST method, and multipart MIME encoding.
			      form.setEncoding(FormPanel.ENCODING_MULTIPART);
			      form.setMethod(FormPanel.METHOD_POST);
			      fileUpload.setName("uploadFormElement");
			      panel.add(fileUpload);
			      //add a button to upload the file
			      panel.add(uploadButton);
			      uploadButton.addClickHandler(new ClickHandler() {
			          @Override
			          public void onClick(ClickEvent event) {
			             //get the filename to be uploaded
			             String filename = fileUpload.getFilename();
			             if (filename.length() == 0) {
			                Window.alert("No File Specified!");
			             } else {
			                form.submit();
			             }
			          }
			       });

			    form.add(panel);
			    mainPanel.add(uploadLabel);
				mainPanel.add(form);
				mainPanel.add(screenPanel);

			}
			else
			{
				mainPanel.add(rePlayButton);
				mainPanel.add(zoomInButton);
				mainPanel.add(zoomOutButton);
				mainPanel.add(screenPanel);
			}
		}


		if(Scene.get().createScene() == false) {
			//TODO exception  if canvas not supported?
			CatrobatDebug.error("Canvas not supported");
			return;
		}

		rootCanvas = Scene.get().getCanvas();
		screenPanel.add(rootCanvas);
		rootCanvas.ensureDebugId("rootCanvas");

		//populateProjectsListBox();

		RootPanel.get("firstWindow").add(mainPanel);

		final Stage stage = Stage.getInstance();
		stage.setCanvas(rootCanvas);
		stage.setLogBox(logBox);

		stage.defaultLogBoxSettings();

		server = new ServerConnectionCalls();

		playButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rotationAngle = 0;
				rotateDirection(0, screenPanel);

				CatrobatDebug.info("Play button was clicked, project: " + projectNumber + " is selected");

				stage.clearStage();

				stage.displayLoadingImage();

				stage.setProjectNumber(projectNumber);

				//get xml-projectfile from server
				server.getXML(projectNumber);
			}
		});

	       form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {

				//Window.alert(event.getResults());
				rotationAngle = 0;
				rotateDirection(0, screenPanel);
				//int selectedIndex = projectListBox.getSelectedIndex();
				//String projectNumber = projectListBox.getValue(selectedIndex);

				stage.clearStage();
				stage.displayLoadingImage();
				stage.setProjectNumber(projectNumber);
				server.getXML();
			}
	       });


		//handle click on the log-button
		//
		showLogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				logBox.setVisible(!logBox.isVisible());
			}
		});

		//handle click on the rotateLeft-button
		//
		rotateLeftButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rotateLeft(screenPanel);
			}
		});

		//handle click on the rotateRight-button
		//
		rotateRightButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rotateRight(screenPanel);
			}
		});

		//handle click on the replay-button
		//
		rePlayButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				stage.clearStage();

				stage.displayLoadingImage();

				stage.setProjectNumber(projectNumber);
				if(projectNumber != null)
				{
					server.getXML(projectNumber);
				}
				else
				{
					server.getXML();
				}
			}
		});

		//handle click on the zoomIn-button
		//
		zoomInButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			double twice = 2;
			Scene.get().zoomScene(twice);
			}
		});

		//handle click on the zoomOut-button
		//
		zoomOutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

			double half = 0.5;
			Scene.get().zoomScene(half);
			}
		});

		//handle click on the canvas
		//
		rootCanvas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				stage.getSpriteManager().handleScreenClick(
				  getRelativeXforRotation(event.getRelativeX(rootCanvas.getCanvasElement()),
				                 event.getRelativeY(rootCanvas.getCanvasElement()), screenPanel),
				  getRelativeYforRotation(event.getRelativeX(rootCanvas.getCanvasElement()),
				    event.getRelativeY(rootCanvas.getCanvasElement()), screenPanel));

			}

		});

//		if(projectNumber != null)
//		{
//			CatrobatDebug.on();
//			stage.clearStage();
//
//			stage.displayLoadingImage();
//
//			stage.setProjectNumber(projectNumber);
//			server.getXML(projectNumber);
//		}

		if(projectFileUrl != null)
		{
			stage.clearStage();

			stage.displayLoadingImage();
			server.getXMLFromProjectFileUrl(projectFileUrl);
		}


	}

  public static int getRelativeXforRotation(int x, int y, Panel panel) {
    int width = panel.getOffsetWidth();
    if(width == 0)
        width = getNumberFromCssAttribut(panel.getElement().getStyle().getWidth());
    int height = panel.getOffsetHeight();
    if(height == 0)
        height = getNumberFromCssAttribut(panel.getElement().getStyle().getHeight());

    if (rotationAngle == 180 || rotationAngle == -180) {
      return width - x;
    } else if (rotationAngle == 90 || rotationAngle == -270) {
      return y;
    } else if (rotationAngle == -90 || rotationAngle == 270) {
      return width-y;
    }
    return x;
  }

  public static int getRelativeYforRotation(int x, int y, Panel panel) {
    int width = panel.getOffsetWidth();
    if(width == 0)
        width = getNumberFromCssAttribut(panel.getElement().getStyle().getWidth());
    int height = panel.getOffsetHeight();
    if(height == 0)
        height = getNumberFromCssAttribut(panel.getElement().getStyle().getHeight());

    if (rotationAngle == 180 || rotationAngle == -180) {
      return height-y;
    } else if (rotationAngle == 90 || rotationAngle == -270) {
      return height-x;
    } else if (rotationAngle == -90 || rotationAngle == 270) {
      return x;
    }
    return y;
  }

	public static void rotateRight(Panel panel)
	{
		rotateDirection(90, panel);
	}

	public static void rotateLeft(Panel panel)
	{
		rotateDirection(-90, panel);
	}

	private static void rotateDirection(int degree,Panel panel)
	{
		int width = panel.getOffsetWidth();
		if(width == 0)
			width = getNumberFromCssAttribut(panel.getElement().getStyle().getWidth());
		int height = panel.getOffsetHeight();
		if(height == 0)
			height = getNumberFromCssAttribut(panel.getElement().getStyle().getHeight());
		int translation = 0;
		rotationAngle =(rotationAngle + degree) % 360;
		if(Math.abs(rotationAngle) != 0 && Math.abs(rotationAngle) != 180)
		{
			if((degree > 0 ))
			{
				translation = (width/2) - (height/2);
			}
			else
			{
				translation = (height/2) -(width/2);
			}
			if(rotationAngle != degree)
			{
				translation = translation * -1;
			}
		}
		CatrobatDebug.debug("translation: " + translation + " rotationAngle: " + rotationAngle + " degree: " + degree);
		panel.getElement().getStyle().setProperty("transform", "rotate("+rotationAngle+"deg) translate("+translation+"px,"+translation+"px)");
		panel.getElement().getStyle().setProperty("WebkitTransform", "rotate("+rotationAngle+"deg) translate("+translation+"px,"+translation+"px)");
		panel.getElement().getStyle().setProperty("MsTransform", "rotate("+rotationAngle+"deg) translate("+translation+"px,"+translation+"px)");
		panel.getElement().getStyle().setProperty("OTransform", "rotate("+rotationAngle+"deg) translate("+translation+"px,"+translation+"px)");
		panel.getElement().getStyle().setProperty("MozTransform", "rotate("+rotationAngle+"deg) translate("+translation+"px,"+translation+"px)");
	}

	private static int getNumberFromCssAttribut(String attr)
	{
		if(attr != null && !attr.equals(""))
		{
			String numberPart = attr.trim().split("[a-z]")[0];
			return Integer.parseInt(numberPart);
		}
		return 0;
	}

	public static int getRotationAngle()
	{
		return rotationAngle;
	}

}
