package com.base.functionClass.socket.tcp.demo2;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandel implements Runnable {  
    private String username;  
    private static HashMap<String, Socket> clientSocket = new HashMap<String, Socket>();  
    public static int count = 0;  
    Socket socket = null;  
  
    public ClientHandel(Socket socket, String username) {  
        this.username = username;  
        count++;
        this.socket = socket;  
        clientSocket.put(username, socket);  
        System.out.println("用户" + count + "接入");  
    }  
  
    @Override  
    public void run() {  
        try {  
            while (true) {  
                //读取客户端内容  
                byte[] data = Tool.read(socket);  
                //解析目标线程的key  
                String key = getKey(data);  
  
                System.out.println(new String(data));  
                if (data.length > 1 && key != null) {  
                    //传递给指定线程  
                    Tool.write(clientSocket.get(key), data);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                socket.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    private String getKey(byte[] data) throws IOException {  
        String key = new String(data).split(":")[0];  
        if (!clientSocket.containsKey(key)) {  
            Tool.write(socket, "该用户不在线".getBytes());  
            return null;  
        }  
        return key;  
    }  
}  