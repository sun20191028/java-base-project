package com.zzl.jThread.jaThread.third;

public class RunnablePool implements Runnable{
	int i = 0;

	public void run() {
		// TODO Auto-generated method stub
		for (; i < 10; i++) {
			System.out.println("========"+Thread.currentThread().getName()+"  "+i);
		}
	}

}
