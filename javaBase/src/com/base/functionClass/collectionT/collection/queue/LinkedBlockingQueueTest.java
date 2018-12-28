package com.base.functionClass.collectionT.collection.queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {
	static final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);
	
	public static void main(String[] args) {
		doJob();
//		doJob2();
		
	}
	static void doJob2(){
		new Thread(){
			public void run(){
				for (; ; ) {
					System.out.println(queue.size());
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				for (; ; ) {
					try {
						queue.put(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		new Thread(){
			public void run(){
				for (; ; ) {
					try {
						queue.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	static void doJob(){
		new Thread(){
			public void run(){
				
//				System.out.println(queue.element());
				for (; ; ) {
					System.out.println(queue.size());
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				for (int i = 0; i < 100; i++) {
					queue.add(i);
//					System.out.println("offer " + queue.offer(i));
					try {
						Thread.currentThread().sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				for (int i = 0; i < 100; i++) {
					queue.remove();
//					System.out.println("poll " + queue.poll());
//					System.out.println("peek " + queue.peek());
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
}
