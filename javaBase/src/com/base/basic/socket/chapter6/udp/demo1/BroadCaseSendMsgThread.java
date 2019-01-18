package com.base.basic.socket.chapter6.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.base.basic.socket.chapter1.inetAddress.InetAddressDemo;

public class BroadCaseSendMsgThread extends Thread{
	
	private DatagramSocket dataSocket;
	
	public BroadCaseSendMsgThread(DatagramSocket dataSocket){
		this.dataSocket = dataSocket;
	}
	
	@Override
	public void run(){
		try {
			while(true){
				String msg = GlobalVariable.serverDuty.take();
				byte[] temp = msg.getBytes();
				Set<String> addrs =  BroadCastAcceptThread.adds.keySet();
				if(null == addrs ||addrs.isEmpty())//没有接收端，则不发送
					continue;
				String logs = "";
				for (String inet : addrs) {
					DatagramPacket dp2 = new DatagramPacket(temp, temp.length,getInetAddress(inet.split(":")[0]), (int)BroadCastAcceptThread.adds.get(inet));
					logs = logs + "[" + inet + ":" + BroadCastAcceptThread.adds.get(inet) + "],";
					try {
						dataSocket.send(dp2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("接收广播的服务端有：" + logs);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public InetAddress getInetAddress(String ip){
		try {
			String[] strs = ip.split("\\.");
			byte[] by = new byte[strs.length];
			for (int i = 0; i < strs.length; i++) {
				by[i] = Byte.parseByte(strs[i]);
			}
			return InetAddress.getByAddress(by);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
