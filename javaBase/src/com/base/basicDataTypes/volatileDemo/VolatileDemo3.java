package com.base.basicDataTypes.volatileDemo;

public class VolatileDemo3 {
	public static int num;
	public volatile static int num1;
	
	
	public static void main(String[] args) {
		
		new Thread("a"){
			public void run(){
				
				for (int i = 0; i < 100; i++) {
					num = num+1;
					System.out.println(Thread.currentThread().getName()+"--num-->"+num);
				}
				for (int i = 0; i < 100; i++) {
					num1 = num1+1;
					System.out.println(Thread.currentThread().getName()+"--num1-->"+num1);
				}
			}
		}.start();
		
		new Thread("b"){
			public void run(){
				for (int i = 0; i < 100; i++) {
					num = num+1;
					System.out.println(Thread.currentThread().getName()+"--num-->"+num);
				}
				for (int i = 0; i < 100; i++) {
					num1 = num1+1;
					System.out.println(Thread.currentThread().getName()+"--num1-->"+num1);
				}
			}
		}.start();
		
		
		
		
		
		
	}
	
	
	
	
	
}
