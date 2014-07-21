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
package org.catrobat.html5player.client.threading;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class WaitCountTest extends GWTTestCase {

	private WaitCount waitCount_ = null;
	private String waitingThreadName = "waitingThread";
	private CatThread waitingThread_ = null;
	private Callable task_ = null;
	
	public WaitCountTest() {
		
	}
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void gwtSetUp() {
		task_ = new Callable() {
			
			int n = 0;
			
			@Override
			public void run() {
				if(n < 5) {
					n++;
				}
				else {
					setWorkDone();
				}
				
			}
		};
		
		waitingThread_ = new CatThread(waitingThreadName, task_);
		waitCount_ = new WaitCount(waitingThread_);
	}
	
	/**
	 * 
	 */
	public void gwtTearDown() {
		waitingThread_ = null;
		task_ = null;
		waitCount_ = null;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testCountUp() {
		WaitCount waitCount = new WaitCount(waitingThread_);
		
		waitCount.countUp();
		waitCount.countUp();
		
		assertEquals(2, waitCount.getCount());
	}
	
	/**
	 * 
	 */
	public void testCountDown() {
		WaitCount waitCount = new WaitCount(waitingThread_);
		
		waitCount.countUp();
		waitCount.countUp();
		
		assertEquals(2, waitCount.getCount());
		
		waitCount.countDown();
		
		assertEquals(1, waitCount.getCount());
		
		waitCount.countDown();
		
		assertEquals(0, waitCount.getCount());
	}
	
	/**
	 * 
	 */
	public void testAwait() {
		WaitCount waitCount = new WaitCount(waitingThread_);
		
		waitingThread_.sleep();
		
		assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
			
		waitCount.await();
		
		assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
		
		Timer timer = new Timer() {
			public void run() {
				
				assertEquals(CatThread.READY, waitingThread_.getStatus());
	
				this.cancel();
				
				// tell the test system the test is now done
				finishTest();
	    	}
		};
	
	  	// Set a delay period significantly longer than the
	  	// event is expected to take
	  	delayTestFinish(100);
	
	  	// Schedule the event and return control to the test system
	  	timer.schedule(50); //50ms because the actual repeating time of the 
	  						//WaitCount is 20ms
		
	}
	
	/**
	 * uses the member waitCount_
	 */
	public void testAwaitWithCountUpDown() {

		waitingThread_.sleep();
		
		assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
			
		waitCount_.countUp(); //counter == 1
		
		waitCount_.await();
		
		assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
		
		Timer timer = new Timer() {
			
			int callCount = 0;
			
			public void run() {
	    	
				callCount++;
				
				if(callCount == 1) {
					assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
					assertEquals(1, waitCount_.getCount());
					waitCount_.countDown(); //counter == 0
					assertEquals(0, waitCount_.getCount());
				}
				else {
	
					assertEquals(CatThread.READY, waitingThread_.getStatus());
					assertEquals(0, waitCount_.getCount());
					
					this.cancel(); //important, because of 'scheduleRepeating'
					
					// tell the test system the test is now done
					finishTest();
				}
	    	}
		};
	
	  	// Set a delay period significantly longer than the
	  	// event is expected to take
	  	delayTestFinish(200);
	
	  	// Schedule the event and return control to the test system
	  	timer.scheduleRepeating(50); //50ms because the actual repeating time of the 
	  						         //WaitCount is 20ms
		
	}

}
