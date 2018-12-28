package com.base.basic.socket.chapter3.tcp2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHelpThread extends Thread{
	private SocketWrapper socketWrapper;
	
	public SocketHelpThread(Socket socket){
		this.socketWrapper = new SocketWrapper(socket);
	}
	
	public void run(){
		int num = 0;
		try {
			for(;;){
				StringBuffer buffer = new StringBuffer();
				while((num = socketWrapper.in.read(socketWrapper.by)) > 0){
					buffer.append(new String(socketWrapper.by,0,num));
				}
				GlobalVariable.serverDuty.put(socketWrapper.socket.getPort(),buffer.toString());
				socketWrapper.out.write("success".getBytes());
				socketWrapper.out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
