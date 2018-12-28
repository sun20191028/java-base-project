package com.base.basic.socket.chapter4.nio.demo2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Server {
	
	
	private ServerSocketChannel server;
	private Selector selector;
	private Charset charset = Charset.forName("utf-8");
	
	public void init() throws IOException{
		selector = Selector.open();
		
		server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(8080));
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	public void proc() throws IOException{
		
		while(selector.select() > 0 ){
			for (SelectionKey sk : selector.selectedKeys()) {
				if(sk.isAcceptable()){
					SocketChannel channel = server.accept();
					channel.configureBlocking(false);
					channel.register(selector, SelectionKey.OP_READ);
					sk.interestOps(SelectionKey.OP_ACCEPT);
					
				}else if(sk.isReadable()){
					SocketChannel channel = (SocketChannel) sk.channel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					String content = "";
					for(int num;(num = channel.read(buffer))>0;){
						buffer.flip();
						content += charset.decode(buffer);
						buffer.clear();
					}
					System.out.println("读取的数据为" + content);
					sk.interestOps(SelectionKey.OP_READ);
				}
				selector.selectedKeys().remove(sk);
				
			}
			
			
			
			
		}
		
		
	}
	
	
}
