package org.catrobat.html5Player.client.threading;

public abstract class Callable {
	
	private boolean workDone = false;
	private boolean pause = false; //NOT IN USE
	
	private String executorName = null;
	
	//##########################################################################
	
	/**
	 *
	 */
	public abstract void run();
	
	//##########################################################################
	
	/**
	 * NOT IN USE
	 */
	protected void pauseWork() {
		this.pause = true;
	}
	
	/**
	 * NOT IN USE
	 */
	protected void resumeWork() {
		this.pause = false;
	}
	
	/**
	 * 
	 */
	protected void setWorkDone() {
		this.workDone = true;
	}
	
	/**
	 * 
	 */
	protected void resetWorkDone() {
		this.workDone = false;
	}
	
	//##########################################################################
	
	protected void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	
	protected String getExecutorName() {
		return this.executorName;
	}
	
	//##########################################################################
	
	/**
	 * 
	 * @return
	 */
	protected boolean isWorkDone() {
		return this.workDone;
	}
	
	/**
	 * 
	 * @return
	 */
	protected boolean isPausing() {
		return this.pause;
	}
}
