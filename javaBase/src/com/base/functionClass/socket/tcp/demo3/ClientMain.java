package com.base.functionClass.socket.tcp.demo3;

import java.io.IOException;
import java.net.SocketException;

public class ClientMain {

	public static void main(String[] args) throws SocketException, IOException {
		Clients client = new Clients();
		client.init();
		client.sendMsg();
		
	}
}
