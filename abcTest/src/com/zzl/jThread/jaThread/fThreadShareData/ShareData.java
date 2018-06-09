package com.zzl.jThread.jaThread.fThreadShareData;

public class ShareData {
	private int i;
	public void printNumber(){
		for (; i < 20; i++) {
			System.out.println("printNumber==>"+Thread.currentThread().getName()+"-->"+i);
		}
	}
	public void printZimu(){
		for (; i <30; i++) {
			System.out.println("printZimu==>"+Thread.currentThread().getName()+"-->"+i);
		}
	}
}
