package com.base.functionClass.thread.threadPool;


public class ThreadDemo implements Runnable{

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			Thread currentThread = Thread.currentThread();
			ThreadGroup group = currentThread.getThreadGroup();
			
			Thread[] threads = new Thread[group.activeCount()];
			group.enumerate(threads);
			for (int j = 0; j < threads.length; j++) {
				System.out.println(threads[j].getName());
			}
			try {
				currentThread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
