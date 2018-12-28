package com.base.functionClass.socket.tcp.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
	public static void main(String[] args) throws Exception {

		ServerSocket server=new ServerSocket(3300);
		Socket sk=server.accept();
		
		BufferedReader br=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		
		String st;
		while((st=br.readLine())!=null){
			System.out.println("客户端说："+st);
		}
		
		
		PrintWriter pw1=new PrintWriter(new OutputStreamWriter(sk.getOutputStream())) ;
		pw1.println("你好！我是服务器。");
		pw1.flush();
		br.close();
		pw1.close();
	
		
	}

}
