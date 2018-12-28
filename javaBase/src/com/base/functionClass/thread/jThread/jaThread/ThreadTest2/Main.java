package com.base.functionClass.thread.jThread.jaThread.ThreadTest2;

public class Main {
	public static void main(String[] args) {
		Pool pool =  new  ThreadPool();		 // new a ThreadPool
		// load resources on each page, and start #s of thread.
//		for(int i = 0;i<resourceList.size();i++){
//			Executor executor = (Executor) pool.getExecutor(); // get Executor form pool
//			Task resourceLoader = new ResourceLoader((String)resourceList.get(i));
//			executor.setTask(resourceLoader); // set the task to executor
//			executor.startTask(); // try to start the executor.
//		}

		// wait while all task are done, the destroy the pool.
		pool.destroy();
	}
	
}
