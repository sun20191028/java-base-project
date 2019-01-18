package com.base.basic.socket.chapter4.nio.demo1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;
import com.base.basic.socket.chapter4.nio.demo1.util.Tool;

public class ClientThread extends Thread{

	private Selector selector = GlobalVariable.selector;;
	private static final int PORT = 30000;
	private Charset charset = Charset.forName("utf-8");
	private SocketChannel socketChannel = null;
	private volatile boolean serverIsRun = false;

	public void init() {
		try {
			selector = Selector.open();
			serverIsRun = true;
			InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.1",PORT);
			socketChannel = SocketChannel.open(inetAddress);
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			new Thread(){
				
				public void run(){
					try {
						while(true) {
							Map<String, String> req = new HashMap<String, String>();
							req.put("name", socketChannel.socket().getPort()+"");
							req.put("msg", "this is port : " + socketChannel.socket().getPort() + " sends message!");
							Tool.writeChannel(socketChannel, charset, JSON.toJSONString(req));
							sleep(3000);
						}
						
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void run(){
		try {
			while (serverIsRun) {
				if(selector.select(2000) > 0){
					for (SelectionKey sk : selector.selectedKeys()) {
						selector.selectedKeys().remove(sk);
						String resp = Tool.readChannel((SocketChannel)sk.channel(), charset);
						sk.interestOps(SelectionKey.OP_READ);
						System.out.println(resp);
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
