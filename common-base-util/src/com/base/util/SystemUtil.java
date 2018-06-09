package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

public class SystemUtil {
	
	public static void println(String str){
		str = str + "\r\n";
		print(str);
	}
	
	public static void print(String str){
		String path = "system.txt";
		write(str, path, true);
	}
	
	
	private static void write(String str,String path,boolean isAppend){
		FileOutputStream out  = null;
		FileChannel outChannel  = null;
		try{
			out  = new FileOutputStream(new File(path),isAppend);
			outChannel = out.getChannel();
			ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
			outChannel.write(buffer);
			outChannel.notify();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(outChannel != null){
				try {
					outChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
