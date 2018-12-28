package com.base.functionClass.thread.jThread.jaThread.forth;

public class Account {
	private ThreadLocal<String> threadLocal=new ThreadLocal<String>();
	public Account(String str){
		this.threadLocal.set(str);
		System.out.println("---"+this.threadLocal.get());
	}
	public String getName() {
		return threadLocal.get();
	}
	public void setName(String name) {
		this.threadLocal.set(name);
	}
	
}
