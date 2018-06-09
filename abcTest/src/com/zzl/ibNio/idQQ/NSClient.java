package com.zzl.ibNio.idQQ;

import java.net.InetAddress;
import java.net.Socket;

public class NSClient {
	 // 监听端口号
    private static final int port = 9999;
    // 绑定到本机的IP地址
    private static final String bindAddr = "127.0.0.1";
 
    public static void main(String[] args) {
        try {
            System.out.println("正在连接Socket服务器");
            Socket socket=new Socket(InetAddress.getByName(bindAddr),port);
            System.out.println("已连接\n==================================");
            new ClientMessageSender(socket).start();
            new ClientMessageReceiver(socket).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }
}
