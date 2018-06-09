package com.base.functionClass.socket.tcp.demo3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.base.util.socket.SocketUtil;

public class ServerSocketWorkThread implements Runnable{
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public ServerSocketWorkThread(Socket socket) throws IOException{
		this.socket = socket;
//		this.dis = new DataInputStream(socket.getInputStream());
//		this.dos = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			while(true){
//				SocketAddress
				byte[] iByte = SocketUtil.readLengthValue(dis);
				System.out.println(new String(iByte, "GBK"));
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
	}
	
	
}
