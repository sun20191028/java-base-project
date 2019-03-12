package com.base.basic.thread.chapter6.threadpool.test1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
	public static LinkedBlockingQueue queue = new LinkedBlockingQueue<Runnable>(10);
	public static ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(5,10,60L,TimeUnit.SECONDS,queue);
	
	
	public static void main(String[] args) {
		
		Runnable run = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		new Thread() {
			public void run() {
				while(true){
					System.out.println(fixedPool.getActiveCount() + " : " +  queue.size() + " : " + fixedPool.getTaskCount());
					try {
						Thread.currentThread().sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		int sleep = 500;
		for (int i = 0; i < 60; i++) {
			if(i>50){//30刚刚好
				sleep = 1000;
			}else if(i>50){
				sleep = 2000;
			}
			fixedPool.execute(run);
			try {
				Thread.currentThread().sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
}
