package com.base.functionClass.socket.NIO.demo1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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
			socketChannel.register(selector, SelectionKey.OP_READ);
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				socketChannel.write(charset.encode(sc.next()));
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
