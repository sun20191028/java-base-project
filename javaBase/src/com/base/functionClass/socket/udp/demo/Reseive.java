package com.base.functionClass.socket.udp.demo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Reseive {

	public static void main(String[] args) throws Exception {
		DatagramSocket receive = new DatagramSocket(4444);
		byte[] byt = new byte[1024];
		DatagramPacket dp = new DatagramPacket(byt, byt.length);
		receive.receive(dp);
		
		byte[] data = dp.getData();
		System.out.println("接受的数据" + new String(data));
		System.out.println("发送方IP地址"+ dp.getAddress());
		System.out.println("发送方端口号:" + dp.getPort());

		byte[] temp = ("我接受完毕，接受内容:" + new String(data)).getBytes();
		DatagramPacket dp2 = new DatagramPacket(temp, temp.length,dp.getAddress(), dp.getPort());
		receive.send(dp2);
	}
}
