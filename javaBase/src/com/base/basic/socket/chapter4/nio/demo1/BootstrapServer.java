package com.base.basic.socket.chapter4.nio.demo1;

import java.io.IOException;

import com.base.basic.socket.chapter4.nio.demo1.listener.ServerListener;
import com.base.basic.socket.chapter4.nio.demo1.server.ConsumerMsgThread;
import com.base.basic.socket.chapter4.nio.demo1.server.ServerAcceptThread;
import com.base.basic.socket.chapter4.nio.demo1.server.ServerReadWrite;

public class BootstrapServer {

	public static void main(String[] args) throws IOException {
		
		ServerAcceptThread accept = new ServerAcceptThread();
		accept.init();
		accept.start();
		
		ServerReadWrite rw = new ServerReadWrite();
		rw.init();
		rw.start();
		
		ConsumerMsgThread consumer = new ConsumerMsgThread();
		consumer.start();
		
		ServerListener listener = new ServerListener();
		listener.start();
		
		
	}
	
	
}
