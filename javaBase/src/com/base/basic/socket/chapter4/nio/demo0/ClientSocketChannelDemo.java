package com.base.basic.socket.chapter4.nio.demo0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientSocketChannelDemo {
	
	public static void main(String[] args) throws IOException, Exception {
		
		SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",30000));
		
		Thread.currentThread().sleep(100000);//不要让客户端关闭了socket连接
	}
	
}
