package com.zzl.jThread.jaThread.fThreadShareData;

public class ProducerAndConsumer {
	public static void main(String[] args) {
		final ShareData shareData=new ShareData();
		new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
//				for (int i = 0; i < 50; i++) {
					shareData.printNumber();
//				}
			}
			
		},"aa").start();
		new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				shareData.printZimu();
			}
			
		},"bb").start();
	}
}
