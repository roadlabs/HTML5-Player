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
package org.catrobat.html5player.client.threading;

import java.util.ArrayList;

import org.catrobat.html5player.client.CatrobatDebug;

public class CatThread {

	public final static int READY = 1;
	public final static int SLEEPING = 2;
	public final static int DESTROY = 3;
	
	private String name;
	private Callable task;
	private int status;
	
	private ArrayList<WaitCount> waitingSignalers;
	private WaitCount resumeSignaler = null;
	
	//##########################################################################
	
	/**
	 * 
	 * @param name
	 * @param task
	 */
	public CatThread(String name, Callable task) {
		this.name = name;
		this.task = task;
		this.status = READY;
		
		CatrobatDebug.debug("new thread with name: " + this.name);
		
		this.task.setExecutorName(this.name);
		
		this.waitingSignalers = new ArrayList<WaitCount>();
	}
	
	//##########################################################################
	
	public void run() {
		
		
		
//      CatrobatDebug.debug("Thread '" + this.name + "' running...");
		
		//work
		task.run();
		
		//still work to do
		if(!task.isWorkDone())
			return;
		
		//work is done
		
		//but resume signal has not yet arrived
		if(resumeSignaler != null)
			return;
		
		//if there are waiting threads
		if(numberOfWaitingSignalers() != 0)
			this.signalFinishedExecution();
		
		this.status = DESTROY;
		
	}
	
	//##########################################################################
	
	/**
	 * sets the status to READY if the current status is SLEEPING
	 */
	public void wake() {
		if(this.status == SLEEPING)
			this.status = READY;
	}
	
	/**
	 * sets the status to SLEEPING
	 */
	public void sleep() {
		this.status = SLEEPING;
	}
	
	/**
	 * sets the status to DESTROY
	 */
	public void kill() {
		this.status = DESTROY;
	}
	
	//##########################################################################
	
	public String getName() {
		return this.name;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public Callable getTask() {
		return this.task;
	}
	
	//##########################################################################
	
	/**
	 * the thread sets its status to SLEEP and waits until the signaler calls
	 * the function resumeSignal()
	 * @param signaler Signaler who wakes the thread up
	 */
	public void awaitSignal(WaitCount signaler) {
		this.resumeSignaler = signaler;
		sleep();
		resumeSignaler.await();
	}
	
	/**
	 * the thread sets its status to READY and removes the current signaler
	 */
	public void resumeSignal() {
		this.resumeSignaler = null;
		wake();
	}
	
	/**
	 * adds an waiting signaler to the threads signalers list and counts the 
	 * counter of the WaitCount up
	 * @param waitingSignaler
	 */
	public void signalFinishedExecution(WaitCount waitingSignaler) {
		if(!this.waitingSignalers.contains(waitingSignaler)) {
			waitingSignaler.countUp();
			this.waitingSignalers.add(waitingSignaler);
		}
	}
	
	/**
	 * counts for all waiting signalers the counter of the WaitCount down and
	 * clears the signalers list
	 */
	private void signalFinishedExecution() {
		for(WaitCount signaler : this.waitingSignalers) {
			signaler.countDown();
		}
		
		this.waitingSignalers.clear();
	}
	
	/**
	 * @return returns the number of the actual waiting signalers
	 */
	public int numberOfWaitingSignalers() {
		return this.waitingSignalers.size();
	}
	
	//##########################################################################
}
