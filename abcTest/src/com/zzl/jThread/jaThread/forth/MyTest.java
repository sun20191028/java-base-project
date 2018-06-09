package com.zzl.jThread.jaThread.forth;

public class MyTest extends Thread{
	private Account account;
	public MyTest(Account account,String name){
		super(name);
		this.account=account;
	}
	
	public void run(){
		for (int i = 0; i < 10; i++) {
			if(i==6){
				account.setName(getName());  //这个getName是继承Thread中的，是获取当前线程的线程名的方法。
											//这就是继承了Thread类的线程，之所以可以直接用getName的原因。
			}
			System.out.println("线程“"+Thread.currentThread().getName()+"”中的name值为"+account.getName()+"====》"+i);
//			System.out.println("线程名："+Thread.currentThread().getName());
		}
	}
}
