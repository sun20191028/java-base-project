package com.base.functionClass.socket.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Demo1 {
	public static void main(String[] args) throws UnknownHostException {
		
		InetAddress inet = InetAddress.getLocalHost();
		
		System.out.println(inet.getHostAddress());
		System.out.println(inet.getHostName());
		System.out.println(inet.getAddress());
		
		System.out.println("\r\n\r\n");
//		byte[] by = {19,19,19,194};
		InetAddress inet1 = InetAddress.getByName("www.baidu.com");
		
		System.out.println(inet1.getHostAddress());
		System.out.println(inet1.getHostName());
		System.out.println(inet1.getAddress());
		System.out.println("\r\n\r\n");
		byte[] by = {19,19,19,111};
		InetAddress inet2 = InetAddress.getByAddress(by);
		System.out.println(inet2.getHostAddress());
		System.out.println(inet2.getHostName());
		System.out.println(inet2.getAddress());
//		System.out.println(inet.getHostAddress());
		
		
	}
}
