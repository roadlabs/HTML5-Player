package org.catrobat.html5Player.client.threading;

import java.util.ArrayList;

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
		
		System.out.println("new thread with name: " + this.name);
		
		this.task.setExecutorName(this.name);
		
		this.waitingSignalers = new ArrayList<WaitCount>();
	}
	
	//##########################################################################
	
	public void run() {
		
		
		
//		System.out.println("Thread '" + this.name + "' running...");
		
//		//check if task should pause
//		if(task.pause()) {
////			System.out.println("Callable wants to pause work");
//			return;
//		} //task shall resume
//		else { 
////			System.out.println("Callable wants to resume work");
//		}
		
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
