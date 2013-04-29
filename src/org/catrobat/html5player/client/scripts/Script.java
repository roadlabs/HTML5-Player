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
package org.catrobat.html5player.client.scripts;

import java.util.ArrayList;
import java.util.List;

import org.catrobat.html5player.client.CatrobatDebug;
import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.bricks.Brick;
import org.catrobat.html5player.client.bricks.BroadcastBrick;
import org.catrobat.html5player.client.bricks.BroadcastWaitBrick;
import org.catrobat.html5player.client.bricks.ForeverBrick;
import org.catrobat.html5player.client.bricks.LoopBeginBrick;
import org.catrobat.html5player.client.bricks.LoopEndBrick;
import org.catrobat.html5player.client.bricks.PlaySoundBrick;
import org.catrobat.html5player.client.bricks.RepeatBrick;
import org.catrobat.html5player.client.bricks.StopAllSoundsBrick;
import org.catrobat.html5player.client.bricks.WaitBrick;
import org.catrobat.html5player.client.threading.Callable;
import org.catrobat.html5player.client.threading.CatScheduler;
import org.catrobat.html5player.client.threading.CatThread;

import com.google.gwt.user.client.Timer;


public abstract class Script extends Callable implements Comparable<Script> {
	
	private static int id = 0;

	protected final Sprite sprite;

	protected final String type;

	protected final String name;

	protected final List<Brick> brickList;
	
	protected int currentBrick = 0;
	
	protected boolean scriptFinished = false;
	
	//##########################################################################

	public Script(Sprite sprite, String type, String name) {
		this.sprite = sprite;
		this.type = type;
		
		id++;
		
		this.name = name + id;
		this.brickList = new ArrayList<Brick>();
	}

	public final Sprite getSprite() {
		return sprite;
	}

	public final String getType() {
		return this.type;
	}

	public final String getName() {
		return this.name;
	}

	public Brick getBrick(int position) {
		return brickList.get(position);
	}

	public void addBrick(Brick brick) {
		if (brick != null)
			brickList.add(brick);
	}

	public void addBrick(Brick brick, int position) {
		if (brick != null)
			brickList.add(position, brick);
	}

	public void deleteBrick(int index) {
		brickList.remove(index);
	}

	public List<Brick> getBrickList() {
		return brickList;
	}
	
	//##########################################################################

	/**
	 * 
	 */
	public void run() {	

		if(brickList.isEmpty()) {
			CatrobatDebug.debug("no bricks, work is done");
			setWorkDone();
			return;
		}
		
		CatrobatDebug.debug(brickList.get(currentBrick).toString() + " : " + getSprite().getName());
		
		long start = System.currentTimeMillis();
		
		brickList.get(currentBrick).execute();
		
		CatrobatDebug.debug("brick-execution took " + (System.currentTimeMillis() - start) + " ms");
		CatrobatDebug.info("brick execution done");
		
		long start2 = System.currentTimeMillis();
		
		CatrobatDebug.info("Redrawing...");
		
		//redraw screen
		if(isRedrawNecessary()) { // check visibility -sprite.getCostume().isVisible()
			Stage.getInstance().getSpriteManager().redrawScreen();
			
			CatrobatDebug.debug("Redraw-execution took " + (System.currentTimeMillis() - start2) + " ms");
		}
				
		if(currentBrick < (brickList.size() - 1)) {
			currentBrick++;
		}
		else {
			setWorkDone();
			scriptFinished = true;
		}
	}

	/**
	 * 
	 * @param time
	 */
	public void pause(int time) {
		if (time > 0) {
			
			CatScheduler.get().getThread(getExecutor()).sleep();
			
			CatrobatDebug.debug("SCRIPT: " + getExecutor() + " sleep for " + time + " ms...");
			
			Timer wakeUpTimer = new Timer() {
				public void run() {
					resume();
				}
			};
			wakeUpTimer.schedule(time);
		}
	}
	
	/**
	 * thread sleeps and will not wake up till resume() gets called
	 */
	public void pause() {

		CatScheduler.get().getThread(getExecutor()).sleep();
		
		CatrobatDebug.debug("SCRIPT: " + getExecutor() + " sleep...");
	}

	/**
	 * 
	 */
	public void resume() {
		CatThread myself = CatScheduler.get().getThread(getExecutor());
		
		if(myself != null) {
			myself.wake();
			
			CatrobatDebug.debug("SCRIPT: " + getExecutor() + " wake up...");
		}
		else {
			//thread has finished execution and is already dead, no need to wake up
		}
	}
	
	/**
	 * 
	 * @param currentBrick
	 */
	public void setCurrentBrick(int currentBrick) {
		this.currentBrick = currentBrick;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCurrentBrick() {
		return this.currentBrick;
	}
	
	/**
	 * 
	 * @return
	 */
	public LoopBeginBrick getLastLoopBeginBrickWithoutLoopEndBrick() {
		
		int lastBrickIndex = (brickList.size() - 1);
		
		for(int i = lastBrickIndex; i >= 0; i--) {
			if(brickList.get(i) instanceof LoopBeginBrick) {
				LoopBeginBrick brick = (LoopBeginBrick)brickList.get(i);
				
				if(brick.getLoopEndBrick() == null)
					return brick;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getExecutor() {
		return super.getExecutorName();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasScriptFinished() {
		return scriptFinished;
	}
	
	/**
	 * Returns if a redraw of the screen is necessary
	 * @return true if a redraw is necessary, false otherwise
	 */
	private boolean isRedrawNecessary() {
		
		Brick executedBrick = brickList.get(currentBrick);
		boolean redraw = true;
		
		if(executedBrick instanceof BroadcastBrick)
			redraw = false;
		else if(executedBrick instanceof BroadcastWaitBrick)
			redraw = false;
		else if(executedBrick instanceof RepeatBrick)
			redraw = false;
		else if(executedBrick instanceof ForeverBrick)
			redraw = false;
		else if(executedBrick instanceof LoopEndBrick)
			redraw = false;
		else if(executedBrick instanceof WaitBrick)
			redraw = false;
		else if(executedBrick instanceof PlaySoundBrick)
			redraw = false;
		else if(executedBrick instanceof StopAllSoundsBrick)
			redraw = false;
			
		return redraw;
	}
	
	//##########################################################################

	@Override
	public int compareTo(Script other) {
		if (other == null)
			return -255;
		if (this.type == null && other.type != null) {
			return 128;
		} else if (this.type != null && other.type == null) {
			return -128;
		} else if (this.type != null && !this.type.equals(other.type)) {
			return this.type.compareTo(other.type);
		}
		if (this.name == null && other.name != null) {
			return 64;
		} else if (this.name != null && other.name == null) {
			return -64;
		} else if (this.name != null && !this.name.equals(other.name)) {
			return this.name.compareTo(other.name);
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Script other = (Script) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
