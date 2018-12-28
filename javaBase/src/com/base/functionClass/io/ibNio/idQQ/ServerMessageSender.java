package com.base.functionClass.io.ibNio.idQQ;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerMessageSender extends Thread{
    private Socket socket;
 
    public ServerMessageSender(Socket socket) {
        this.socket = socket;
    }
/**
 * 只发送一个欢迎信息
 */
    @Override
    public void run() {
        try {
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
//            BufferedReader inputReader=new BufferedReader(new InputStreamReader(System.in));
            try {
                String msg="server :welcome "+socket.getPort();
                writer.write(msg);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}