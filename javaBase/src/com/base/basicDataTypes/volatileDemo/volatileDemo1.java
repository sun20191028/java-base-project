package com.base.basicDataTypes.volatileDemo;

public class volatileDemo1 {
	
	public static void main(String[] args) {
		final VolatiteService service = new VolatiteService();
		
		Thread thread = new Thread(){
			public void run(){
				service.doJob();
			}
		};
		Thread thread1 = new Thread(){
			public void run(){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				service.setAsleep(false);
			}
		};
		thread.start();
		thread1.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
