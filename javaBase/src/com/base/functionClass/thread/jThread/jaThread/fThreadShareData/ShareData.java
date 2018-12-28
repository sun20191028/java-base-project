package com.base.functionClass.thread.jThread.jaThread.fThreadShareData;

public class ShareData {
//	private int i;
	private volatile int i;
	public void printNumber(){
		for (; i < 20; i++) {
			System.out.println("printNumber==>"+Thread.currentThread().getName()+"-->"+i);
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void printZimu(){
		for (; i <30; i++) {
			System.out.println("printZimu==>"+Thread.currentThread().getName()+"-->"+i);
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
