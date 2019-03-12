package com.base.basic.socket.chapter4.nio.demo1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import com.base.basic.socket.chapter4.nio.demo1.entity.SocketChannelWrapper;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;

public class ServerAcceptThread extends Thread{
	
	
	private ServerSocketChannel serverSocketChannel ;
	private Selector selector;
	private volatile boolean serverIsRun = false;
	
	public void init() throws IOException{
		selector = GlobalVariable.selector;
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(30000));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		serverIsRun = true;
	}
	
	public void run(){
		try {
			while (serverIsRun) {
				if(GlobalVariable.selector.select(2000) > 0){
					for (SelectionKey sk : selector.selectedKeys()) {
						selector.selectedKeys().remove(sk);
						doEvent(sk);
					}
					System.out.println("2s");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void doEvent(SelectionKey sk) throws IOException{
		if(sk.isAcceptable()){
			SocketChannel sc = serverSocketChannel.accept();
			
			SocketChannelWrapper wrapper = new SocketChannelWrapper();
			wrapper.channel = sc;
			sc.configureBlocking(false);
			System.out.println("站点 【" + sc.getRemoteAddress() + " : " + sc.socket().getPort() + "】上线");//ip都是本机，所以外加服务端对应端口
			GlobalVariable.mapSocketChannel.put(sc.socket().getPort(), wrapper);
//			GlobalVariable.socketChannelQueue.add(sc);//selector 可以进行并发的处理。一个线程在注册，一个线程在监听移除
			sc.register(GlobalVariable.rwSelector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
			
			sk.interestOps(SelectionKey.OP_ACCEPT);//ServerSocketChannel 对应的 SelectionKey 准备下一次接收
			
			
			
		}
	}
	
}
