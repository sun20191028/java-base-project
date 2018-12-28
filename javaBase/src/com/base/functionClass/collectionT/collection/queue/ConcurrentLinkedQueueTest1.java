package com.base.functionClass.collectionT.collection.queue;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentLinkedQueueTest1 {
	static ConcurrentLinkedQueueSource<Integer> q = new ConcurrentLinkedQueueSource();
	static AtomicInteger atom = new AtomicInteger(1);
	
	
	public static void main(String[] args) {
//		q.offer(9);
//		q.offer(88);
//		q.offer(6);
//		q.offer(-3);
//		q.offer(20);
//		q.offer(18);
//		System.out.println(q);
//		System.out.println(q.peek());
//		System.out.println(q);
//		System.out.println(q.poll());
//		System.out.println(q);
		new Demo().start();
		new Demo().start();
		new Demo().start();
		new Demo().start();
		new Demo().start();
		
		Iterator<Integer> ite = q.iterator();
		while(ite.hasNext()){
			int num = ite.next();
		}
	}
	
	static class Demo extends Thread{
		public void run(){
//			for (int i = 0; i < 100000; i++) {
			for (int i = 0; i < 100000; i++) {
				q.offer(atom.incrementAndGet());
			}
			for (int i = 0; i < 100000; i++) {
				if(null == q.poll()){
					System.out.println("null"+i);
				}
			}
			System.out.println("over");
		}
	}
	
}
