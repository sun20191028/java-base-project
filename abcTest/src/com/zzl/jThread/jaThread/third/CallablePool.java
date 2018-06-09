package com.zzl.jThread.jaThread.third;

import java.util.concurrent.Callable;

public class CallablePool implements Callable{

	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		for (; i < 50; i++) {
			System.out.println("|||||||||||||"+Thread.currentThread().getName()+"callableThread-->"+i);
		}
		return i;
	}

}
