package com.base.functionClass.thread.jThread.jaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
 * 直接调用 thread的start ，为通过 thread 的start 启动线程
 * 若是通过线程池启动，是不走 thread 的start 去启动线程的。   走线程池  thread只是一个Runnable的实现类。直接调用的是其run方法。
 * 	相当与普通的 类。调用方法。
 */
public class OverrideStart {
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(){
			@Override
			public void start(){
				setName("自己命名的线程");
				System.out.println("start");
				super.start();
			}
			@Override
			public void run(){
				for (int i = 0	; i < 10; i++) {
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("over!");
			}
		};
		
		thread.start();
		
//		ExecutorService executor = Executors.newFixedThreadPool(2);
//		executor.execute(thread);
		
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		Thread[] threads = new Thread[group.activeCount()];
		int num = group.enumerate(threads);
		for (int i = 0; i < threads.length; i++) {
			System.out.println(threads[i].getName());
		}
	}
	
	
}
