package com.base.functionClass.socket.tcp.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 这种肯定是不行 的
 * 这种只能做到一个连接，客户端发一条，接一条。socket就没办法再打开输入输出流了，socket就需要关闭了。
 * 
 * 因为br.readLine()必须要等到 客户端输出流关闭了，才会结束，停止阻塞。当然只读一行也行。
 * 而socket.shutdownOutput()之后，就不能再打开 输出流了。这个socket只能丢弃，这肯定是我们不想看到的。
 * 所以只能采用DataInputStream  BufferedInputStream 等能够指定 输入长度的 输入流来控制
 * 而且，所有的输出流都必须 指定 输出文件的长度，才能方便 服务端读取。
 */
public class Clients {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket soc = new Socket("localhost",30000);
		while(true){
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			while((line = br.readLine())!=null){
				PrintWriter pw = new PrintWriter(soc.getOutputStream());
				BufferedReader buffr = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				System.out.println(Thread.currentThread().getName()+" : "+line);
				
				pw.write(Thread.currentThread().getName()+" : "+line);
				pw.flush();
				soc.shutdownOutput();
//				pw.close();
				
				String buffLine = "";
				while((buffLine = buffr.readLine())!=null){
					System.out.println(buffLine);
				}
				soc.shutdownInput();
			}
		}
			
		
	}
}
