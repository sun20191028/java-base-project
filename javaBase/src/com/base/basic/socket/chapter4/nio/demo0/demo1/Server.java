package com.base.basic.socket.chapter4.nio.demo0.demo1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.AbstractDocument.Content;


public class Server {
	
	private Selector selector;
	private static final int PORT = 30000;
	private Charset charset  = Charset.forName("utf-8");
	
	public void init(){
		String content = null;
		try {
			selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", PORT);
			serverSocketChannel.bind(inetSocketAddress);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while(selector.select() > 0 ){
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();	
				while(it.hasNext()){
					it.remove();
					SelectionKey sk = it.next();
					if(sk.isAcceptable()){
						SocketChannel socketChannel = serverSocketChannel.accept();
						socketChannel.register(selector, SelectionKey.OP_READ);
						sk.interestOps(SelectionKey.OP_ACCEPT);
						
					}else if(sk.isReadable()){
						SocketChannel socketChannel = (SocketChannel) sk.channel();
						ByteBuffer buff = ByteBuffer.allocate(1024);
						content = null;
						while(socketChannel.read(buff)>0){
							buff.flip();
							content += charset.decode(buff);
							
						}
						System.out.println("服务端读取的数据为 " + content);
						sk.interestOps(SelectionKey.OP_READ);
					}					
				}
				
				
				
			}
			
			if(content.length()>0){
				for (SelectionKey key : selector.keys()) {
					Channel targetChannel = key.channel();
					if(targetChannel instanceof SocketChannel){
						SocketChannel dest = (SocketChannel) targetChannel;
						dest.write(charset.encode(content));
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.init();
	}
	
	
}
