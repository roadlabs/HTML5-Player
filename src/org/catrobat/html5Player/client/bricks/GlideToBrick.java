/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.CatrobatDebug;
import org.catrobat.html5Player.client.Const;
import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.Stage;
import org.catrobat.html5Player.client.common.Costume;
import org.catrobat.html5Player.client.scripts.Script;

import com.google.gwt.user.client.Timer;

public class GlideToBrick extends Brick {

	private int xDestination;
	private int yDestination;
	
	private long startTime;
	private Timer glideTimer;

	// TODO: better solution for this easy quick hack
	private Script script;
	
	//TODO: not needed anymore
	private int currentStep; 
	
	private int durationInMilliSeconds;
	private int remainingDuration;

	public GlideToBrick(String spriteName, int duration, int newXDestination, int newYDestination, Script newScript) {
		super(spriteName);

		this.xDestination = newXDestination + Stage.getInstance().getStageMiddleX();
		this.yDestination = -newYDestination + Stage.getInstance().getStageMiddleY();
		
		this.script = newScript;
		
		this.currentStep = 0;
		this.durationInMilliSeconds = (int) duration;
		this.remainingDuration = 0;
	}
	
	public boolean execute(final Sprite sprite) {
		script.pause();
		
		startTime = System.currentTimeMillis();
		this.remainingDuration = durationInMilliSeconds;

		glideTimer = new Timer() {
			public void run() {
				
				CatrobatDebug.on();
				CatrobatDebug.console("GLIDETO: Timer run() - elapsed time = " + (System.currentTimeMillis() - startTime) + "ms");
				CatrobatDebug.off();
				
				currentStep++;
				
				long currentTime = System.currentTimeMillis();
				
				int timePassed = (int)(currentTime - startTime);
				remainingDuration -= timePassed;
				
				updatePosition(sprite, timePassed, remainingDuration);
				checkCancle(sprite);
				
				startTime = currentTime;
			}
		};

		glideTimer.scheduleRepeating(Const.GLIDE_UPDATE_RATE);
		return true;
	}

	
	
	private void checkCancle(Sprite sprite) {
		
		if (remainingDuration <= 0 && glideTimer != null) { 
			
			Costume costume = sprite.getCostume();
			
			CatrobatDebug.on();
			CatrobatDebug.console("GLIDETO: cancel timer, destination reached");
			CatrobatDebug.console("GLIDETO: cancel timer, duration was " + durationInMilliSeconds);
			CatrobatDebug.off();
			
			glideTimer.cancel();
			
			costume.setXPosition(xDestination);
			costume.setYPosition(yDestination);
			
			
			costume.setMiddleX(xDestination);
			costume.setMiddleY(yDestination);
			
			currentStep = 0;
			
//			CatrobatDebug.on();
//			CatrobatDebug.console("GLIDETO: cancel timer, now redraw screen");
//			
//			long start = System.currentTimeMillis();
			
			Stage.getInstance().getSpriteManager().redrawScreen();
			
			script.resume();
			
//			CatrobatDebug.on();
//			CatrobatDebug.console("GLIDETO: redrawScreen needed " + (System.currentTimeMillis() - start) + " ms");
//			CatrobatDebug.off();
		}
	}
	
	private void updatePosition(Sprite sprite, int timePassed, int duration) {
		
		Costume costume = sprite.getCostume();

		double xPosition = costume.getXPosition();
		double yPosition = costume.getYPosition();
			
		xPosition += ((double) timePassed / duration) * (xDestination - xPosition);
		yPosition += ((double) timePassed / duration) * (yDestination - yPosition);

		costume.setXPosition(xPosition);
		costume.setYPosition(yPosition);
		
//	double xMiddlePosition = costume.getXPosition();
//	double yMiddlePosition = costume.getYPosition();
//
//	xMiddlePosition += ((double) timePassed / duration) * (xDestination - xMiddlePosition);
//	yMiddlePosition += ((double) timePassed / duration) * (yDestination - yMiddlePosition);
//	
//	costume.setMiddleX(xMiddlePosition);
//	costume.setMiddleY(yMiddlePosition);
		
//		CatrobatDebug.on();
//		CatrobatDebug.console("GLIDETO: updated position, now redraw screen");
//		
//		long start = System.currentTimeMillis();
		
		Stage.getInstance().getSpriteManager().redrawScreen();
		
//		CatrobatDebug.on();
//		CatrobatDebug.console("GLIDETO: redrawScreen needed " + (System.currentTimeMillis() - start) + " ms");
//		CatrobatDebug.off();
		
	}
}
