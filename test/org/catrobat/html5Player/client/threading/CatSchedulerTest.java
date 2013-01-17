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
package org.catrobat.html5Player.client.threading;

import com.google.gwt.junit.client.GWTTestCase;

public class CatSchedulerTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5Player.html5player";
	}
	
	//--------------------------------------------------------------------------
	
	public void gwtTearDown() {
		CatScheduler.get().clear();
		CatScheduler.get().reviveScheduler();
	}
	
	//--------------------------------------------------------------------------
	
	/**
	 * 
	 */
	public void testGet() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		assertTrue(scheduler instanceof CatScheduler);
		assertEquals(scheduler, CatScheduler.get());
	}
	
	/**
	 * 
	 */
	public void testGetThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		//try to get a thread
		assertNull(scheduler.getThread(task.getExecutorName()));
		assertNull(scheduler.getThread("someName"));
		assertNull(scheduler.getThread(null));
		
		//add thread to scheduler
		scheduler.schedule(thread);
		
		//try to get thread
		CatThread threadFromScheduler = scheduler.getThread(task.getExecutorName());
		
		assertNotNull(threadFromScheduler);
		assertEquals(task.getExecutorName(), threadFromScheduler.getName());
		
		//add second thread to scheduler
		scheduler.schedule(thread2);
		
		//try to get thread
		CatThread threadFromScheduler2 = scheduler.getThread(task2.getExecutorName());
		
		assertNotNull(threadFromScheduler2);
		assertEquals(task2.getExecutorName(), threadFromScheduler2.getName());
	}
	
	/**
	 * 
	 */
	public void testGetThreadCount() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			
			@Override
			public void run() {
			}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);

		//add thread to scheduler
		scheduler.schedule(thread);
		
		//get threadcount
		assertEquals(1, scheduler.getThreadCount());
		
		//try to add the same thread
		scheduler.schedule(thread);
		
		//get threadcount
		assertEquals(1, scheduler.getThreadCount());
		
		//add second thread to scheduler
		scheduler.schedule(thread2);
		
		//get threadcount
		assertEquals(2, scheduler.getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testExecuteSingleThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 2)
					a++;
				
				if(a == 2)
					setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);

		//add thread to scheduler
		scheduler.schedule(thread);
		
		//get threadcount
		assertEquals(1, scheduler.getThreadCount());
		
		//execute
		scheduler.execute();
		
		//get threadcount, task not finished
		assertEquals(1, scheduler.getThreadCount());
		
		//execute
		scheduler.execute();
		
		//get threadcount, task finished
		assertEquals(0, scheduler.getThreadCount());
	}
	
	/**
	 * 3 threads
	 */
	public void testExecuteMultipleThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 2)
					a++;
				
				if(a == 2)
					setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 1)
					a++;
				
				if(a == 1)
					setWorkDone();
			}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 1)
					a++;
				
				if(a == 1)
					setWorkDone();
			}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);

		//add threads to scheduler
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		
		//get threadcount
		assertEquals(3, scheduler.getThreadCount());
		
		//execute
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount, thread2 finished
		assertEquals(2, scheduler.getThreadCount());
		
		//execute
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount, thread3 and thread finished
		assertEquals(0, scheduler.getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testExecuteSleepingThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 2)
					a++;
				
				if(a == 2)
					setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		//add threads to scheduler
		scheduler.schedule(thread);
		
		thread.sleep();
		
		//execute
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount, thread would have finished work, if not sleeping
		assertEquals(1, scheduler.getThreadCount());
		
		thread.wake();
		
		//execute, thread finishes work
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount
		assertEquals(0, scheduler.getThreadCount());
	}
	
	/**
	 * Thread should finish work after 2 execute()-calls, but gets killed before
	 * the first call of execute()
	 */
	public void testExecuteKillThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 2)
					a++;
				
				if(a == 2)
					setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		//add threads to scheduler
		scheduler.schedule(thread);

		thread.kill();
		
		//execute
		scheduler.execute();
		
		//get threadcount, thread got killed
		assertEquals(0, scheduler.getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testExecuteWakeSleepingThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create thread
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				if(a < 2)
					a++;
				
				if(a == 2)
					setWorkDone();
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		//add threads to scheduler
		scheduler.schedule(thread);

		thread.sleep();
		
		//execute, 
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount, thread would have finished work, if not sleeping
		assertEquals(1, scheduler.getThreadCount());
		
		thread.wake();
		
		//execute, thread finishes work
		scheduler.execute();
		scheduler.execute();
		
		//get threadcount
		assertEquals(0, scheduler.getThreadCount());
	}
	
	/**
	 * 
	 */
	public void testExecuteNoThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//execute, 
		assertTrue(scheduler.execute());
	}
	
	/**
	 *
	 */
	public void testKillSchedulerNoThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		scheduler.killScheduler();
		
		//execute, 
		assertFalse(scheduler.execute());
	}
	
	/**
	 *
	 */
	public void testKillSchedulerWithThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {
			}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		
		assertEquals(2, scheduler.getThreadCount());
		
		scheduler.killScheduler();
		
		assertEquals(0, scheduler.getThreadCount());
		
		//execute
		assertFalse(scheduler.execute());
	}
	
	/**
	 *
	 */
	public void testKillThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {
			}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {
			}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		
		assertEquals(2, scheduler.getThreadCount());
		
		scheduler.killThread(thread.getName());
		
		assertEquals(CatThread.DESTROY, thread.getStatus());

		scheduler.killThread(thread2.getName());
		
		assertEquals(CatThread.DESTROY, thread2.getStatus());
		
		assertEquals(2, scheduler.getThreadCount());
	}
	
	/**
	 *
	 */
	public void testGetCurrentThreadAndIndexSimpleWithSingleThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			
			private int a = 0;
			
			@Override
			public void run() {
				a++;
				
				if(a == 2)
					setWorkDone();
			}
			
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		scheduler.schedule(thread);

		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		
		scheduler.execute(); //a == 1
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0", 0, scheduler.getCurrentThreadIndex());
		
		scheduler.execute(); //a == 2, task finished
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
	}
	
	/**
	 *
	 */
	public void testGetCurrentThreadAndIndexSimpleWithMultipleThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (1)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread3
		
		assertEquals("not thread3", thread3, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 2", 2, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread4
		
		assertEquals("not thread4", thread4, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 3", 3, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (1)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
	}	
	
	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfCurrentThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (1)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		thread3.kill();
		
		scheduler.execute(); //thread3
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (2)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 3", 3, scheduler.getThreadCount());
		
		scheduler.execute(); //thread4
		
		assertEquals("not thread4", thread4, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 2", 2, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 3", 3, scheduler.getThreadCount());
	}
	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfCurrentThreadAndThreadsInFront() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (1)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (1)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		thread2.kill();
		thread3.kill();
		
		scheduler.execute(); //thread3
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (2)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 2", 2, scheduler.getThreadCount());
		
		scheduler.execute(); //thread4
		
		assertEquals("not thread4", thread4, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (2)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 2", 2, scheduler.getThreadCount());
	}
	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfCurrentThreadAndAllThreadsInFront() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1 (1)", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (1)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4",4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (1)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		thread.kill();
		thread2.kill();
		thread3.kill();
		
		scheduler.execute(); //thread3
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1 (2)", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 1", 1, scheduler.getThreadCount());
		
		scheduler.execute(); //thread4
		
		assertEquals("not thread4", thread4, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (2)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 1", 1, scheduler.getThreadCount());
	}
	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfAllThreads() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1 (1)", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		thread.kill();
		thread2.kill();
		thread3.kill();
		thread4.kill();
		
		scheduler.execute(); //thread3
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1 (2)", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 0", 0, scheduler.getThreadCount());
	}

	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfThreadsInFrontOfCurrentThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4",4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (1)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		thread2.kill();
		
		scheduler.execute(); //thread3
		
		assertEquals("not thread3", thread3, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1 (2)", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 3", 3, scheduler.getThreadCount());
		
		scheduler.execute(); //thread4
		
		assertEquals("not thread4", thread4, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 2", 2, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 3", 3, scheduler.getThreadCount());
	}
	
	/**
	 * for testing of the function cleanUpThreads
	 */
	public void testRemovealOfThreadsAfterCurrentThread() {
		
		//get scheduler
		CatScheduler scheduler = CatScheduler.get();
		
		//create threads
		String threadName = "threadName";
		Callable task = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread = new CatThread(threadName, task);
		
		String threadName2 = "threadName2";
		Callable task2 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread2 = new CatThread(threadName2, task2);
		
		String threadName3 = "threadName3";
		Callable task3 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread3 = new CatThread(threadName3, task3);
		
		String threadName4 = "threadName4";
		Callable task4 = new Callable() {
			@Override
			public void run() {}
		};
		
		CatThread thread4 = new CatThread(threadName4, task4);
		//
		
		scheduler.schedule(thread);
		scheduler.schedule(thread2);
		scheduler.schedule(thread3);
		scheduler.schedule(thread4);
		
		assertNull("not null", scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not -1", -1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4", 4, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (1)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 4",4, scheduler.getThreadCount());
		
		thread3.kill();
		thread4.kill();
		
		scheduler.execute(); //thread2
		
		assertEquals("not thread2", thread2, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 1", 1, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 2", 2, scheduler.getThreadCount());
		
		scheduler.execute(); //thread
		
		assertEquals("not thread", thread, scheduler.getCurrentThread());
		assertEquals("currentThreadIndex not 0 (2)", 0, scheduler.getCurrentThreadIndex());
		assertEquals("number of threads not 2", 2, scheduler.getThreadCount());
	}

}
