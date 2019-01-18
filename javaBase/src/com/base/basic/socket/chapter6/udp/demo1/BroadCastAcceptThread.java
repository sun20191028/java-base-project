package com.base.basic.socket.chapter6.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadCastAcceptThread extends Thread{
	public static volatile Map adds = new HashMap<String, Integer>();
	public static String token = "secretKey";
	
	private DatagramSocket rec;
	
	public BroadCastAcceptThread(DatagramSocket rec){
		this.rec = rec;
	}
	
	public void run() {
		try {
			byte[] byt = new byte[1024];
			DatagramPacket dp = new DatagramPacket(byt, byt.length);
			while(true){
				rec.receive(dp);
				System.out.println("尝试请求数据ip :" + dp.getAddress() + ",port:" + dp.getPort() + ",data:" + (new String(dp.getData(),"utf-8")));
				String data = new String(dp.getData(),"utf-8");
				if(isPassCheck(data)){
					adds.put(dp.getAddress().getHostAddress()+":"+dp.getPort(),dp.getPort());
				}
				
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public boolean isPassCheck(String data){
		if(null!=data&&token.equalsIgnoreCase(data.trim()))//相当于用户校验
			return true;
		return false;
	}
}
