package com.zzl.jThread.jaThread.forth.comparison;

public class MyTest extends Thread{
	private Account account;
	public MyTest(Account account,String name){
		super(name);
		this.account=account;
	}
	
	public void run(){
		for (int i = 0; i < 10; i++) {
			if(i==6){
				account.setName(getName());  //这个就是因为同一个Account at对象  两个线程共用 ，切记是同一个对象，在两个线程间被使用。
												//实例变量的的范围是整个对象。而不是单一线程。
			}
			System.out.println("线程“"+Thread.currentThread().getName()+"”中的name值为"+account.getName()+"====》"+i);
		}
	}
	public static void main(String[] args) {
		Account at=new Account();
		new MyTest(at,"线程甲").start();
		new MyTest(at,"线程乙").start(); 
		
		
		/**
		 * 那么现在的问题是，如果不用ThreadLocal 如何获取线程中的变量值。
		 */
	}
}
