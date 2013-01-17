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
 package org.catrobat.html5Player.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.common.CostumeData;
import org.catrobat.html5Player.client.common.Sound;
import org.catrobat.html5Player.client.common.SoundInfo;
import org.catrobat.html5Player.client.scripts.BroadcastScript;
import org.catrobat.html5Player.client.scripts.Script;
import org.catrobat.html5Player.client.scripts.StartScript;
import org.catrobat.html5Player.client.scripts.WhenScript;
import org.catrobat.html5Player.client.threading.CatScheduler;
import org.catrobat.html5Player.client.threading.CatThread;
import org.catrobat.html5Player.client.threading.WaitCount;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Image;

public class Sprite {

	private final String name;
	private boolean isBackground = false;
	
	private double volume;

	private Costume costume;
	private Image currentCostume = null;

	private Map<String, Costume> costumes;
	private Map<String, Sound> sounds;
	private Set<Script> scripts;

	public Sprite(String name) {
		this.name = name;
		this.isBackground = false;
		this.volume = 70;

		this.costumes = new LinkedHashMap<String, Costume>();
		this.sounds = new LinkedHashMap<String, Sound>();
		this.scripts = new LinkedHashSet<Script>();

		costume = new Costume(Stage.getInstance().getStageMiddleX(), Stage.getInstance().getStageMiddleY(), true);
	}

	public String getName() {
		return this.name;
	}

	public boolean isBackground() {
		return isBackground;
	}

	public void setBackground(boolean background) {
		isBackground = background;
	}
	
	public Costume getCostume() {
		return costume;
	}

	public void addCostumeData(CostumeData costumeData) {
		if (costumeData != null && !costumes.containsKey(costumeData.getName())) {
			Costume costume = new Costume(0.0f, 0.0f, true);
			costume.setCostumeData(costumeData);
			costumes.put(costumeData.getName(), costume);
		}

	}

	public Costume getCostume(String name) {
		return costumes.get(name);
	}

	public List<Costume> getCostumes() {
		List<Costume> costumes = new ArrayList<Costume>();
		costumes.addAll(this.costumes.values());
		return costumes;
	}
	
	/**
	 * 
	 */
	public ArrayList<String> getCostumeDataNames() {
		return new ArrayList<String>(costumes.keySet());
	}
	
	/**
	 * 
	 */
	public ArrayList<CostumeData> getCostumeData() {
		ArrayList <Costume> costumesList = new ArrayList<Costume>(costumes.values());
		ArrayList<CostumeData> costumeDataList = new ArrayList<CostumeData>();
		
		for(Costume costume : costumesList) {
			CostumeData costumeData = costume.getCostumeData(); 
			costumeDataList.add(costumeData);
		}
		
		return costumeDataList;
	}
	
	/**
	 * 
	 */
	public CostumeData getCostumeDataByName(String costumeName) {
		
		if(costumes.containsKey(costumeName)) {
			return costumes.get(costumeName).getCostumeData();
		}
		
		return null;
	}

	public void addScript(Script script) {
		if (script != null && !scripts.contains(script)) {
			scripts.add(script);
		}
	}

	//TODO: unused, only in sprite test
	public Script getScript(String name) {
		for (Script script : scripts) {
			if (script.getName().equals(name)) {
				return script;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param position
	 * @return script
	 */
	public Script getScript(int position){
		ArrayList<Script> scriptsList = new ArrayList<Script>(scripts);
		
		if(position < getNumberOfScripts())
			return scriptsList.get(position);
		else
			return null;
	}

	public void addSound(SoundInfo soundInfo) {
		if (soundInfo != null && !sounds.containsKey(soundInfo.getId())) {
			Sound sound = new Sound();
			sound.setSoundInfo(soundInfo);
			this.sounds.put(soundInfo.getId(), sound);
		}
	}

	public Sound getSound(String id) {
		return sounds.get(id);
	}
	
	/**
	 * 
	 */
	public void showCostume() {
		if (costume == null)
			return;
		
		CostumeData costumeData = costume.getCostumeData();
		
		if(costumeData == null)
			return;
		
		currentCostume = ImageHandler.get().getImage(costumeData.getFilename());
		
		costumeData.setWidth(currentCostume.getWidth());
		costumeData.setHeight(currentCostume.getHeight());
	}

	public void drawSprite() {
		
		if(currentCostume == null || !costume.isVisible()) {
			return;
		}
		
		long start = System.currentTimeMillis();

//		CatrobatDebug.on();
		CatrobatDebug.console("drawSprite: " + this.name + " - customename: " + costume.getCostumeData().getFilename());
		CatrobatDebug.off();
		
		CostumeData costumeData = costume.getCostumeData();
		double size = costume.getSize();
		double width = (double)costumeData.getWidth() * size;
		double height = (double)costumeData.getHeight() * size;
				
//		if(costume.getBrightnessValue() == 1.0) {
//			Scene.get().drawImage(currentCostume, 
//					costume.getXPosition(), 
//					costume.getYPosition(), 
//					-costumeData.getWidth() / 2,
//					-costumeData.getHeight() / 2, 
//					costumeData.getWidth(), 
//					costumeData.getHeight(),
//					costume.getRotation(), 
//					costume.getSize(), costume.getSize(),
//					costume.getAlphaValue());
//		}
//		else { //TODO: call the draw function with a brightness parameter
//			Scene.get().drawImage(currentCostume, 
//					costume.getXPosition(), 
//					costume.getYPosition(), 
//					-costumeData.getWidth() / 2,
//					-costumeData.getHeight() / 2, 
//					costumeData.getWidth(), 
//					costumeData.getHeight(),
//					costume.getRotation(), 
//					costume.getSize(), costume.getSize(),
//					costume.getAlphaValue());
//		}
		
//		Scene.get().drawImage(currentCostume, 
//				costume.getXPosition(), 
//				costume.getYPosition(), 
//				-costumeData.getWidth() / 2,
//				-costumeData.getHeight() / 2, 
//				costumeData.getWidth(), 
//				costumeData.getHeight(),
//				costume.getRotation(), 
//				costume.getSize(), costume.getSize(),
//				costume.getAlphaValue());
		
		Scene.get().drawImageJSNI(currentCostume, 
				costume.getXPosition(), 
				costume.getYPosition(), 
				-width / 2,
				-height / 2,
				width, 
				height,
				-costume.getRotation(), // the MINUS is important because canvas positive rotation is clockwise
				costume.getAlphaValue());
		
		
//		CatrobatDebug.on();
		CatrobatDebug.console("drawSprite-execution needed " + (System.currentTimeMillis() - start) +
							  "ms : z-Pos: " + costume.getZPosition() +
							  " : name: " + this.name);
		CatrobatDebug.off();
		
	}

	public void run() {
		
//		CatrobatDebug.on();
		
		CatrobatDebug.console("Sprite: " + this.name + ".run() - add startscripts");
		
		CatrobatDebug.console("number of scripts: " + this.getNumberOfScripts());
		
		List<Script> scriptList = new ArrayList<Script>();
		scriptList.addAll(scripts);
		Collections.sort(scriptList);
		
		for (Script script : scriptList) {

			//only add StartScripts to the scheduler
			if(script instanceof StartScript) {

//				CatrobatDebug.console("script is no WhenScript or BroadcastScript, Sprite: " + this.name);
				
				CatThread thread = new CatThread(this.getName() + script.getName(), script);
				CatScheduler.get().schedule(thread);
			}
			else {
//				CatrobatDebug.console("script '" + script.getName() + "' is a WhenScript or a BroadcastScript, Sprite: " + this.name);
			}
			
//			script.run();
		}
		
		CatrobatDebug.off();
	}

	public void startTapScripts() {
		
		CatrobatDebug.console("<<< Sprite: " + this.name + " startTapScripts() >>>");
		
		for (Script script : scripts) {
			if (script.getType().equals(WhenScript.SCRIPT_TYPE)) {
				WhenScript touchScript = (WhenScript) script;
				
				if(CatScheduler.get().getThread(script.getExecutor()) != null) {
					CatrobatDebug.on();
					CatrobatDebug.console("Already existing Thread with WhenScript: " + script.getName());
					CatrobatDebug.off();
					continue;
				}
				else {
					CatrobatDebug.console("Add WhenScript to Scheduler");
				}
				
				if(touchScript.hasScriptFinished()) {
					touchScript.resetWhenScript();
				}
				
				CatThread thread = new CatThread(this.getName() + script.getName(), touchScript);
				
				CatScheduler.get().schedule(thread);
				
				CatrobatDebug.console("script added to scheduler");
			}
		}
	}
	
	public void startScriptBroadcast(BroadcastScript script) {
		CatThread thread = new CatThread(this.getName() + script.getName(), script);
		CatScheduler.get().schedule(thread);
	}
	
	public void startScriptBroadcastWait(BroadcastScript script, WaitCount signaler) {
		CatThread thread = new CatThread(this.getName() + script.getName(), script);
		
		thread.signalFinishedExecution(signaler);
		
		CatScheduler.get().schedule(thread);
	}
	
	/**
	 * 
	 * @return number of scripts
	 */
	public int getNumberOfScripts() {
		return scripts.size();
	}

	/**
	 * 
	 * @param relativeX
	 * @param relativeY
	 * @return
	 */
	public boolean processOnTouch(int relativeX, int relativeY) {
		
		//15.08.12: added || !costume.isVisible())  --Andi
		
		//28.08.12 removed isBackground ||  --Andi
//		if (isBackground || currentCostume == null || !costume.isVisible())
//			return false;
		
		if (currentCostume == null || !costume.isVisible())
			return false;
		
		double xPosition = costume.getXPosition();
		double yPosition = costume.getYPosition();
		double size = costume.getSize();
		
		CostumeData costumeData = costume.getCostumeData();
		
		double widthHalf = ((double)costumeData.getWidth() * size) / 2;
		double heightHalf = ((double)costumeData.getHeight() * size) / 2;
		
		if( xPosition + widthHalf  > relativeX &&
			xPosition - widthHalf  < relativeX &&
		    yPosition + heightHalf > relativeY &&
		    yPosition - heightHalf < relativeY) {
		
			CatrobatDebug.on();
			CatrobatDebug.console("sprite " + this.name + " got touched");
			CatrobatDebug.off();
			
			return true;
		}
		
		
//		double relXTrans = relativeX - Stage.getInstance().getStageMiddleX();
//		double relYTrans = relativeY - Stage.getInstance().getStageMiddleY();
//		
//		double costumeWidthHalf = currentCostume.getWidth()*costume.getSize() / 2;
//		double costumeHeightHalf = currentCostume.getHeight()*costume.getSize() / 2;
//		
//		double leftXBorder = costume.getMiddleX() - costumeWidthHalf;
//		double rightXBorder = costume.getMiddleX() + costumeWidthHalf;
//		
//		double topYBorder = costume.getMiddleY() + costumeHeightHalf;
//		double bottomYBorder = costume.getMiddleY() - costumeHeightHalf;
//		
//		String debugMessage = "Click(" + relativeX + "," + relativeY + ")" +
//							  " - transform to (" + relXTrans + "," + relYTrans + ")";
//		
//		debugMessage += "; Borders: left: " + leftXBorder + ", right: " + rightXBorder + ", top: " + topYBorder + ", bottom:" + bottomYBorder +
//					    " of sprite: " + this.name + " with middleX: " + costume.getMiddleX() + " and middleY: " + costume.getMiddleY();
//		
//		CatrobatDebug.on();
//		CatrobatDebug.console(debugMessage);
//		CatrobatDebug.off();
//		
//		if((relXTrans > leftXBorder && relXTrans < rightXBorder) &&
//			relYTrans > bottomYBorder && relYTrans < topYBorder) {
//
//			CatrobatDebug.on();
//			CatrobatDebug.console("sprite " + this.name + " got touched");
//			CatrobatDebug.off();
//			
//			return true;
//		}
		
		return false;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double newVolume) {
		this.volume = newVolume;
	}

	public void playSound(String id) {
		Sound sound = getSound(id);
		Audio audio = sound != null ? sound.getAudio() : null;
		if (audio != null) {
			audio.setVolume((double) volume / 100);
			audio.play();
			
			CatrobatDebug.on();
			CatrobatDebug.console("playSound - getError: " + audio.getError() + "...");
			CatrobatDebug.off();
		}
		else {
			Stage.getInstance().log("no sound with id: " + id);
		}
	}

	public void stopSound() {
		for (Sound sound : sounds.values()) {
			Audio audio = sound.getAudio();
			if (audio != null) {
				audio.pause();
				
				try {
					//reset
					double initialTime = audio.getInitialTime();
					audio.setCurrentTime(initialTime); 
				}
				catch(JavaScriptException e) {
					CatrobatDebug.on();
					CatrobatDebug.console("JavaScriptException in stopSound of sprite: " + this.name);
					CatrobatDebug.console("initialTime: " + audio.getInitialTime());
					CatrobatDebug.console(e.getMessage());
					CatrobatDebug.off();
				}
				
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprite other = (Sprite) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
