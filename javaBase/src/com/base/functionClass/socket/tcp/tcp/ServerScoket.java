package com.base.functionClass.socket.tcp.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerScoket {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(3333);
		Socket sk = server.accept();
		
//		InputStream is = sk.getInputStream();
//		InputStreamReader isr = new InputStreamReader(is);
//		BufferedReader bfr = new BufferedReader(isr);
		System.out.println(sk.getInetAddress().toString());
//		BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
//		String str = bfr.readLine();
		
		
		/**
		 * 测试 输出流和输入流是互不干扰的
		 * 客户端发送了10000个字节
		 * 服务端接收 1000个字节之后，启动了输出流，
		 * 之后在从连接中读取数据时，会接着1000字节处读取，有点像队列。
		 */
		byte[] by = new byte[4];
		int b = sk.getInputStream().read(by);
		System.out.println(sk.getInetAddress().toString()+"客户端说:"+new String(by));
		
		PrintWriter bfw = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()));
		bfw.write("你好啊，我是服务器。");
		
		
		BufferedReader bfr = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		String str = bfr.readLine();
		System.out.println(sk.getInetAddress().toString()+"客户端说:"+str);
		bfw.flush();
		bfw.close();
//		bfr.close();
	}

}
