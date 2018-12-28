package com.base.Unsafe.safe;

import java.util.concurrent.CountDownLatch;


public class UnsafeTest {
	public static int num;
	public static CountDownLatch latch = new CountDownLatch(50);//总共多少个线程就初始化多少，没个线程关闭记得调用一下countDown()
	//不要大了，也不要小了，大了 latch.await() 会一直阻塞。小了监控的线程还没跑完就继续往下执行了。
	
	public static void main(String[] args) throws InterruptedException {
		SafeEntity safe = new SafeEntity();
		for (int i = 0; i < 50; i++) {
			new ThreadDemo(safe,latch).start();
		}
		latch.await();
		System.out.println(safe.getNum());
		System.out.println(safe.getSum());
	}
	
	static class ThreadDemo extends Thread{
		SafeEntity safe;
		CountDownLatch latch;
		public ThreadDemo(SafeEntity safe,CountDownLatch latch){
			this.safe = safe;
			this.latch = latch;
		}
		
		public void run(){
			for (int i = 0; i < 100000; i++) {
				try {
					safe.numAddOne();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			latch.countDown();
		}
		
	}
}

