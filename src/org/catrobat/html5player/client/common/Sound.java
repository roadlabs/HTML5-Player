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
package org.catrobat.html5player.client.common;

import org.catrobat.html5player.client.CatrobatDebug;
//import org.catrobat.html5player.client.Const;
//import org.catrobat.html5player.client.ServerConnectionCalls;
//import org.catrobat.html5player.client.Stage;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Window;

public class Sound {
	//private ServerConnectionCalls server = new ServerConnectionCalls();
	private SoundInfo soundInfo;

	private Audio audio;

	public SoundInfo getSoundInfo() {
		return soundInfo;
	}

	public void setSoundInfo(SoundInfo soundInfo) {
		this.soundInfo = soundInfo;
		updateAudio();
	}

	public static String getSoundBasePath() {
		//return Const.PROJECT_PATH + Stage.getInstance().getProjectNumber() + "/sounds/";
		return "http://"+Window.Location.getHost()+ "/Html5Player/fileupload?name=";
	}

	public Audio getAudio() {
		return audio;
	}

	/**
	 * 
	 * audio.addSource(String):
	 * Only use this method if you do not know the type of the source file,
	 * as the browser cannot determine the format from the filename and must
	 * download each source until a compatible one is found. Instead, you should 
	 * specify the type for the media using addSource(String, String) so the browser
	 * can choose a source file without downloading the file. 
	 */
	private void updateAudio() {
		if (soundInfo == null) {
			this.audio = null;
		} else {
			
			audio = Audio.createIfSupported();
			
			if(audio != null) {
				audio.addSource(getSoundBasePath() + soundInfo.getFileName());
				audio.load();
				//server.getSound(soundInfo.getFileName(), audio);
				CatrobatDebug.on();
				CatrobatDebug.console("updateAudio - getError: " + audio.getError() + "...");
				CatrobatDebug.off();
			}
			
		}
	}

}
