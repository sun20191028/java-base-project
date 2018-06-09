package com.zzl.jThread.threadPool.threadPool1;

public class MyThread extends Thread{
	
	int i=0;
	
	public MyThread(String name){
		super(name);
	}
	
    public void run() {
    	for (; i < 20; i++) {
    		System.out.println(Thread.currentThread().getName() + "===>执行中。。。"+i);
		}
//  	int i=0;
//       while(true){
//        	try {
//        		System.out.println(Thread.currentThread().getName() + "===>执行中。。。"+i);
//        		i++;
//    			sleep(1000);
//    		} catch (InterruptedException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
//        }
        
    }
}
