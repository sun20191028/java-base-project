package com.base.functionClass.socket.udp.demo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 切记这个已经没有的客户端和服务器了
 * 大家都平等，所以收发都一样
 */
public class Send {

	public static void main(String[] args) throws Exception {
		DatagramSocket send = new DatagramSocket(3333);
		byte[] bt = "你好我是发送方发送的数据".getBytes();
		DatagramPacket dp = new DatagramPacket(bt, bt.length,InetAddress.getByName("localhost"), 4444);
		send.send(dp);
		
		byte[] temp = new byte[1024];
		DatagramPacket p = new DatagramPacket(temp, temp.length);
		send.receive(p);
		
		byte[] msg = p.getData();
		System.out.println("发送方端口:" + p.getPort());
		System.out.println("发送方说:"+new String(msg));

	}
}
