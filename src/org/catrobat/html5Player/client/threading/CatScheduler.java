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
package org.catrobat.html5Player.client.threading;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.catrobat.html5Player.client.CatrobatDebug;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;

public class CatScheduler implements RepeatingCommand {

	public final static int SCHEDULE_DELAY = 1; //milliseconds
	
	private static CatScheduler instance = null;
	
	private LinkedHashMap<String, CatThread> threadMap;
	
	private boolean alive = true;
	
	private CatThread currentThread = null;
	private int currentThreadIndex = -1;
	
	//##########################################################################
	
	/**
	 * 
	 */
	private CatScheduler() {
		threadMap = new LinkedHashMap<String, CatThread>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static CatScheduler get() {
		if(instance == null) {
			instance = new CatScheduler();
		}
		
		return instance;
	}
	
	//##########################################################################
	
	@Override
	public boolean execute() {
		
		long start = System.currentTimeMillis();
		
		if(currentThread == null && getThreadCount() == 0)
			return alive;
		
		ArrayList<CatThread> threads = new ArrayList<CatThread>(threadMap.values());
		
//		if(currentThread == null || getThreadCount() == 1) {
//			currentThread = threads.get(0);
//			currentThreadIndex = 0;
//		}
//		else {
//			int newCurrentThreadIndex = (threads.indexOf(currentThread) + 1) % getThreadCount();
//			currentThread = threads.get(newCurrentThreadIndex);
//		}
		
		currentThreadIndex = (currentThreadIndex + 1) % getThreadCount();
		currentThread = threads.get(currentThreadIndex);
		
		if(currentThread.getStatus() == CatThread.READY) {
			
			currentThread.run();
		
			CatrobatDebug.on();
//			CatrobatDebug.console("currentThreadIndex: " + currentThreadIndex);
			CatrobatDebug.console("One Thread-execution needed " + (System.currentTimeMillis() - start) + "ms, thread: " + currentThread.getName());
			CatrobatDebug.off();
		}
		else if(currentThread.getStatus() == CatThread.SLEEPING) {
//			System.out.println("currentThread is sleeping"); //nur zum debuggen
		}
		//esle if: eventuell nächsten thread auswählen??
		
		cleanUpThreads();
		
//		CatrobatDebug.on();
		CatrobatDebug.console("currentThreadIndex after cleanUp: " + currentThreadIndex);
		CatrobatDebug.off();
		
		return alive;
	}
	
	//##########################################################################

	/**
	 * 
	 * @param thread
	 */
	public void schedule(CatThread thread) {
		
		if(!threadMap.containsKey(thread.getName())) {
			threadMap.put(thread.getName(), thread);
		}
	}
	
	//##########################################################################
	
	/**
	 * 
	 */
	public void killScheduler() {
		alive = false;
		killThreads();
	}
	
	/**
	 * 
	 */
	public void killThreads() {
		//kill all threads
		for(CatThread thread : threadMap.values()) {
			thread.kill();
		}
		
		cleanUpThreads();
	}
	
	/**
	 * 
	 */
	private void cleanUpThreads() {
		
		Iterator<Entry<String, CatThread>> iterator = threadMap.entrySet().iterator();
		
		int removedThreadsBeforeCurrentThread = 0;
		int maxCheckedIndex = 0;
		
		while(iterator.hasNext()) {
			Entry<String, CatThread> entry = (Entry<String, CatThread>)iterator.next();
			if(entry.getValue().getStatus() == CatThread.DESTROY) {
				
				checkIfThreadToRemoveIsCurrentThread(entry.getValue());
				
				if(maxCheckedIndex < currentThreadIndex) {
					removedThreadsBeforeCurrentThread++;
				}
				
				iterator.remove();
			}
			
			maxCheckedIndex++;
		}
		
//		checkThreadCount();
		
		CatrobatDebug.on();
		CatrobatDebug.console("currentThreadIndex: " + currentThreadIndex);
		
		setCurrentThreadIndexAfterRemoval(removedThreadsBeforeCurrentThread);
		
		CatrobatDebug.console("currentThreadIndex after removal: " + currentThreadIndex);
		
		boolean debugYes = true;
		if(currentThread != null)
			debugYes = false;
		
		CatrobatDebug.console("SCHEDULER: removed " + removedThreadsBeforeCurrentThread + " threads before currentThread, currentThread removed: " + debugYes);
		CatrobatDebug.off();
	}
	
	/**
	 * Sets the status of the thread to DESTROY if it is already added to the
	 * scheduler
	 */
	public void killThread(String threadName) {
		if(threadMap.containsKey(threadName)) {
			threadMap.get(threadName).kill();
		}
	}
	
	/**
	 * checks if the current number of threads equals zero and resets in this
	 * case the currentThread and currentThreadIndex member variables
	 */
	private void checkThreadCount() {
		if(getThreadCount() == 0) {
			currentThread = null;
			currentThreadIndex = -1;
		}
	}
	
	/**
	 * checks if the thread to remove is the currentThread,
	 * @param threadToRemove
	 */
	private void checkIfThreadToRemoveIsCurrentThread(CatThread threadToRemove) {
		if(currentThread != null && threadToRemove.equals(currentThread)) {
			currentThread = null;
		}
	}
	
	/**
	 * checks if currentThread equals null (currentThread got removed from map)
	 * and sets the currentThreadIndex according to the number of threads which
	 * are scheduled. If the currentThread got not removed, the 
	 * currentThreadIndex is calculated of the currentThreadIndex minus the
	 * number of threads which where removed before the currentThread
	 * 
	 * @param removedThreadsBeforeCurrentThread
	 */
	private void setCurrentThreadIndexAfterRemoval(int removedThreadsBeforeCurrentThread) {
		
		//current thread got removed
		if(currentThread == null) {
			if((getThreadCount() == 0) || (getThreadCount() == 1)) {
				currentThreadIndex = -1;
			}
			else {
				currentThreadIndex -= (removedThreadsBeforeCurrentThread + 1);
			}
		}
		else { //current thread got not removed
			currentThreadIndex -= removedThreadsBeforeCurrentThread;
		}
	}
	
	//##########################################################################
	
	/**
	 * 
	 */
	public int getThreadCount() {
		return threadMap.size();
	}
	
	/**
	 * 
	 * @param threadName
	 * @return
	 */
	public CatThread getThread(String threadName) {
		if(threadMap.containsKey(threadName)) {
			return threadMap.get(threadName);
		}
		
		return null;
	}
	
	/**
	 * @return last thread which got executed, null if thread finished or no
	 * threads were added to the scheduler
	 */
	public CatThread getCurrentThread() {
		return this.currentThread;
	}
	
	/**
	 * @return index of the currentThread
	 */
	public int getCurrentThreadIndex() {
		return this.currentThreadIndex;
	}
	
	//##########################################################################
	
	/**
	 * FOR UNIT TESTING
	 */
	public void clear() {
		threadMap.clear();
		currentThread = null;
		currentThreadIndex = -1;
	}
	
	/**
	 * FOR UNIT TESTING
	 */
	public void reviveScheduler() {
		alive = true;
	}
	
	//##########################################################################
}
