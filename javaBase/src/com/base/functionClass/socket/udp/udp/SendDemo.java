package com.base.functionClass.socket.udp.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendDemo {

	public static void main(String[] args) throws Exception {
		DatagramSocket send = new DatagramSocket(3333);
		byte[] bt = "你好我是发送方发送的数据".getBytes();
		DatagramPacket dp = new DatagramPacket(bt, bt.length,InetAddress.getByName("localhost"), 4444);
		send.send(dp);
		
		System.out.println("已发送完成");
		byte[] temp = new byte[1024];
		DatagramPacket p = new DatagramPacket(temp, temp.length);
		send.receive(p);
		
		byte[] msg = p.getData();
		System.out.println("发送方端口:" + p.getPort());
		System.out.println("发送方说:"+new String(msg));
	}
}
