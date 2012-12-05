package org.catrobat.html5Player.client.threading;

import com.google.gwt.user.client.Timer;

public class WaitCount {

	private final int repeatTime = 20; //in ms
	private int count = 0;
	private CatThread waitingThread;
	private Timer checkCountTimer;
	
	//##########################################################################
	
	public WaitCount(CatThread thread) {
		this.waitingThread = thread;
		
		this.checkCountTimer = new Timer() {
			
			@Override
			public void run() {
				if(count == 0) {
					this.cancel();
					waitingThread.resumeSignal();
				}
			}
			
		};
	}
	
	//##########################################################################
	
	public void await() {
		this.checkCountTimer.scheduleRepeating(this.repeatTime);
	}
	
	public void countUp() {
		this.count++;
	}
	
	public void countDown() {
		this.count--;
	}
	
	public int getCount() {
		return this.count;
	}
	
	//##########################################################################
	
}
