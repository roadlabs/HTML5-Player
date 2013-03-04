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
package org.catrobat.html5player.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Html5Player implements EntryPoint {

	private FlowPanel mainPanel = new FlowPanel();
	private Button playButton = new Button("Play");
	private Button showLogButton = new Button("ShowLogBox");
	private TextArea logBox = new TextArea();
	private VerticalPanel screenPanel = new VerticalPanel();

	private Canvas rootCanvas;
	
	private ServerConnectionCalls server;
	private ListBox projectListBox = new ListBox();
	
	//##########################################################################

	public void onModuleLoad() {

		CatrobatDebug.off();
		String projectPath = Window.Location.getParameter("projectpath");
		if(projectPath != null && !projectPath.equals(""))
		{
			//Window.alert(projectPath);
			//return;
			Const.PROJECT_PATH = projectPath;
		}
		
		String projectNumber = Window.Location.getParameter("projectnumber");
		if(projectNumber == null)
		{
			mainPanel.add(playButton);
			playButton.ensureDebugId("playButton");
	
			mainPanel.add(projectListBox);
			playButton.ensureDebugId("projectListBox");
	
			mainPanel.add(showLogButton);
			showLogButton.ensureDebugId("showLogBox");
	
			mainPanel.add(screenPanel);
			screenPanel.add(logBox);
		}
		else
		{
			mainPanel.add(screenPanel);
		}

		
		if(Scene.get().createScene() == false) {
			//TODO exception  if canvas not supported?
			CatrobatDebug.console("Canvas not supported");
			return;
		}
		
		rootCanvas = Scene.get().getCanvas();
		screenPanel.add(rootCanvas);
		rootCanvas.ensureDebugId("rootCanvas");
	
		populateProjectsListBox();

		RootPanel.get("firstWindow").add(mainPanel);

		final Stage stage = Stage.getInstance();
		stage.setCanvas(rootCanvas);
		stage.setLogBox(logBox);
		
		stage.defaultLogBoxSettings();
		
		server = new ServerConnectionCalls();

		//handle click on the play-button
		//
		playButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				int selectedIndex = projectListBox.getSelectedIndex();
				String projectName = projectListBox.getItemText(selectedIndex);
				String projectNumber = projectListBox.getValue(selectedIndex);
				
				CatrobatDebug.on();
				CatrobatDebug.console("Play button was clicked, project: " + projectNumber + " is selected");
					
				stage.clearStage();
				
				stage.displayLoadingImage();
				
				stage.setProjectNumber(projectNumber);
				
				//get xml-projectfile from server
				server.getXML(projectName, projectNumber);
			}
		});

		//handle click on the log-button
		//
		showLogButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				logBox.setVisible(!logBox.isVisible());
			}
		});

		//handle click on the canvas
		//
		rootCanvas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				stage.getSpriteManager().handleScreenClick(
						event.getRelativeX(rootCanvas.getCanvasElement()),
						event.getRelativeY(rootCanvas.getCanvasElement())); 
			}
		});
		
		if(projectNumber != null)
		{
			CatrobatDebug.on();
			CatrobatDebug.console("Play button was clicked, project: " + projectNumber + " is selected");
				
			stage.clearStage();
			
			stage.displayLoadingImage();
			
			stage.setProjectNumber(projectNumber);
			// TODO: remove project name
			String projectName = ""; 
			server.getXML(projectName, projectNumber);
		}
	}

	//##########################################################################
	
	/**
	 * 
	 */
	public void populateProjectsListBox() {
		
//		projectListBox.addItem("Asteroids 2 - 316", "316");
		
		projectListBox.addItem("fruit catcher 2 - 297", "297");
		
		projectListBox.addItem("Fishy - 515", "515");
		
		projectListBox.addItem("Pacman - 579", "579");
		projectListBox.addItem("Aquarium3 - 581", "581");
		
		projectListBox.addItem("Hannah Montana - 299", "299");
		projectListBox.addItem("Nintendo Hunter - 305", "305");
		projectListBox.addItem("ShufflingGoose - 317", "317");
		projectListBox.addItem("Rainbow lama - 311", "311");
		
		projectListBox.addItem("batmobile v1.1 - 313", "313");
		projectListBox.addItem("batmobile - 309", "309");
		
		projectListBox.addItem("Bouncing Balls - 339", "339");
		projectListBox.addItem("Cartoon Dance - 363", "363");
		projectListBox.addItem("Gear - 332", "332");
		projectListBox.addItem("Wood saw Cutter - 328", "328");
		
		projectListBox.addItem("mole with sounds - 357", "357");
		projectListBox.addItem("Bubble shoot - 327", "327");
		
		projectListBox.addItem("neu - 558", "558");
		projectListBox.addItem("Ballontest - 547", "547");
		
		
		projectListBox.addItem("Nyan Cat - 298", "298");
		projectListBox.addItem("POC Moorhuhn - 296", "296");
		
		projectListBox.addItem("Puppet- 333", "333");
		
		projectListBox.addItem("Darts - 315", "315");
		projectListBox.addItem("Skyrim Hunter - 314", "314");
		
		projectListBox.addItem("Asteroids - 320", "320");
		
		projectListBox.addItem("ShakingHeads - 323", "323");
		projectListBox.addItem("Tuned Simple Piano - 324", "324");
		
		projectListBox.addItem("eggdroid - 337", "337");
		projectListBox.addItem("Fruit Eater - 336", "336");
		
		projectListBox.addItem("Swat - 346", "346");
		projectListBox.addItem("Tester - 347", "347");
		
		projectListBox.addItem("maxb-other - 342", "342");
		projectListBox.addItem("cogs - 340", "340");
		
		projectListBox.addItem("Simple eyes - 352", "352");
		projectListBox.addItem("Lightningrohan13 - 368", "368");
		
		projectListBox.addItem("Basic Movement Program - 510", "510");
		projectListBox.addItem("moving baseball remix - 364", "364");
		
		projectListBox.addItem("passion13 - 367", "367");
		
		projectListBox.addItem("IQ Test - 398", "398");
		projectListBox.addItem("Biene Maja - 334", "334");
		projectListBox.addItem("sound test - 393", "393");
		projectListBox.addItem("cat go up and down - 409", "409");
		projectListBox.addItem("cat hide - 410", "410");
		projectListBox.addItem("insectcat - 411", "411");
		projectListBox.addItem("monster - 412", "412");
		projectListBox.addItem("simple project - 560", "560");
	}
}
