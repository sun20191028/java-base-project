package com.zzl.jThread.jaThread.first;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
	public static void main(String[] args) {
		CallableImpl callable=new CallableImpl();
		FutureTask<Integer> task=new FutureTask<Integer>(callable);
		for (int i = 0; i < 30; i++) {
			System.out.println(Thread.currentThread().getName()+"-->"+i);
			if (i==20) {
				new Thread(task,"有返回的task线程").start();
				new Thread(task,"22222222222").start();
			}
		}
		try {
			System.out.println("子线程的返回值为："+task.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
