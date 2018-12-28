package com.base.functionClass.thread.jThread.threadGroup.threadGroup1;

public class TestThread extends Thread{
	public TestThread(ThreadGroup group, String name){
		super(group,name);
	}
	
	public void run(){
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
