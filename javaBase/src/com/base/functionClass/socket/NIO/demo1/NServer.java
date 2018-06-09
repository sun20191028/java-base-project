package com.base.functionClass.socket.NIO.demo1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 
 * @author zhiyu
 *
 */
public class NServer {
	
	
	private Selector selector = null;
	
	static final int PORT = 30000;
	static final String addr = "127.0.0.1";
	
	private Charset charset = Charset.forName("UTF-8");
	
	public void init() throws IOException{
		selector = Selector.open();
		
		ServerSocketChannel server = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(addr, PORT);
		
		server.bind(address);
		server.configureBlocking(false);
		
		server.register(selector, SelectionKey.OP_ACCEPT);
		
		while(selector.select() > 0){
			for (SelectionKey sk : selector.selectedKeys()) {
				selector.selectedKeys().remove(sk);
				if(sk.isAcceptable()){
					SocketChannel sc = server.accept();
					sc.configureBlocking(false);
//					sc.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);//也是可以的。
					sc.register(selector, SelectionKey.OP_READ);
					
					sk.interestOps(SelectionKey.OP_ACCEPT);
					
				}else if(sk.isReadable()){
					SocketChannel sc = (SocketChannel)sk.channel();
					ByteBuffer buff = ByteBuffer.allocate(1024);
					StringBuffer stb = new StringBuffer();
					while (sc.read(buff) > 0 ) {
						buff.flip();
						stb.append(charset.decode(buff));
						
					}
					if(stb.toString().trim().length()>0){
						for (SelectionKey key : selector.keys()) {
							Channel targetChannel = key.channel();
						}
					}
					
				}
				
				
			}
			
		}
		
	}
	
}
