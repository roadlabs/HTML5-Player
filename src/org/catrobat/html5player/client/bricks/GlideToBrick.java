/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
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
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Const;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.formulaeditor.Formula;
import org.catrobat.html5player.client.scripts.Script;

import com.google.gwt.user.client.Timer;

public class GlideToBrick extends Brick {

	private Formula xDestination;
	private Formula yDestination;
	
	private long startTime;
	private Timer glideTimer;

	private Script script;
	
	private Formula durationInSeconds;
	private int remainingDuration;

	public GlideToBrick(String spriteName, Formula duration, Formula newXDestination, Formula newYDestination, Script newScript) {
		super(spriteName);

		//this.xDestination = newXDestination + Stage.getInstance().getStageMiddleX();
		//this.yDestination = -newYDestination + Stage.getInstance().getStageMiddleY();
		this.xDestination = newXDestination;
		this.yDestination = newYDestination;
		
		this.script = newScript;

		this.durationInSeconds = duration;
		this.remainingDuration = 0;
	}
	
	public boolean execute(final Sprite sprite) {
		script.pause();
		
		startTime = System.currentTimeMillis();
		this.remainingDuration = (int) (1000.0 * durationInSeconds.interpretFloat(sprite));

		glideTimer = new Timer() {
			public void run() {
				
				CatrobatDebug.debug("GLIDETO: Timer run() - elapsed time = " + (System.currentTimeMillis() - startTime) + " ms");
				
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
	        double xDestination = this.xDestination.interpretFloat(sprite) + Stage.getInstance().getStageMiddleX();
	        double yDestination = -this.yDestination.interpretFloat(sprite) + Stage.getInstance().getStageMiddleY();
			Look look = sprite.getLook();
			
			CatrobatDebug.debug("GLIDETO: cancel timer, destination reached");
			CatrobatDebug.debug("GLIDETO: cancel timer, duration was " + durationInSeconds);
			
			glideTimer.cancel();
			
			look.setXPosition(xDestination);
			look.setYPosition(yDestination);
			
			
			look.setMiddleX(xDestination);
			look.setMiddleY(yDestination);
			
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
		
		Look look = sprite.getLook();
        double xDestination = this.xDestination.interpretFloat(sprite) + Stage.getInstance().getStageMiddleX();
        double yDestination = -this.yDestination.interpretFloat(sprite) + Stage.getInstance().getStageMiddleY();
		double xPosition = look.getXPosition();
		double yPosition = look.getYPosition();
			
		xPosition += ((double) timePassed / duration) * (xDestination - xPosition);
		yPosition += ((double) timePassed / duration) * (yDestination - yPosition);

		look.setXPosition(xPosition);
		look.setYPosition(yPosition);
		
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
