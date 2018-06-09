package com.base.functionClass.socket.tcp.tcp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket {

	public static void main(String[] args) throws Exception{
//		InetAddress add = InetAddress.getByName("192.168.1.109");
		InetAddress add = InetAddress.getLocalHost();
		Socket client = new Socket(add,3333);
		
//		while(true){
//			Thread.sleep(10000);
//		}
		
		
		//BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		
		PrintWriter pw = new PrintWriter(client.getOutputStream());
		pw.println("你好！我是客户端！路口附近开了罚单时空裂缝啊丢了饭卡手动阀离开啊打算离开就发大苏打附近开了阿道夫阿达放大来看");
		pw.flush();
		
		
		BufferedReader bfr = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String str = bfr.readLine();
		System.out.println("服务器说:"+str);
	}
}
