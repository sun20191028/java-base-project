package com.base.basic.socket.chapter2.tcp1;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerDemo {
	
	ServerSocket server;
	Socket socket;
	StringBuffer stb = new StringBuffer();
	
	void init() throws IOException{
		server = new ServerSocket(8080);
		socket = server.accept();
	}
	
	void read() throws IOException{
		InputStream input = socket.getInputStream();
		int num = 0;
		byte[] by  = new byte[1024];
		while((num=input.read(by))>0){
			stb.append(new String(by,0,num));
		}
		System.out.println(stb.length());
	}
	
	public static void main(String[] args) throws IOException {
		
		ServerDemo demo = new ServerDemo();
		demo.init();
		demo.read();
	}
	
}
