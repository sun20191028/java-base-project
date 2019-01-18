package com.base.basic.socket.chapter4.nio.demo0;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NonBlockingSocketChannelClient {
	
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			
			
			new Thread(){
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				
				public void run(){
					
					try {
						SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",30000));
						socketChannel.configureBlocking(false);
						socketChannel.write(NonBlockingSocketChannelServer.charset.encode(socketChannel.socket().getPort() + ": send message"));
						int num = 0;
						StringBuffer stb = new StringBuffer();
						for (;;) {
							while(socketChannel.read(buffer) > 0){
								buffer.flip();
								stb.append(NonBlockingSocketChannelServer.charset.decode(buffer));
								buffer.clear();
							}
							if(stb.length()>0){
								break;
							}
							sleep(1000);
						}
						System.out.println(stb);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("over!");
				}
			}.start();
		}
	}
}
