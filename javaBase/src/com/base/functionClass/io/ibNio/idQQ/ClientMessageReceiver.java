package com.base.functionClass.io.ibNio.idQQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMessageReceiver extends Thread {
    
    private Socket socket;
     
    public ClientMessageReceiver(Socket socket) {
        this.socket=socket;    
    }
 
    @Override
    public void run() {
        try {
            // 获取socket的输 出\入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            //接收到的消息
            String content;
            while (true) {
                if(socket.isClosed()){
                    System.out.println("Socket已关闭，无法获取消息");
                    reader.close();
                    socket.close();
                    break;
                }
                content=reader.readLine();
                if(content.equals("bye")){
                    System.out.println("对方请求关闭连接,无法继续进行聊天");
                    reader.close();
                    socket.close();
                    break;
                }
                System.out.println(content+"\n");
            }
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
}