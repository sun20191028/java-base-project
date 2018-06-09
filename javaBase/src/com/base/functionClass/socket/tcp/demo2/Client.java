package com.base.functionClass.socket.tcp.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {  
    private static final String HOST = "127.0.0.1";  
    private static final int PORT = 9999;  
    Socket socket  ;  
    Scanner in = new Scanner(System.in);  
    public void init(){  
        try {
            //登陆  
            if(login()){  
                //开启读取服务器端线程  
                new Thread(new Receive(socket)).start();  
                //一直读取控制台  
                while (true){  
                    if(in.hasNextLine()){  
                        //检测消息是否合法  
                        String temp = in.nextLine();  
                        if(temp.contains(":")){  
                            byte [] content = temp.getBytes();  
                            Tool.write(socket, content);  
                        }else {  
                            System.out.println("信息格式不对, 目标id:消息内容");  
                        }  
  
                    }  
                }  
            }  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally {  
            try {  
                socket.close();  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }  
  
    }  
  
    private boolean login() throws IOException {  
        boolean b = false;  
        System.out.println("请输入用户名密码:");  
        String username = in.next();  
        String password = in.next();  
        socket = new Socket(HOST,PORT);  
        //登陆检测  
        Tool.write(socket,(username+" "+password).getBytes());  
        String flag = new String(Tool.read(socket));  
        if( flag.equals("true")){  
            b = true;  
        }  
        in.nextLine();  
        return b;  
    }  
  
}  