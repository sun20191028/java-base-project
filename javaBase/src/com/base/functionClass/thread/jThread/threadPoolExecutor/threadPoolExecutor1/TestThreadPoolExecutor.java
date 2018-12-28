package com.base.functionClass.thread.jThread.threadPoolExecutor.threadPoolExecutor1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
	  
    private static int produceTaskSleepTime = 2*1000;  
      
    private static int produceTaskMaxNumber = 10;  
  
    public static void main(String[] args) {  
  
        // 构造一个线程池  
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 40, 3,  
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),  
                new ThreadPoolExecutor.DiscardOldestPolicy());  
        //ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,long keepAliveTime, TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler)
        /*
         * corePoolSize： 线程池维护线程的最少数量
         * maximumPoolSize：线程池维护线程的最大数量
         * keepAliveTime： 线程池维护线程所允许的空闲时间
         * unit： 线程池维护线程所允许的空闲时间的单位
         * workQueue： 线程池所使用的缓冲队列
         * handler： 线程池对拒绝任务的处理策略
         */
        
        for (int i = 1; i <= produceTaskMaxNumber; i++) {  
            try {  
                String task = "task@ " + i;  
                System.out.println("创建任务并提交到线程池中：" + task);  
                threadPool.execute(new ThreadPoolTask(task));  
//                Thread.sleep(produceTaskSleepTime);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
