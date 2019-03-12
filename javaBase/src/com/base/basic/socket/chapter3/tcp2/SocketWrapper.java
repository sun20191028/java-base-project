package com.base.basic.socket.chapter3.tcp2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapper{
		public byte[] by = new byte[1024];
		
		public Socket socket;
		
		public InputStream in;
		
		public OutputStream out;
		
		public SocketWrapper(Socket socket){
			this.socket = socket;
			try {
				in = socket.getInputStream();
				out = socket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}