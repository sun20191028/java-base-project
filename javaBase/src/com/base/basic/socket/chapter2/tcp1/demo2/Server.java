package com.base.basic.socket.chapter2.tcp1.demo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(8080);
		
		Socket socket = serverSocket.accept();
		InputStream in = socket.getInputStream();
		byte[] by = new byte[1024];
		int num = 0;
		while((num = in.read(by)) > 0){
			System.out.println(new String(by,"utf-8"));
		}
	}
	
}
