package com.base.functionClass.io.iaio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.nio.cs.UnicodeEncoder;

/**
 * 字符流 & 节点流测试
 * @author liangpro
 *
 */
public class ByteCharTest {
	
	public static void main(String[] args) throws IOException {
		File file =new File("src/com/zzl/iaio/charByte.txt");
		if(!(file.exists())){
			file.createNewFile();
		}
		
		/*
		 * FileOutputStream
		 */
//		OutputStream Writer
//		InputStream	Reader
		String str="我有一只小毛驴，我从来也不骑";
		
//		FileOutputStream fos=new FileOutputStream(file);
//		byte[] b = str.getBytes();
		
//		fos.write(b,2,10);
		
////	fos.write(b);
		
//		fos.flush();
//		fos.close();
		
		
		/*
		 * FileWriter
		 */
//		FileWriter writer = new FileWriter(file);
		
//		writer.write(str);
//		writer.write(str, 2, 8);
		
//		char[] cha = str.toCharArray();
//		writer.write(cha);
//		writer.write(cha, 2, 2);//从0开始，从2号位开始，再数2个字符
		
//		writer.flush();
//		writer.close();
		
		
		/*
		 * FileInputStream
		 */
//		FileInputStream input = new FileInputStream(file);//读中文txt文档一定乱码
//		int by = input.read();
//		System.out.println(by);
		
//		int num = 0;
//		byte[] buffer = new byte[1024];
//		while((num = input.read(buffer))>0){
//			System.out.println(new String(buffer,0,num, "utf-8"));
//		}
		
		/* 
		 * FileReader
		 */
		FileReader reader = new FileReader(file);
//		int cc = '我';
//		System.out.println(cc);
//		System.out.println(Integer.toHexString(cc));
//		int r = reader.read();
//		System.out.println(r);
//		System.out.println(Integer.toHexString(r));
		int num = 0;
		char[] buff = new char[2];
		while((num = reader.read(buff))>0){
			System.out.println(new String(buff,0,num));
		}
		
		
		
	}

}
