package com.base.basicDataTypes.volatileDemo;

import java.util.concurrent.CountDownLatch;

/**
 * 非volatile类型的64位数值变量（double 和long），java内存模型要求，变量的读取操作和写入操作都必须是原子操作，因为对于非volatile类型的long和double变量， jvm允许将64位的读操作或写操作分解位两个32位的操作。
所以多线程程序中使用共享且可变的long和double等类型变量也是不安全的。需要用volatile类声明它，或者用锁保护起来。


1 -- 79960000008265
1 -- 79970000008265
1 -- 79980000008265
2 -- 79850000008265
2 -- 79990000008266
2 -- 79990000008267


 * @author liangpro
 *
 */
public class VolatileDemo {
//	static long num = 0;
	static volatile long num = 0;
	static CountDownLatch latch=new CountDownLatch(2);//两个工人的协作  ，启动几个线程，传入几
	static final int sum = 10000;
	
	public static void main(String[] args) {
		
		Thread thread = new Thread(){
			
			public void run(){
				latch.countDown();
				for (int i = 0; i < sum; i++) {
					num = num+10000000000l;
					System.out.println("1 -- " + num);
				}
				
			}
		};
		Thread thread1 = new Thread(){
			public void run(){
				latch.countDown();
				for (int i = 0; i < sum; i++) {
					num++;
					System.out.println("2 -- " + num);
				}
			}
		};
		
		thread.start();
		thread1.start();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
