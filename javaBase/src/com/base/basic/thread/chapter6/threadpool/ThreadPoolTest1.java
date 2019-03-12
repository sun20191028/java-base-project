package com.base.basic.thread.chapter6.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.base.functionClass.thread.task.CallableTest;

public class ThreadPoolTest1 {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(TimeUnit.SECONDS.toNanos(60l));
		
		ExecutorService pool = Executors.newFixedThreadPool(5);
//		ExecutorService pool = Executors.newWorkStealingPool(8);
//		ExecutorService pool = Executors.newWorkStealingPool();
//		ThreadDemo thread = new ThreadDemo();
//		pool.execute(thread);
//		ThreadPoolExecutor fixedPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
		ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(5,10,60L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
		
		
//		ThreadPoolExecutor cachedPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
//		ThreadPoolExecutor singlePool = (ThreadPoolExecutor)Executors.newSingleThreadExecutor();
//		fixedPool.getActiveCount();
//		
		
//		System.exit(0);//强行退出虚拟机
		
		
		/**
		 * 其实用ThreadPoolExecutor 去执行 Thread是资源浪费，它真正需要的只是一个实现runnable接口的  类的  对象，调用自身的方法。
		 * 线程池里面的 线程 都是 在线程池中 创建的  ，是ThreadPoolExecutor 的内部类 Worker的实例。
		 * 	此Worker中有一个 Runnable的 实例变量。存储  我们execute的对象。
		 */
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
//					System.out.println("这里是第" + i + "声 ，赵彦博晚上好！");
					System.out.println("这里是第" + i + "声 ，赵彦宸大笨蛋！");
					try {
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		pool.execute(runnable);
		
		
		
	}
	
	
}
