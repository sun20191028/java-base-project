package com.base.functionClass.socket.udp.udp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendDemo {

	public static void main(String[] args) throws Exception {
//		DatagramSocket dataSocket = new DatagramSocket(3333);
		DatagramSocket dataSocket = new DatagramSocket(5555);
		byte[] bt = "你好!我需要请求数据。".getBytes();
		DatagramPacket dp = new DatagramPacket(bt, bt.length,InetAddress.getByName("localhost"), 4444);
		dataSocket.send(dp);
		
		byte[] temp = new byte[1024];
		DatagramPacket p = new DatagramPacket(temp, temp.length);
		do{
			
			dataSocket.receive(p);      
			
			
			
			if(p != null){
				byte[] msg = p.getData();
				System.out.println("发送方端口:" + p.getPort());
				System.out.println("发送方说:"+new String(msg));
			}
			
		}while(true);
	}
}
