package com.zzl.gnet.zaInetAddress;

import java.net.InetAddress;

public class InetAddressDemo {
	public static void main(String[] args) throws Exception{
		InetAddress address = InetAddress.getLocalHost();
		System.out.println("主机名：" + address.getHostName() + ", ip地址："
		        + address.getHostAddress());
		InetAddress add = InetAddress.getByName("BOPZKQZ9SSY5ECY");
		System.out.println(add.getHostAddress());
	}
}
