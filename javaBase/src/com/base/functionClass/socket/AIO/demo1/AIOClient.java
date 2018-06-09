package com.base.functionClass.socket.AIO.demo1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIOClient {
	
	final static String charset = "utf-8";
	final static int PORT = 30000;
	
	AsynchronousSocketChannel clientChannel;
	
	
	public void init(){
		
		
		
	}
	
	public void connect() throws Exception{
		
		
		final ByteBuffer buff = ByteBuffer.allocate(1024);
		
		ExecutorService executor = Executors.newFixedThreadPool(80);
		
		AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(executor);
		
		clientChannel = AsynchronousSocketChannel.open(group);
		
		clientChannel.connect(new InetSocketAddress("127.0.0.1",PORT)).get();
		
		Scanner sc = new Scanner(System.in);
		
		String content = sc.next();
		
		try {
			clientChannel.write(ByteBuffer.wrap(content.getBytes(charset))).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buff.clear();
		
		clientChannel.read(buff, null, new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				buff.flip();
				String content = StandardCharsets.UTF_8.decode(buff).toString();
				System.out.println("某人说："+content+"\n");
				buff.clear();
				clientChannel.read(buff, null, this);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("读取数据失败" + exc);
			}
			
		
		
		});
		
	}
	
	public static void main(String[] args) throws Exception {
		AIOClient client = new AIOClient();
		client.connect();
		
//		client.init();
		
		
		
	}
			
	
	
}
