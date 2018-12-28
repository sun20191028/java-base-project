package com.base.functionClass.io.ibNio.idQQ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NSServer {
    private static int port =9999;
    // 可接受请求队列的最大长度
    private static int backlog=100;
    // 绑定到本机的IP地址
    private static final String bindAddr = "127.0.0.1";
    //socket字典列表
    private static List<Socket> nodes= new ArrayList<Socket>();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(port, backlog,InetAddress.getByName(bindAddr));
            for(;;){
                //发生阻塞,等待客户端连接            
                Socket sc = ss.accept();
                nodes.add(sc);
                InetAddress addr = sc.getLocalAddress();
                System.out.println("create new session from "+addr.getHostName()+":"+sc.getPort()+"\n");            
               //针对一个Socket 客户端 启动两个线程，分别是接收信息，发送信息
                new Thread(new ServerMessageReceiver(sc,nodes)).start();
                new ServerMessageSender(sc).start();
            }            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
