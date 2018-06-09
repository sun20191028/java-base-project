package com.base.functionClass.thread.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App {
	
	public static void main(String[] args) throws InterruptedException {
		
		CallableTest callable = new CallableTest();
		FutureTask<CallableTest> task = new FutureTask(callable);
		Thread th = new Thread(task);
		
		
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " : " + i);
			if(i == 20){
				th.start();
			}
			Thread.currentThread().sleep(2);
		}
		try {
			System.out.println(task.get()); //这里会阻塞 主线程，知道FutureTask返回 call的返回值，才会继续往下执行
											//call中报错也是在此处抛出
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
