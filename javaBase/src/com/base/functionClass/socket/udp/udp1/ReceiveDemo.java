package com.base.functionClass.socket.udp.udp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiveDemo {
	public static Map adds = new HashMap<InetAddress, Integer> ();

	public static void main(String[] args) throws Exception {
		DatagramSocket rec = new DatagramSocket(4444);
		byte[] byt = new byte[1024];
		DatagramPacket dp = new DatagramPacket(byt, byt.length);
		SendThread st = new SendThread(rec);
		st.start();
		
		do {
//			System.out.println("报盘机接收到的数据为：");
			rec.receive(dp);
			if(dp != null ){
			
				byte[] data = dp.getData();
//				System.out.println("接受的数据" + new String(data));
//				System.out.println("发送方IP地址"+ dp.getAddress());
//				System.out.println("发送方端口号:" + dp.getPort());
				
				adds.put(dp.getAddress(),dp.getPort());
			}
			
			
//			byte[] temp = ("我接受完毕，接受内容:" + new String(data)).getBytes();
//			DatagramPacket dp2 = new DatagramPacket(temp, temp.length,dp.getAddress(), dp.getPort());
//			rec.send(dp2);
			
		} while (true);
		
	}
}
