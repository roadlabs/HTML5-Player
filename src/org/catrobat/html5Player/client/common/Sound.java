package org.catrobat.html5Player.client.common;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Const;
import org.catrobat.html5Player.client.Stage;

import com.google.gwt.media.client.Audio;

public class Sound {

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
		return Const.PROJECT_PATH + Stage.getInstance().getProjectNumber() + "/sounds/";
	}

	public Audio getAudio() {
		return audio;
	}

	/**
	 * TODO:
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
				
				CatrobatDebug.on();
				CatrobatDebug.console("updateAudio - getError: " + audio.getError() + "...");
				CatrobatDebug.off();
			}
			
		}
	}

}
