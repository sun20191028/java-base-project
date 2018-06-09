package com.zzl.gnet.TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
	public static void main(String[] args) throws Exception {

		InetAddress id=InetAddress.getLocalHost();
		String localname=id.getHostName();
		String localid=id.getHostAddress();
		System.out.println(id.toString());
		Socket client=new Socket(localid,3300);   //此id，应为服务器id，但此处由于服务器是本机。
		
		//BufferedWriter bfw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		PrintWriter pw=new PrintWriter(client.getOutputStream());
//		BufferedWriter pw =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		pw.println("你好啊。我是张三丰\r\ndhf大力开发费爱咖啡双方的答复");
		
		pw.flush();
		/**
		 * 客户端写入完毕后，socket调用一个方法，告知服务器，已经输出完毕。
		 * 否则服务器的读循环不会跳出来。
		 */
		client.shutdownOutput();
		
		
		BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
		String s=br.readLine();
		System.out.println("服务器说："+s);
		pw.close();
		br.close();
	}
}
