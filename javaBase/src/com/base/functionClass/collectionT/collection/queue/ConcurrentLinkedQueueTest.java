package com.base.functionClass.collectionT.collection.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
	
	public static void main(String[] args) {
		ConcurrentLinkedQueue q = new ConcurrentLinkedQueue();
		q.offer(9);
		q.offer(88);
		q.offer(6);
		q.offer(-3);
		q.offer(20);
		q.offer(18);
		System.out.println(q);
		System.out.println(q.peek());
		System.out.println(q);
		System.out.println(q.poll());
		System.out.println(q);
		
	}
	
	
}
