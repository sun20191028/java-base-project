package com.base.basic.socket.chapter4.nio.demo0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 不需要Selector ，直接使用 ServerSocketChannel（非阻塞式的） 设计通信服务
 * 当把 ServerSocketChannel 和 SocketChannel 设置成 非阻塞的时候，
 * 若不通过Selector 进行监听，怎么处理 何时进行读写的问题。
 * 采用循环 和线程休眠的方式
 * @author liangpro
 *
 */
public class NonBlockingSocketChannelServer {

	public static volatile Map<Integer,SocketChannel> keys = Collections.synchronizedMap(new HashMap<Integer, SocketChannel>());//考虑并发 移除也可以用ConcurrentHashMap
	static ConcurrentLinkedQueue<MsgWrapper> msgQueue = new ConcurrentLinkedQueue<MsgWrapper>();
	static Charset charset = Charset.forName("utf-8");
	
	
	public static void main(String[] args) {
		AcceptSocketThread acceptSocketThread = new AcceptSocketThread();
		acceptSocketThread.start();
		
		ReadMsgThread readMsgThread = new ReadMsgThread();
		readMsgThread.start();
		
		ConsumerMsgThread consumerMsgThread = new ConsumerMsgThread();
		consumerMsgThread.start();
		
		
	}
	
	static class AcceptSocketThread extends Thread {
		volatile boolean runningFlag = true;
		
		public void run(){
			try {
				ServerSocketChannel serverChannel = ServerSocketChannel.open();
				serverChannel.bind(new InetSocketAddress(30000));
				serverChannel.configureBlocking(false);
				
				while(runningFlag){
					SocketChannel channel = serverChannel.accept();
					
					if(null == channel){
						System.out.println("服务端监听中.....");
						Thread.currentThread().sleep(1000);
					}else{
						channel.configureBlocking(false);
						System.out.println("一个客户端上线，占用端口 ：" + channel.socket().getPort());
						keys.put(channel.socket().getPort(), channel);
					}
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 由于读是非阻塞式的，所以没必要一个socketChannel一个线程
	 * 也可以通过线程池来执行，此处只做实例，学习，不扩展
	 * @author liangpro
	 */
	static class ReadMsgThread extends Thread{
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		public void run(){
			try {
				int num = 0;
				for (;;) {
					Iterator<Integer> ite = keys.keySet().iterator();
					while (ite.hasNext()) {
						int key = ite.next();
						StringBuffer stb = new StringBuffer();
						try{
							while((num = keys.get(key).read(buffer)) > 0 ){
								buffer.flip();
								stb.append(charset.decode(buffer).toString());
								buffer.clear();
							}
							if(stb.length() > 0){
								MsgWrapper msg = new MsgWrapper();
								msg.key = key;
								msg.msg = stb.toString();
								System.out.println("端口：" + msg.key + "的通道,读取到的数据" + msg.msg);
								msgQueue.add(msg);
							}
						}catch(Exception e){
							System.out.println("error: 端口占用为：" + keys.get(key).socket().getPort() + ",的连接的客户端下线了");
							ite.remove();
							e.printStackTrace();//这只是个输出语句
						}
					}
					sleep(1000);
					System.out.println("读取线程监听中......");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	static class ConsumerMsgThread extends Thread{
		public volatile boolean isRunningFlag = false;
		
		public void run (){
			isRunningFlag = true;
			try {
//				sleep(10000);
				while (isRunningFlag) {
					MsgWrapper msg = msgQueue.poll();
					for (; null != msg; msg = msgQueue.poll()) {
						SocketChannel channel = keys.get(msg.key);
						channel.write(charset.encode("response:" + msg.msg));
					}
					sleep(1000);
					System.out.println("响应线程响应中......");
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static class MsgWrapper {
		public int key;
		public String msg;
	}
}
