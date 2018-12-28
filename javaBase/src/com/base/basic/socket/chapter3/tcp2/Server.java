package com.base.basic.socket.chapter3.tcp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.base.basic.socket.chapter3.tcp2.moniter.SocketThreadMoniter;

public class Server extends Thread{
	private ServerDemo demo;
	
	private SocketThreadMoniter moniter;
	
	public Server(int port){
		this.demo = new ServerDemo(port);
	}
	
	
	public void run(){
		try {
			Socket socket = demo.serverSocket.accept();
			SocketHelpThread thread = new SocketHelpThread(socket);
			thread.start();
			moniter.moniterThread(socket.getPort(), thread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public SocketThreadMoniter getMoniter() {
		return moniter;
	}


	public void setMoniter(SocketThreadMoniter moniter) {
		this.moniter = moniter;
	}



	public class ServerDemo{
		public int port = 8080;
		
		public ServerSocket serverSocket;
		
		public ServerDemo(int port){
			this.port = port;
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
