package com.base.functionClass.thread.jThread.jaThread.ThreadTest2;

import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ThreadPool implements Pool {
	private boolean isShut;
	private LinkedList pool;
//	private static Properties prop = PropReader.getProperties("webconfig.properties");
//	private int size = Integer.parseInt(prop.getProperty("threadsperpage", "3"));

	public ThreadPool() {
		// read configuration and set the
		// content of pool by objects of Executor
		isShut = false; // set the status of pool to active
		pool = new LinkedList();
//		for (int i = 0; i < size; i++) {
//			Executor executor =(Executor) new ExecutorImpl(); // new a executor thread
//			pool.add(executor); // add it to pool
//			((ExecutorImpl) executor).start(); // start it
//		}
	}

	public void destroy() { // Destroy
		synchronized (pool) {
			isShut = true; // set the status of pool to inactive
			pool.notifyAll(); // notify all listener.
			pool.clear(); // clear the list of threads
		}
	}

	public  Executor getExecutor(){   
       Executor ret =  null ;   
        synchronized (pool){ //return if any.   
            if (pool.size() >  0 ){   
               ret = (Executor)pool.removeFirst();   
           } else {   
                try  {   
                   pool.wait();   
               }  catch  (InterruptedException e) {   
                   e.printStackTrace();   
               }   
               ret = (Executor)pool.removeFirst();   
           }   
       }   
        return  ret;   
	}
}
