package com.base.basic.thread.chapter6.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    //固定大小的线程池：
    //初始化一个指定线程数的线程池，其中corePoolSize == maximumPoolSize，使用LinkedBlockingQuene作为阻塞队列，当线程池没有可执行任务时，也不会释放线程。
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static ThreadPoolExecutor executor_ = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());

    //缓存线程池：
    //初始化一个可以缓存线程的线程池，默认缓存60s，线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；
    //和newFixedThreadPool创建的线程池不同，newCachedThreadPool在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源，当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销；
    //所以，使用该线程池时，一定要注意控制并发的任务数，否则创建大量的线程可能导致严重的性能问题;
    private static ExecutorService executor2 = Executors.newCachedThreadPool();
    private static ExecutorService executor2_ = new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());

    //初始化的线程池中只有一个线程，如果该线程异常结束，会重新创建一个新的线程继续执行任务，唯一的线程可以保证所提交任务的顺序执行，内部使用LinkedBlockingQueue作为阻塞队列。
    private static ExecutorService executor3 = Executors.newSingleThreadExecutor();
//  因为FinalizableDelegatedExecutorService类是不可直接访问的，这样写会报错，所以注释掉
//  private static ExecutorService executor3_ = new FinalizableDelegatedExecutorService
//            (new ThreadPoolExecutor(1, 1,
//                    0L, TimeUnit.MILLISECONDS,
//                    new LinkedBlockingQueue<Runnable>()));

    //定时任务线程池：
    //初始化的线程池可以在指定的时间内周期性的执行所提交的任务，在实际的业务场景中可以使用该线程池定期的同步数据。
    private static ScheduledExecutorService executor4 = Executors.newScheduledThreadPool(5);
//  因为new DelayedWorkQueue()这个类是内部类所以这里也不可以直接这样写，这样写是为了让大家了解它的实现本质
//  private static ExecutorService executor4_ = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 0, NANOSECONDS,
//            new DelayedWorkQueue());  


    public static void main(String[] args){
        if(!executor.isShutdown())
            executor.execute(new Task());
        Future f = executor.submit(new Task());
        executor_.execute(new Task());
        executor_.getLargestPoolSize();
    }

    
    static class Task implements Runnable{
        public void run(){
            System.out.println(Thread.currentThread().getName());
        }
    }
}