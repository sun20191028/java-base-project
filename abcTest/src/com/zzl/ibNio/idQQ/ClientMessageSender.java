package com.zzl.ibNio.idQQ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientMessageSender extends Thread {
    
    private Socket socket;
 
    public ClientMessageSender(Socket socket) {
        this.socket = socket;
    }
 
    @Override
    public void run() {
        try {
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
            BufferedReader inputReader=new BufferedReader(new InputStreamReader(System.in));
            try {
                String msg;
                for(;;){
                    msg=inputReader.readLine();
                    if(msg.toLowerCase().equals("exit")){
                        System.exit(0);
                    }
                    if(socket.isClosed()){
                        System.out.println("Socket已关闭，无法发送消息");
                        writer.close();
                        socket.close();
                        break;
                    }
                    writer.write(msg);
                    writer.newLine();
                    writer.flush();
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
}