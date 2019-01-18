package com.base.basic.socket.chapter4.nio.demo1;

import java.io.IOException;

import com.base.basic.socket.chapter4.nio.demo1.server.ConsumerMsgThread;
import com.base.basic.socket.chapter4.nio.demo1.server.ServerAccept;
import com.base.basic.socket.chapter4.nio.demo1.server.ServerReadWrite;

public class BootstrapServer {

	public static void main(String[] args) throws IOException {
		
		ServerAccept accept = new ServerAccept();
		accept.init();
		accept.start();
		
		ServerReadWrite rw = new ServerReadWrite();
		rw.init();
		rw.start();
		
		ConsumerMsgThread consumer = new ConsumerMsgThread();
		consumer.start();
		
		
		
		
	}
	
	
}
