package com.zzl.jThread.threadPool.threadPool2;

public class RunnablePool implements Runnable{
	int i=0;
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			i++;
			System.out.println(Thread.currentThread().getName()+"    name===>"+i);
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
