package com.base.functionClass.thread.jThread.jaThread.third;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

	public static void main(String[] args) {
		RunnablePool runnableTask=new RunnablePool();
		CallablePool callableTask=new CallablePool();
		
		ExecutorService pool=Executors.newFixedThreadPool(6);
		pool.submit(runnableTask);
		pool.submit(runnableTask);
		pool.submit(callableTask);
		pool.shutdown();
		
	}

}
