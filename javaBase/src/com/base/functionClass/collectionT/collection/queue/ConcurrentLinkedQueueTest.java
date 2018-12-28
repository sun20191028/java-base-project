package com.base.functionClass.collectionT.collection.queue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author liangpro
 *
 */
public class ConcurrentLinkedQueueTest {
	static StringBuffer stb=new StringBuffer();
    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
    private static int count = 2; // 线程个数
    //CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
    /**
     * 会出现线程数控制重复。他的作用主要是“在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待”
     */
    private static CountDownLatch latch = new CountDownLatch(count);

    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        ConcurrentLinkedQueueTest.offer();
        /**
         * 不是for循环会开辟多个线程。而是这里的for循环，每进入一次 线程池submit一次，便开辟了一条线程
         * 若不开辟线程 则只有一条主线程。若开辟了线程则如同在主线程中开了一个分支。
         */
        
        for (int i = 0; i < 10; i++) {
            es.submit(new Poll());
        }
        latch.await(); //使得主线程(main)阻塞直到latch.countDown()为零才继续执行
        stb.append("cost time " + (System.currentTimeMillis() - timeStart) + "ms\r\n");
//        System.out.println("cost time " + (System.currentTimeMillis() - timeStart) + "ms");
        File out = new File("./Out.txt");
        if(!out.exists()) {
            try {
                out.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        	writer.append(stb.toString());
        	writer.flush();
        	writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        es.shutdown();
    }
    /**
     * 生产
     */
    public static void offer() {
        for (int i = 0; i < 10000; i++) {
            queue.offer(i);
        }
    }
    /**
     * 消费
     */
    static class Poll implements Runnable {
        public void run() {
            // while (queue.size()>0) {
            while (!queue.isEmpty()) {
            	stb.append(queue.poll()+"\r\n");
//                System.out.println(queue.poll());
                try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            /**
             * 注意是在循环之外
             * latch是等于2的。值的设定在new CountDownLatch(count)中的count设定了。启动了10个线程。但是交由线程池中的4条线程代为处理。
             * 这里只需要两个线程运行结束就latch.await()就会放行。在类CountDownLatchTest已测试完毕
             * 这里测不出来难道是因为线程池的原因？？？？？
             */
            latch.countDown();
        }
    }
}
