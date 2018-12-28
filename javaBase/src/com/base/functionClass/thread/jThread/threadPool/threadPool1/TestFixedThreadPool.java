package com.base.functionClass.thread.jThread.threadPool.threadPool1;

import java.util.concurrent.*;

public class TestFixedThreadPool {
	public static void main(String[] args) {
        /**
         * 固定数量的线程池，没提交一个任务就是一个线程，直到达到线程池的最大数量，然后后面进入等待队列直到前面的任务完成才继续执行
         * newFixedThreadPool(int)
         */
		
        ExecutorService pool = Executors.newFixedThreadPool(2);
        
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        Thread t5 = new MyThread("t5");
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        //关闭线程池
        pool.shutdown();
    }
}
