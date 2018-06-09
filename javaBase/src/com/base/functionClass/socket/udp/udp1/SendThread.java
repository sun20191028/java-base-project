package com.base.functionClass.socket.udp.udp1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SendThread extends Thread{
	
	private DatagramSocket dataSocket;
	
	public SendThread(DatagramSocket dataSocket){
		this.dataSocket = dataSocket;
	}
	
	@Override
	public void run(){
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要发送的数据：");
		String readLine = sc.next();
		while(readLine != null){
			byte[] temp = readLine.getBytes();
			if(ReceiveDemo.adds.size() != 0){
				Set<InetAddress> addrs =  ReceiveDemo.adds.keySet();
				for (InetAddress inet : addrs) {
					DatagramPacket dp2 = new DatagramPacket(temp, temp.length,inet, (int)ReceiveDemo.adds.get(inet));
					try {
						dataSocket.send(dp2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			readLine = sc.next();
		}
		
		
	}
	
	
	
}
