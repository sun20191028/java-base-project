package com.base.basic.socket.chapter6.udp.demo1;

public class BootstrapClient {
	
	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			
			ClientThread client = new ClientThread(4444+i);
			client.start();
			Thread.currentThread().sleep(1000);
			
		}
		
		
		
	}
	
	
}
