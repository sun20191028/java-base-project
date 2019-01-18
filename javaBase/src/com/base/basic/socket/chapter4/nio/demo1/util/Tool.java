package com.base.basic.socket.chapter4.nio.demo1.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

public class Tool {
	
	/**
	 * 往socketchannel中读数据。头4个字节表示 msg的长度
	 * @param channel
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readChannel(SocketChannel channel,Charset charset) throws IOException{
		ByteBuffer buffer0 = ByteBuffer.allocate(4);
		int num = channel.read(buffer0);
		buffer0.flip();
		
		ByteBuffer buffer1 = ByteBuffer.allocate(byteArrayToInt(buffer0.array()));
		buffer0.clear();
		num = channel.read(buffer1);
		buffer1.flip();
		return charset.decode(buffer1).toString();
	}
	
	/**
	 * 往socketchannel中写数据。头4个字节表示 msg的长度
	 * @param channel
	 * @param charset
	 * @param content
	 * @throws IOException
	 */
	public static void writeChannel(SocketChannel channel,Charset charset, String content) throws IOException{
		ByteBuffer buffer = charset.encode(content);
		byte[] by = intToByteArray1(buffer.limit());
//		channel.write(ByteBuffer.wrap(by, 0, 4));
//		channel.write(charset.encode(content));//写线程也需要支持并发，所以不能分开写
		
		ByteBuffer buffer1 = ByteBuffer.allocate(buffer.limit()+4);
		buffer1.put(by, 0, 4).put(content.getBytes());
		buffer1.flip();
		channel.write(buffer1);
	}
	
	
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

