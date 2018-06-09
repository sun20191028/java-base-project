package com.base.functionClass.thread.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.base.functionClass.thread.task.CallableTest;

public class ThreadPoolTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService pool = Executors.newFixedThreadPool(5);
//		ExecutorService pool = Executors.newWorkStealingPool(8);
//		ExecutorService pool = Executors.newWorkStealingPool();
		ThreadDemo thread = new ThreadDemo();
		pool.execute(thread);
		
		
		
		
		Thread.currentThread().sleep(5000);
		
//		System.exit(0);//强行退出虚拟机
	}
	
	
}
