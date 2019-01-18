package com.base.functionClass.socket.tcp.demo2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.alibaba.fastjson.JSON;

public class Tool {  
    public static byte[] read(Socket socket) throws IOException {  
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());  
        //包头  
        byte[] head = new byte[4];  
        bis.read(head);  
        byte[] data = new byte[Tool.byteArrayToInt(head)];  
        //包体  
        bis.read(data);  
        return data;  
  
    }  
    public static void write(Socket socket, byte[] content) throws IOException {  
        //包头,固定4个字节,包含包体长度信息  
        byte [] head = Tool.intToByteArray1(content.length);  
        BufferedOutputStream bis = new BufferedOutputStream(socket.getOutputStream());  
        bis.write(head);  
        bis.flush();  
        //包体  
        bis.write(content);  
        bis.flush();  
    }  
    //int 转字节数组 
    public static byte[] intToByteArray1(int i) {  
        byte[] result = new byte[4];  
        result[0] = (byte)((i >> 24) & 0xFF);  
        result[1] = (byte)((i >> 16) & 0xFF);  
        result[2] = (byte)((i >> 8) & 0xFF);  
        result[3] = (byte)(i & 0xFF);// 8位保存  两个 16进制树
        return result;  
    }  
  
    public static byte[] intToByteArray2(int i) throws Exception {  
        ByteArrayOutputStream buf = new ByteArrayOutputStream();  
        DataOutputStream out = new DataOutputStream(buf);  
        out.writeInt(i);  
        byte[] b = buf.toByteArray();  
        out.close();  
        buf.close();  
        return b;  
    }  
  
    //字节数组转int  
    public static int byteArrayToInt(byte[] b) {  
        int intValue=0;  
        for(int i=0;i<b.length;i++){  
            intValue +=(b[i] & 0xFF)<<(8*(3-i));  
        }  
        return intValue;  
    }
    
    
    public static void main(String[] args) throws Exception {
		byte[] by1 = intToByteArray1(156);
		byte[] by2 = intToByteArray2(156);
		
		System.out.println(JSON.toJSONString(by1));
		System.out.println(JSON.toJSONString(by2));
		
		System.out.println(byteArrayToInt(by1));
	}
}  

