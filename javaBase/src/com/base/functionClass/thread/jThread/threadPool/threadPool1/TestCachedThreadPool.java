package com.base.functionClass.thread.jThread.threadPool.threadPool1;

import java.util.concurrent.*;

public class TestCachedThreadPool {
	
	public static void main(String[] args) {
		
        /**
         * 创建可缓存线程池，当线程池大小超过了处理任务所需的线程，
         * 那么就会回收部分空闲（一般是60秒无执行）的线程，当有任务来时，又智能的添加新线程来执行。
         * newCachedThreadPool()
         */
        ExecutorService pool = Executors.newCachedThreadPool();
        //创建实现了Runnable接口对象
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        Thread t5 = new MyThread("t5");
        //将线程放入池中进行执行
//        pool.execute(t1);
//        pool.execute(t2);
//        pool.execute(t3);
//        pool.execute(t4);
//        pool.execute(t5);
//        //关闭线程池
//        pool.shutdown();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        
        
    }
	
}
