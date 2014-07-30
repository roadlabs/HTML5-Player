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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.common.LookData;
import org.catrobat.html5player.client.common.Sound;
import org.catrobat.html5player.client.common.SoundInfo;
import org.catrobat.html5player.client.scripts.BroadcastScript;
import org.catrobat.html5player.client.scripts.Script;
import org.catrobat.html5player.client.scripts.StartScript;
import org.catrobat.html5player.client.scripts.WhenScript;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;
import org.catrobat.html5player.client.threading.WaitCount;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Image;

public class Sprite {

	private final String name;
	private boolean isBackground = false;

	private double volume;

	private Look look;
	private Image currentLook = null;

	private Map<String, Look> looks;
	private Map<String, Sound> sounds;
	private Set<Script> scripts;

	public Sprite(String name) {
		this.name = name;
		this.isBackground = false;
		this.volume = 70;

		this.looks = new LinkedHashMap<String, Look>();
		this.sounds = new LinkedHashMap<String, Sound>();
		this.scripts = new LinkedHashSet<Script>();

		look = new Look(Stage.getInstance().getStageMiddleX(), Stage.getInstance().getStageMiddleY(), true);
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

	public Look getLook() {
		return look;
	}

	public void addLookData(LookData lookData) {
		if (lookData != null && !looks.containsKey(lookData.getName())) {
			Look look = new Look(0.0f, 0.0f, true);
			look.setLookData(lookData);

			looks.put(lookData.getName(), look);
		}

	}

	public Look getLook(String name) {
		return looks.get(name);
	}

	public List<Look> getLooks() {
		List<Look> looks = new ArrayList<Look>();
		looks.addAll(this.looks.values());
		return looks;
	}

	/**
	 *
	 */
	public ArrayList<String> getLookDataNames() {
		return new ArrayList<String>(looks.keySet());
	}

	/**
	 *
	 */
	public ArrayList<LookData> getLookData() {
		ArrayList <Look> looksList = new ArrayList<Look>(looks.values());
		ArrayList<LookData> lookDataList = new ArrayList<LookData>();

		for(Look look : looksList) {
			LookData lookData = look.getLookData();
			lookDataList.add(lookData);
		}

		return lookDataList;
	}

	/**
	 *
	 */
	public LookData getLookDataByName(String lookName) {

		if(looks.containsKey(lookName)) {
			return looks.get(lookName).getLookData();
		}

		return null;
	}

	public void addScript(Script script) {
		if (script != null && !scripts.contains(script)) {
			scripts.add(script);
		}
	}

	// unused, only in sprite test
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


	   public int getScriptIndex(Script script){
	        ArrayList<Script> scriptsList = new ArrayList<Script>(scripts);
	        int index = 0;
	        for(Script s : scriptsList)
	        {
	          if(s.getId() == script.getId())
	          {
	            return index;
	          }
	          index++;
	        }
	        return -1;
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
	public void showLook() {
		if (look == null)
		{
			CatrobatDebug.info("Look NULLLL");
			return;
		}

		LookData lookData = look.getLookData();

		if(lookData == null)
		{
			CatrobatDebug.info("LookDATA NULLLL: ");
			return;
		}

		currentLook = ImageHandler.get().getImage(lookData.getFilename());

		if(currentLook == null)
			return;

		lookData.setWidth(currentLook.getWidth());
		lookData.setHeight(currentLook.getHeight());
	}

	public void drawSprite() {

		if(currentLook == null || !look.isVisible()) {
			CatrobatDebug.info("current look = " + currentLook + "| visible = " + look.isVisible());
			return;
		}

		long start = System.currentTimeMillis();


		CatrobatDebug.debug("drawSprite: " + this.name + " - customename: " + look.getLookData().getFilename());

		LookData lookData = look.getLookData();
		double size = look.getSize();
		double width = (double)lookData.getWidth() * size;
		double height = (double)lookData.getHeight() * size;


		Scene.get().drawImageJSNI(currentLook,
				look.getXPosition(),
				look.getYPosition(),
				-width / 2,
				-height / 2,
				width,
				height,
				-look.getRotation(), // the MINUS is important because canvas positive rotation is clockwise
				look.getAlphaValue());

		CatrobatDebug.debug("drawSprite-execution took " + (System.currentTimeMillis() - start + " ms"));
		CatrobatDebug.debug("z-Pos: " + look.getZPosition() + " : name: " + this.name);
	}

	public void run() {

		CatrobatDebug.debug("Sprite: " + this.name + ".run() - add startscripts");
		CatrobatDebug.debug("Number of scripts: " + this.getNumberOfScripts());

		List<Script> scriptList = new ArrayList<Script>();
		scriptList.addAll(scripts);
		Collections.sort(scriptList);
		Stage.getInstance().setCurrentSprite(this);


		for (Script script : scriptList) {

			//only add StartScripts to the scheduler
			if(script instanceof StartScript) {

//				CatrobatDebug.debug("script is no WhenScript or BroadcastScript, Sprite: " + this.name);

				CatThread thread = new CatThread(this.getName() + script.getName(), script);
				CatScheduler.get().schedule(thread);

			}
			else if (script instanceof WhenScript) {

				//CatrobatDebug.debug("script '" + script.getName() + "' is a WhenScript or a BroadcastScript, Sprite: " + this.name);
			}

//			script.run();
		}


	}

	public void startTapScripts() {

		CatrobatDebug.debug("<<< Sprite: " + this.name + " startTapScripts() >>>");
		Stage.getInstance().setCurrentSprite(this);

		for (Script script : scripts) {
			if (script.getType().equals(WhenScript.SCRIPT_TYPE)) {
				WhenScript touchScript = (WhenScript) script;

				if(CatScheduler.get().getThread(script.getExecutor()) != null) {
					CatrobatDebug.debug("Already existing Thread with WhenScript: " + script.getName());
					continue;
				}
				else {
					CatrobatDebug.info("Add WhenScript to Scheduler");
				}

				if(touchScript.hasScriptFinished()) {
					touchScript.resetWhenScript();
				}

				CatThread thread = new CatThread(this.getName() + script.getName(), touchScript);

				CatScheduler.get().schedule(thread);

				CatrobatDebug.info("script added to scheduler");
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

		//15.08.12: added || !look.isVisible())  --Andi

		//28.08.12 removed isBackground ||  --Andi
//		if (isBackground || currentLook == null || !look.isVisible())
//			return false;

		if (currentLook == null || !look.isVisible())
			return false;

		double xPosition = look.getXPosition();
		double yPosition = look.getYPosition();
		double size = look.getSize();

		LookData lookData = look.getLookData();

		double widthHalf = ((double)lookData.getWidth() * size) / 2;
		double heightHalf = ((double)lookData.getHeight() * size) / 2;

		if( xPosition + widthHalf  > relativeX &&
			xPosition - widthHalf  < relativeX &&
		    yPosition + heightHalf > relativeY &&
		    yPosition - heightHalf < relativeY) {

			CatrobatDebug.info("Sprite " + this.name + " got touched");

			return true;
		}


//		double relXTrans = relativeX - Stage.getInstance().getStageMiddleX();
//		double relYTrans = relativeY - Stage.getInstance().getStageMiddleY();
//
//		double costumeWidthHalf = currentLook.getWidth()*look.getSize() / 2;
//		double costumeHeightHalf = currentLook.getHeight()*look.getSize() / 2;
//
//		double leftXBorder = look.getMiddleX() - costumeWidthHalf;
//		double rightXBorder = look.getMiddleX() + costumeWidthHalf;
//
//		double topYBorder = look.getMiddleY() + costumeHeightHalf;
//		double bottomYBorder = look.getMiddleY() - costumeHeightHalf;
//
//		String debugMessage = "Click(" + relativeX + "," + relativeY + ")" +
//							  " - transform to (" + relXTrans + "," + relYTrans + ")";
//
//		debugMessage += "; Borders: left: " + leftXBorder + ", right: " + rightXBorder + ", top: " + topYBorder + ", bottom:" + bottomYBorder +
//					    " of sprite: " + this.name + " with middleX: " + look.getMiddleX() + " and middleY: " + look.getMiddleY();
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

			CatrobatDebug.debug("playSound - getError: " + audio.getError() + "...");
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
					CatrobatDebug.debug("JavaScriptException in stopSound of sprite: " + this.name);
					CatrobatDebug.debug("initialTime: " + audio.getInitialTime());
					CatrobatDebug.debug(e.getMessage());
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
