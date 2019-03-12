package com.base.basic.socket.chapter2.tcp1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientDemo {
	
	Socket socket;
	void init() throws IOException{
		socket = new Socket("127.0.0.1", 8080);
	}
	
	void send() throws IOException{
		OutputStream out = socket.getOutputStream();
		StringBuffer stb = new StringBuffer();
		for (int i = 0; i < 10000; i++) {
			stb.append(i + " : 我有一只小毛驴，我从来也不骑");
		}
		out.write(stb.toString().getBytes());
		out.flush();
		out.close();
//		socket.shutdownOutput();//
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		ClientDemo demo = new ClientDemo();
		demo.init();
		demo.send();
		Thread.currentThread().sleep(100000);
	}
}
