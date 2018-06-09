package com.base.functionClass.socket.tcp.demo2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {  
	  
    private ServerSocket serverSocket;  
  
  
    public void  init(){  
        try {  
            serverSocket = new ServerSocket(9999);  
            System.out.println("服务器端--开始监听");  
  
            while(true){  
                Socket socket  = serverSocket.accept();  
                String username = null;  
                //用户检测  
                if((username = checkUser(socket)) != null){  
                    ClientHandel hm = new ClientHandel(socket,username);  
                    Thread t = new Thread(hm);  
                    t.start();  
                }
  
            }  
  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 返回用户id 
     * @param socket 
     * @return 
     * @throws IOException 
     */  
    private String checkUser(Socket socket) throws IOException {  
        String b =  null;  
        byte [] content = Tool.read(socket);  
        String data [] = new String(content).split(" ");  
        String username = data[0];  
        String password = data[1];  
        b= username;  
        //这里可以连接数据库进行校验  
  
        Tool.write(socket,"true".getBytes());  
        return b;
    }  
  
}  

