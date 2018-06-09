package com.base.functionClass.socket.AIO.demo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleAIOServer {
	
	static final int PORT = 30000;
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
		
		serverChannel.bind(new InetSocketAddress(PORT));
		int i = 0;
		while(true){
			
			Future<AsynchronousSocketChannel> future = serverChannel.accept();
			
			AsynchronousSocketChannel socketChannel = future.get();
			
			socketChannel.write(ByteBuffer.wrap("欢迎来的AIO的世界".getBytes("utf-8"))).get();
			System.out.println(i);
			i++;
			
			Thread.currentThread().sleep(1000);
		}
		
		
	}
	
}
