package com.base.functionClass.thread.task;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest implements Callable<Integer>{


	
	@Override
	public Integer call() throws Exception{
		int i = 0;
		for ( i =  0 ; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + " ：  " + i);
			Thread.sleep(100);
//			if (i == 80) {
//				throw new Exception("test throw exception");
//			}
			
		}
		return i;
	}
	
	
}
