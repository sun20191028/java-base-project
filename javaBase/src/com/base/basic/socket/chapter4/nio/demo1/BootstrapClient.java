package com.base.basic.socket.chapter4.nio.demo1;

public class BootstrapClient {
	
	
	public static void main(String[] args) {
		
		ClientThread client = new ClientThread();
		client.init();
		client.start();
		
		
		
	}
	
	
	
	
	
}
