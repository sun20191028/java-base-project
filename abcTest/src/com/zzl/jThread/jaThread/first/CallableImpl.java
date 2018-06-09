package com.zzl.jThread.jaThread.first;

import java.util.concurrent.Callable;

public class CallableImpl implements Callable{

	public Integer call() throws Exception {
		int i=0;
		for (; i < 20; i++) {
			System.out.println(Thread.currentThread().getName()+"的i为："+i);
		}
		Integer ii=i;
		
		return ii;
	}

}
