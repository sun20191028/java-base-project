package com.base.basic.socket.chapter4.nio.demo0.demo2;

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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 测试并发写 ，对通道的影响
 * 通道的写支持并发，因为里面加锁了。
 * 
 * 虽然write单个 包的时候，是线程安全的，但是对于单个包比较大的时候，需要拆分传输的时候，依然会出现线程不安全的问题
 * 多个线程之间
 * 
 * 
 * @author zhiyu
 */
public class NServer {
	
	
	static final int PORT = 30000;
	static final String addr = "127.0.0.1";
	private volatile Set<SocketChannel> set = Collections.synchronizedSet(new HashSet<SocketChannel>());
	private Charset charset = Charset.forName("UTF-8");
	
	public void init() throws IOException, InterruptedException{
		
		ServerSocketChannel server = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(addr, PORT);
		server.bind(address);
		server.configureBlocking(false);
		
		
		while(true){
			SocketChannel sc = server.accept();
			if(null != sc){
				set.add(sc);
			}
			Thread.sleep(1000);
		}
		
	}
	
	
	public void doResp(){
		
		RespThread t1 = new RespThread("abcdefghijklmnopqrstu");
		RespThread t2 = new RespThread("12345678901234567890");
		RespThread t3 = new RespThread("##########################");
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		NServer server = new NServer();
		server.doResp();
		
		server.init();
		
	}
	
	public class RespThread extends Thread{
		private byte[] msgs;
		public RespThread(String msg){
			this.msgs = msg.getBytes();
		}
		
		public void run(){
			try {
				ByteBuffer bb = ByteBuffer.allocate(msgs.length);
				bb.put(msgs);
				for(;;){
					for (SocketChannel sc : set) {
//						System.out.println(sc.socket().getPort());
						bb.flip();
						sc.write(bb);
						ByteBuffer bf = ByteBuffer.allocate(1024);
						bf.put((" [ " + getName() + " ] ").getBytes());
						bf.flip();
						sc.write(bf);//此write与上一个write 会出现线程不安全的问题， 导致传输的数据不连续
					}
					sleep(1000);
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
