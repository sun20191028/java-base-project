package com.base.functionClass.socket.tcp.demo1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketThread implements Runnable {
	public Socket s;
	public BufferedReader br = null;
	public PrintWriter pw = null;
	
	public SocketThread(Socket s) throws IOException{
		this.s=s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		pw = new PrintWriter(s.getOutputStream());
	}
	
	@Override
	public void run() {
		MyServer.threadNum++;
		System.out.println("线程号："+MyServer.threadNum);
		String msg = "";
		try {
			String str = "";
			while((str=br.readLine())!=null){
				msg = msg + str;
			}
			pw.println("服务器收到了一条消息。");
			System.out.println("服务器接收到的消息："+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			try {
				s.shutdownInput();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for (Socket so : MyServer.socketList) {
			try {
				PrintWriter pwr = new PrintWriter(so.getOutputStream());
				pwr.println(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				pw.flush();
				pw.close();
			}
		}
		
	}
	
	
}
