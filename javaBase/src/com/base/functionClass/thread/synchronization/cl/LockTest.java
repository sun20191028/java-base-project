package com.base.functionClass.thread.synchronization.cl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * lock 测试
 * @author liangpro
 *
 */
public class LockTest {
	static int num;
	static StringBuffer stb = new StringBuffer();
	public static void main(String[] args) {
		
		Runnable runn = new Runnable() {
			private final Lock lock = new ReentrantLock();
			private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
			
			
			@Override
			public void run() {
				
				lock.lock();
				try{
					for (int i = 0; i < 100; i++) {
						num++;
						Thread.currentThread().sleep(20);
						stb.append(Thread.currentThread().getName() + " : " + num + "\r\n");
					}
					System.out.println(stb);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
				
			}
		};

		Thread thread = new Thread(runn);
		Thread thread2 = new Thread(runn);
		thread.start();
		thread2.start();
		
	}
	
	
	
	
	
	
}
