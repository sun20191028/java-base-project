package com.base.basic.socket.chapter4.nio.demo0.demo2;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Client {

	private Selector selector = null;
	private static final int PORT = 30000;
	private Charset charset = Charset.forName("utf-8");
	private SocketChannel socketChannel = null;

	public void init() {
		try {
			selector = Selector.open();
			InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.1",PORT);
			socketChannel = SocketChannel.open(inetAddress);
			socketChannel.configureBlocking(false);
//			socketChannel.register(selector, SelectionKey.OP_READ);
			
			socketChannel.write(charset.encode("success"));
			
			ByteBuffer bb = ByteBuffer.allocate(1024);
			StringBuffer stb = new StringBuffer();
			int num = 0;
			while(true){
				while((num = socketChannel.read(bb))>0){
					bb.flip();
					stb.append(charset.decode(bb));
					bb.clear();
//					File file = new File("src\\com\\base\\basic\\socket\\chapter4\\nio\\demo0\\demo2\\file.txt");
//					Files.write(file.toPath(), stb.toString().getBytes(), StandardOpenOption.WRITE);
				}
				System.out.println(stb);
				stb.setLength(0);
				System.out.println("...");
				Thread.sleep(1000);
			}
			
			
			
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.init();
		
		
		
	}

}
