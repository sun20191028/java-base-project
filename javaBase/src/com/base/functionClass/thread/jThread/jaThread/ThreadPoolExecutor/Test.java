package com.base.functionClass.thread.jThread.jaThread.ThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
	 public static void main(String[] args) {   
         ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(100));
          
         for(int i=0;i<15;i++){
             MyTask myTask = new MyTask(i);
             executor.execute(myTask);
             System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
         }
//         executor.shutdown();
     }
}
