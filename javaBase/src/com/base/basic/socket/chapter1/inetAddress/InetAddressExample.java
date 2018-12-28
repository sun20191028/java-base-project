package com.base.basic.socket.chapter1.inetAddress;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InetAddressExample {
	
	public static void main(String[] args) throws SocketException {
		Enumeration<NetworkInterface> networkList = NetworkInterface.getNetworkInterfaces();
		
		while (networkList.hasMoreElements()) {
			NetworkInterface networkInterface = networkList.nextElement();
			System.out.println("Interface : " + networkInterface.getName());
			Enumeration<InetAddress> addresss = networkInterface.getInetAddresses();
			while (addresss.hasMoreElements()) {
				InetAddress inetAddress = (InetAddress) addresss.nextElement();
				System.out.println(inetAddress instanceof Inet4Address ? "Inet4Address : " : "Inet6Address :" +inetAddress.getHostAddress());
			}
			System.out.println();
		}
		
	}
	
}
