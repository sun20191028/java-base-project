package com.base.functionClass.socket.tcp.demo1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
	public static List<Socket> socketList = Collections.synchronizedList(new ArrayList<Socket>());
	public static int threadNum = 0;
	private final int PORT = 30000;
	private ExecutorService executor = Executors.newFixedThreadPool(80);
	
	ServerSocket serverSocket ;
	
	public void init() throws IOException{
		serverSocket = new ServerSocket(PORT);
		
	}
	
	
	public void startListener() throws IOException{
		
		while(true){
			Socket socket = serverSocket.accept();
			executor.execute(new SocketThread(socket));
			
			socketList.add(socket);
		}
	}
	
}
