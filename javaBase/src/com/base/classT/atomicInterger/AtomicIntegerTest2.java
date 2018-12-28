package com.base.classT.atomicInterger;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicIntegerTest2 {
 
    public static AtomicInteger count = new AtomicInteger(0);
    static volatile int sum = 0;//volatile修饰的变量能够在线程间保持可见性，能被多个线程同时读但是又能保证只被单线程写
    public static int num = 0;
    public static void main(String[] args) throws InterruptedException {
    	AtomicIntegerTest();
    	volatileTest();
    	addNum();
    	Thread.sleep(1000);
    	System.out.println("AtomicInteger count: " + count);
    	System.out.println("volatile sum: " + sum);
    	System.out.println(num);
    }
    
    static void AtomicIntegerTest() throws InterruptedException{
    	for (int i = 0; i < 100; i++) {
    		new Thread() {
    			public void run() {
    				for (int j = 0; j < 100; j++) {
    					count.getAndIncrement();
    				}
    			}
    		}.start();
    	}
    }
    
    static void addNum(){
    	for (int i = 0; i < 100; i++) {
    		new Thread() {
    			public void run() {
    				for (int j = 0; j < 100; j++) {
    					num++;
    				}
    			}
    		}.start();
    	}
    }
    
    static void volatileTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread() {
                public void run() {
                    for (int j = 0; j < 100; j++) {
                    	sum++;
                    }
                }
            }.start();
        }
    }


}
