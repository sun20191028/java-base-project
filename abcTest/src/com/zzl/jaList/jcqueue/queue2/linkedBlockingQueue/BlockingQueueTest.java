package com.zzl.jaList.jcqueue.queue2.linkedBlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueueTest test = new BlockingQueueTest();

        // 建立一个装苹果的篮子
        Basket basket = new Basket();

        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = new Producer("生产者--------------", basket);
        Producer producer2 = new Producer("生产者001", basket);
        Consumer consumer = new Consumer("消费者", basket);
        service.submit(producer);//submit 即走run方法。
        service.submit(producer2);
        service.submit(consumer);
        System.out.println("哈哈哈哈");
        // 程序运行5s后，所有任务停止
//        try {
//            Thread.sleep(1000 * 5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        service.shutdownNow();
    }
}
