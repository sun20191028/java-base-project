package com.base.basic.socket.chapter6.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientThread extends Thread{

	private int port;
	
	public ClientThread(int port){
		this.port = port;
	}
	
	public void run(){
		try {
			DatagramSocket dataSocket = new DatagramSocket(port);
			byte[] bt = "secretKey".getBytes();
			DatagramPacket dp = new DatagramPacket(bt, bt.length,InetAddress.getByName("localhost"), 3333);
			dataSocket.send(dp);
			
			byte[] temp = new byte[1024];
			DatagramPacket p = new DatagramPacket(temp, temp.length);
			do{
				dataSocket.receive(p);
				if(p != null){
					byte[] msg = p.getData();
					System.out.println(Thread.currentThread().getName() +" 接收到的广播的数据 : "+new String(msg));
				}
				
			}while(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	}
}
