package com.zzl.ibNio.icSocketChannel;

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


public class NServer {
	private Selector selector=null;
	static final int PORT=3000;
	private Charset charset=Charset.forName("utf-8");
	public void init () throws IOException {
		selector=Selector.open();
		ServerSocketChannel server=ServerSocketChannel.open();
		ServerSocket serverSocket = server.socket();
		serverSocket.bind(new InetSocketAddress(PORT));
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select()>0){  //如果有需要处理的IO操作时，该方法返回（此处为阻塞模式） 并返回这些Channel的数量。并将对于的Channel的SelectionKey加入被选中的SelectionKey集合中。
			for(SelectionKey sk:selector.selectedKeys()){
				selector.selectedKeys().remove(sk);
				if(sk.isAcceptable()){
					SocketChannel sc=server.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_ACCEPT);
					sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				if(sk.isReadable()){
					SocketChannel sc=(SocketChannel) sk.channel();
					ByteBuffer buff=ByteBuffer.allocate(1024);
					String content="";
					try {
						while(sc.read(buff)>0){
							buff.flip();
							content+=charset.decode(buff);
							buff.clear();
						}
						System.out.println("读取的数据为："+content);
						sk.interestOps(SelectionKey.OP_ACCEPT);
					} catch (IOException e) {
						sk.cancel();
						if(sk.channel()!=null){
							sk.channel().close();
						}
					}
					if(content.length()>0){
						for (SelectionKey key : selector.keys()) {
							Channel targetChannel=key.channel();
							if(targetChannel instanceof SocketChannel){
								SocketChannel dest=(SocketChannel) targetChannel;
								dest.write(charset.encode(content));
							}
						}
					}
				}
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		new NServer().init();
	}	
}
