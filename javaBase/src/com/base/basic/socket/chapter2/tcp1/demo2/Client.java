package com.base.basic.socket.chapter2.tcp1.demo2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static void main(String[] args) {
		
//		Socket serverSocket = new Socket("127.0.0.1", 8080);
		
		ThreadTest t1 = new ThreadTest(8080);
		ThreadTest t2 = new ThreadTest(8080);
		t1.start();
		t2.start();
	}
	
	static class ThreadTest extends Thread{
		int port = 0;
		public ThreadTest(int port){
			this.port = port;
		}
		
		public void run(){
			try {
				Socket socket = new Socket("127.0.0.1", port);
				
				OutputStream out = socket.getOutputStream();
				for (int i = 0; i < 50; i++) {
					out.write((i + "--------").getBytes());
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				socket.shutdownOutput();
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
}
