package com.base.basic.socket.chapter6.udp.demo1;

import java.util.concurrent.FutureTask;

public class BootstrapClient {
	
	public static void main(String[] args) throws InterruptedException {
		
//		for (int i = 0; i < 10; i++) {
			
//			ClientThread client = new ClientThread(4444+i);
			ClientThread client = new ClientThread(4444);
			FutureTask<ClientThread> task = new FutureTask<ClientThread>(client);
			Thread thread = new Thread(task);
			thread.start();
			
			Thread.currentThread().sleep(5000);
			task.cancel(true);
//		}
		
		
		
	}

	
}
