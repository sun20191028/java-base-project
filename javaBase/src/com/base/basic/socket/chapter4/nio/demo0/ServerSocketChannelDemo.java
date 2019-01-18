package com.base.basic.socket.chapter4.nio.demo0;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
/**
 * 不需要Selector ，直接使用 ServerSocketChannel（阻塞式的） 设计通信服务
 */
public class ServerSocketChannelDemo {

	public static Charset charset = Charset.forName("utf-8");
	
	/**
	 * serverSocketChannel 和 socketChannel 都是阻塞式的。
	 * 所以 serverSocketChannel 的accept和socketChannel 的read 都回造成阻塞
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(30000));
//			serverSocketChannel.configureBlocking(false);
			
			SocketChannel socketChannel = serverSocketChannel.accept();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			socketChannel.read(buffer);
			
			System.out.println(charset.decode(buffer));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
