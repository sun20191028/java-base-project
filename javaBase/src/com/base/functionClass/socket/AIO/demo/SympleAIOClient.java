package com.base.functionClass.socket.AIO.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

public class SympleAIOClient {

	static final int PORT = 30000;
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		
		ByteBuffer buff = ByteBuffer.allocate(1024);
		Charset charset = Charset.forName("utf-8");
		
		AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
		
		socketChannel.connect(new InetSocketAddress("127.0.0.1", PORT)).get();
		
		buff.clear();
		socketChannel.read(buff).get();
		buff.flip();
		
		String content = charset.decode(buff).toString();
		
		System.out.println("服务器响应的信息为："+content);
		
		
	}
	
}
