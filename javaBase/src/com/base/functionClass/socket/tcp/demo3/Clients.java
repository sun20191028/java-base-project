package com.base.functionClass.socket.tcp.demo3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import com.base.functionClass.socket.tcp.demo2.Receive;
import com.base.functionClass.socket.tcp.demo2.Tool;
import com.base.util.socket.SocketUtil;

public class Clients {

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 30000;
	private static final int connectTimeout = 2 * 60 * 1000;
	private static final int readTimeout = 1 * 60 * 1000;
	Socket socket;
	DataOutputStream dos ;
	Scanner sc = new Scanner(System.in);

	public void init() throws SocketException, IOException {
		socket = SocketUtil.createNewSocket(HOST, PORT, connectTimeout,readTimeout);
	}
	
	public void sendMsg() throws IOException{
		dos = new DataOutputStream(socket.getOutputStream());
		String str = null;
		while((str = sc.next()) != null) {
			
			byte[] sendBytes = str.getBytes("GBK");
			SocketUtil.sendLengthValue(dos, sendBytes);
		}
	}
}
