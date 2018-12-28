package com.base.functionClass.thread.jThread.threadPool.threadPool1;

import java.util.concurrent.*;

public class TestSingleThreadExecutor {
	public static void main(String[] args) {
        /**
         * 创建一个可重用单一线程数的线程池
         * newSingleThreadExecutor()
         */
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建实现了Runnable接口对象
        Thread tt1 = new MyThread("tt1");
        Thread tt2 = new MyThread("tt2");
        Thread tt3 = new MyThread("tt3");
        Thread tt4 = new MyThread("tt4");
        Thread tt5 = new MyThread("tt5");
        
//        tt1.start();
        //将线程放入池中并执行
        //之所以直接通过线程调用start方法，打出来的是线程名，而利用线程池，打出的却不是tt1的线程名，
        //是因为 这个线程交由线程池代为执行，
        pool.execute(tt1);
        pool.execute(tt2);
        pool.execute(tt3);
        pool.execute(tt4);
        pool.execute(tt5);
        //关闭
        pool.shutdown();
    }
}
