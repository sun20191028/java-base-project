package com.base.functionClass.socket.AIO.demo1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AIOServer {
	
	static int PORT = 30000;
	final static String charset = "utf-8";
	public static List<AsynchronousSocketChannel> channelList = new ArrayList<>();
	
	public void startListen() throws IOException{
		ExecutorService executor = Executors.newFixedThreadPool(20);
		
		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
		
		AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress(PORT));
		
		serverChannel.accept(null ,new AcceptHandler(serverChannel));
		
		
	}
	
	public static void main(String[] args) throws IOException {
		AIOServer server = new AIOServer();
		server.startListen();
		while (true) {
			try {
//				System.out.println(System.currentTimeMillis());
				Thread.sleep(5*1000);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		}
		
	}
	
	
}
class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {
	private AsynchronousServerSocketChannel serverChannel;
	private ByteBuffer buffer = ByteBuffer.allocate(1024);
	
	public AcceptHandler(AsynchronousServerSocketChannel sc){
		this.serverChannel = sc;
	}
	
	@Override
	public void completed(final AsynchronousSocketChannel sc, Object attachment) {
		AIOServer.channelList.add(sc);
		serverChannel.accept(null, this);
		sc.read(buffer, null, new CompletionHandler<Integer, Object>() {
			@Override
			public void completed(Integer result, Object attachment) {
				buffer.flip();
				
				String content = StandardCharsets.UTF_8.decode(buffer).toString();
				try {
					System.out.println(sc.getLocalAddress()+" 说："+content);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (AsynchronousSocketChannel channel : AIOServer.channelList) {
					try {
						channel.write(ByteBuffer.wrap(content.getBytes(AIOServer.charset))).get();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				buffer.clear();
				sc.read(buffer, null, this);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("读取数据失败："+exc);
				AIOServer.channelList.remove(sc);
			}
		});
			
		
		
		
	}

	@Override
	public void failed(Throwable exc, Object attachment) {
		System.out.println("连接失败：" + exc );
	}
}