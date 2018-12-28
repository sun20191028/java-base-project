package com.base.functionClass.thread.jThread.threadPool.threadPool2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestCachedThreadPool {
	public static void main(String[] args) {
		RunnablePool r1 = new RunnablePool();
		RunnablePool r2 = new RunnablePool();
		RunnablePool r3 = new RunnablePool();
		RunnablePool r4 = new RunnablePool();
		RunnablePool r5 = new RunnablePool();
		
		/**
		 * 是一个对象，多个线程才会出现 数据共享。你这多个对象，怎么数据共享！！
		 */
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		new Thread(r1).start();
		
		
//		ExecutorService es=Executors.newCachedThreadPool();
//		es.execute(r1);
//		es.execute(r2);
//		es.execute(r3);
//		es.execute(r4);
//		es.execute(r5);
		
		
	}
}
