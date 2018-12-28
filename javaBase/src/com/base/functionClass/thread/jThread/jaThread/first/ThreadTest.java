package com.base.functionClass.thread.jThread.jaThread.first;

public class ThreadTest extends Thread{
	public static void main(String[] args) {
//		State.RUNNABLE;
		for (int i = 0; i < 50; i++) {
//			System.out.println(Thread.currentThread().getName()+"##"+i);
			if(i==20){
				new ThreadTest().start();
				new ThreadTest().start();
			}
		}
		/**
		 * 下面的不行。由于继承Thread的类创建的就是 线程对象。可以直接调用start（）方法。
		 * 但是实现Runnable的类 由于他创建的不是线程对象。只是线程对象的target，而多个线程可以共享一个target。
		 */
//		ThreadTest tt=new ThreadTest();
//		for (int i = 0; i < 100; i++) {
//			if(i==20){
//				tt.start(); //妈的傻逼啊！同一线程启动两次。
//				tt.start();
//			}
//		}
//		new ThreadTest("aaa").start();
	}
	
	public ThreadTest(){
		
	}
	public ThreadTest(String name){
		super(name);
	}
	int i=0;
	public void run(){
		for (;  i< 20; i++) {
			System.out.println("Name:"+getName()+"            id:"+getId()+"-->"+i);
//			try {
//				sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
}
