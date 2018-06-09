package com.base.functionClass.socket.tcp.demo2;

import java.net.Socket;

public class Receive implements Runnable {  
    Socket socket = null;  
    public Receive(Socket socket){  
        this.socket = socket;  
    }  
  
    public void run() {  
        try {  
            while(true){  
                String data = new String(Tool.read(socket));  
                if(data.contains(":")){  
                    System.out.println(data.split(":")[1]);  
                }else {  
                    System.out.println(data);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  