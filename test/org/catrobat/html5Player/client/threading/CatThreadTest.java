package org.catrobat.html5Player.client.threading;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;

public class CatThreadTest extends GWTTestCase {

	//for asynchronous testing-----------
	private WaitCount waitCount_ = null;
	private String waitingThreadName = "waitingThread";
	private CatThread waitingThread_ = null;
	private Callable taskOfWaitingThread_ = null;
	
	private CatThread threadToWaitFor_ = null;
	private CatThread threadToWaitFor2_ = null;
	//-----------------------------------
	
	private CatThread thread_ = null; //thread for asynchronous testing
	private Callable task_ = null;    //task for asynchronous testing
	
	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void gwtTearDown() {
		
		waitCount_ = null;
		waitingThread_ = null;
		taskOfWaitingThread_ = null;
		
		thread_ = null;
		task_ = null;
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testNewCatThread() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		assertTrue(thread instanceof CatThread);
		assertEquals(task.getExecutorName(), thread.getName());
		assertEquals(CatThread.READY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testSleep() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.sleep();

		assertEquals(CatThread.SLEEPING, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testKill() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.kill();

		assertEquals(CatThread.DESTROY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testWakeWhileSleeping() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.sleep();
		thread.wake();

		assertEquals(CatThread.READY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testWakeAfterKill() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.kill();
		thread.wake();

		assertEquals(CatThread.DESTROY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testWake() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.wake();

		assertEquals(CatThread.READY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testRun() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
				
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.run();

		assertEquals(CatThread.READY, thread.getStatus());
	}
	
	/**
	 * 
	 */
	public void testRunWorkDone() {
		String threadName = "threadName";
		
		Callable task = new Callable() {
			
			@Override
			public void run() {
				setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		thread.run();

		assertEquals(CatThread.DESTROY, thread.getStatus());
	}
	
	/**
	 * also tests the function resumeSignal()
	 */
	public void testAwaitSignal() {

		taskOfWaitingThread_ = new Callable() {
			
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
		
		waitingThread_ = new CatThread(waitingThreadName, taskOfWaitingThread_);
		waitCount_ = new WaitCount(waitingThread_);
		
		waitCount_.countUp(); //counter == 1
		
		waitingThread_.awaitSignal(waitCount_);
		
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
	
	
	/**
	 * also tests the function resumeSignal()
	 */
	public void testSignalFinishedExecution() {
		
		String threadNameOfThreadToWaitFor = "waitForThread";
		Callable taskOfThreadToWaitFor = new Callable() {

			@Override
			public void run() {
					setWorkDone();	
			}
		};
		
		threadToWaitFor_ = new CatThread(threadNameOfThreadToWaitFor,
										 taskOfThreadToWaitFor);
		
		String threadNameOfThreadToWaitFor2 = "waitForThread2";
		Callable taskOfThreadToWaitFor2 = new Callable() {

			@Override
			public void run() {
					setWorkDone();	
			}
		};
		
		threadToWaitFor2_ = new CatThread(threadNameOfThreadToWaitFor2,
										  taskOfThreadToWaitFor2);

		taskOfWaitingThread_ = new Callable() {
			
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
		
		waitingThread_ = new CatThread(waitingThreadName, taskOfWaitingThread_);
		waitCount_ = new WaitCount(waitingThread_);
		
		threadToWaitFor_.signalFinishedExecution(waitCount_);
		threadToWaitFor2_.signalFinishedExecution(waitCount_);
		
		assertEquals(2, waitCount_.getCount());
		
		assertEquals(1, threadToWaitFor_.numberOfWaitingSignalers());
		assertEquals(1, threadToWaitFor2_.numberOfWaitingSignalers());
		
		waitingThread_.awaitSignal(waitCount_);
		
		assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
		
		Timer timer = new Timer() {
			
			int callCount = 0;
			
			public void run() {
	    	
				callCount++;
				
				if(callCount == 1) {
					assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
					assertEquals(2, waitCount_.getCount());
					threadToWaitFor_.run(); //counter == 1
					assertEquals(0,threadToWaitFor_.numberOfWaitingSignalers());
					assertEquals(1, waitCount_.getCount());
				}
				else if(callCount == 2){
					assertEquals(CatThread.SLEEPING, waitingThread_.getStatus());
					assertEquals(1, waitCount_.getCount());
					threadToWaitFor2_.run(); //counter == 0
					assertEquals(0,threadToWaitFor2_.numberOfWaitingSignalers());
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
	  	delayTestFinish(300);
	
	  	// Schedule the event and return control to the test system
	  	timer.scheduleRepeating(50); //50ms because the actual repeating time of the 
	  						         //WaitCount is 20ms
	}
	
	
//	/**
//	 * 
//	 */
//	public void testTaskPauseWork() {
//		String threadName = "threadName";
//		
//		Callable task = new Callable() {
//			private int a = 0;
//			
//			@Override
//			public void run() {
//				if(a == 0)
//					pauseWork();
//				
//				a++;
//				
//				if(a == 2)
//					setWorkDone();
//			}
//		};
//		
//		CatThread thread = new CatThread(threadName, task);
//		
//		thread.run();
//		thread.run();
//		thread.run();
//		thread.run();
//
//		assertEquals(CatThread.READY, thread.getStatus());
//	}
//	
//	/**
//	 * 
//	 */
//	public void testTaskResumeWork() {
//		String threadName = "threadName";
//		
//		task_ = new Callable() {
//			private int a = 0;
//			
//			@Override
//			public void run() {
//				if(a == 0)
//					pauseWork();
//				
//				a++;
//				
//				if(a == 2)
//					setWorkDone();
//			}
//		};
//		
//		thread_ = new CatThread(threadName, task_);
//		thread_.run();
//		thread_.run();
//		thread_.run();
//		thread_.run();
//		assertEquals(CatThread.READY, thread_.getStatus());
//		
//		Timer timer = new Timer() {
//		    public void run() {
//		    	
//		    	task_.resumeWork();
//		    	thread_.run();
//		    	assertEquals(CatThread.DESTROY, thread_.getStatus());
//
//		    	// tell the test system the test is now done
//		    	finishTest();
//		    }
//		  };
//
//		  // Set a delay period significantly longer than the
//		  // event is expected to take
//		  delayTestFinish(300);
//
//		  // Schedule the event and return control to the test system
//		  timer.schedule(100);
//	}

}
