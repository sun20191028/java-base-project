package com.base.basic.socket.chapter3.tcp2;

import com.base.basic.socket.chapter3.tcp2.moniter.ReceiveDataMoniter;
import com.base.basic.socket.chapter3.tcp2.moniter.SocketThreadMoniter;

public class ServerStart {

	public static void main(String[] args) {
		SocketThreadMoniter moniter = new SocketThreadMoniter();
		moniter.start();
		
		ReceiveDataMoniter receiverMoniter = new ReceiveDataMoniter();
		receiverMoniter.start();
		
		Server server = new Server(8080);
		server.setMoniter(moniter);
		
		server.start();
	}
	
	
}
