package com.base.basic.socket.chapter1.inetAddress;

import java.net.Inet4Address;
import java.net.InetAddress;

public class InetAddressDemo {
	public static void main(String[] args) throws Exception{
		InetAddress address = InetAddress.getLocalHost();
		System.out.println("主机名：" + address.getHostName() + ", ip地址：" + address.getHostAddress());
		InetAddress add = InetAddress.getByName("DESKTOP-3F6SR3B");
		System.out.println(add.getHostAddress());
		byte[] b = {(byte) 172,16,23,13};
		InetAddress a = Inet4Address.getByAddress(b);
		System.out.println(a.isReachable(1000));// 需要root权限
	}}
