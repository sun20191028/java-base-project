package com.base.basic.socket.chapter3.tcp2;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	
	public SocketWrapper socketWrapper;
	
	public void init() {
		try {
			socketWrapper = new SocketWrapper(new Socket("127.0.0.1",8080));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void run(){
		try {
//			for(;;){
				int num = 0;
				socketWrapper.out.write((socketWrapper.socket.getLocalPort() + " : 发送数据成功").getBytes());
				socketWrapper.out.flush();
				socketWrapper.socket.shutdownOutput();
				StringBuffer buffer = new StringBuffer();
				while((num = socketWrapper.in.read(socketWrapper.by)) > 0){
					buffer.append(new String(socketWrapper.by,0,num));
				}
				GlobalVariable.clientDuty.add(buffer.toString());
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
