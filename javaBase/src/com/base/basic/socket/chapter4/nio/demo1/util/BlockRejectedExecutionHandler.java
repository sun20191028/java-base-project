package com.base.basic.socket.chapter4.nio.demo1.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


public class BlockRejectedExecutionHandler implements RejectedExecutionHandler {

    public BlockRejectedExecutionHandler() { }

    /**
     * Always throws RejectedExecutionException.
     *
     * @param r the runnable task requested to be executed
     * @param e the executor attempting to execute this task
     * @throws RejectedExecutionException always.
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {//此方法 由execute(runnable) 的线程调用。
    	//当 LinkedBlockingQueue 阻塞队列已满，并且线程池达到最大线程 的时候， execute 时，执行拒绝策略。
        try {//此拒绝策略获取线程池的  阻塞队列。调用  put方法，若线程池依然满状态则 阻塞在这里
			e.getQueue().put(r);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
    }
}