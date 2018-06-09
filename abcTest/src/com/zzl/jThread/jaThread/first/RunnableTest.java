package com.zzl.jThread.jaThread.first;

public class RunnableTest implements Runnable{
	int i = 0;
	
	public void run() {
		// TODO Auto-generated method stub
		for (; i < 10; i++) {
			System.out.println(Thread.currentThread().getName()+"-->"+i);
		}
	}
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
//			System.out.println("主："+Thread.currentThread().getName()+"-->"+i);
			if(i==10){
				RunnableTest rt=new RunnableTest();
				new Thread(rt,"线程1").start();
				new Thread(rt,"线程二").start();
			}
		}
	}
}
