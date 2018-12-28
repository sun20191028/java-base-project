package com.base.functionClass.io.iaio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileOutputStreamTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		File file1 = new File("src/com/base/functionClass/io/io/IoTest.java");
		File file2 = new File("src/com/base/functionClass/io/io/newFile.txt");
		if(!file1.exists()){
			System.out.println("file1 is not exist! ");
		}
		System.out.println(file1.getAbsolutePath());
		if(!file2.exists()){
			file2.createNewFile();
		}
		
		FileInputStream fis = new FileInputStream(file1);
//		fis.wait(1*60*1000l);
		FileOutputStream fos = new FileOutputStream(file2);
		FileChannel chanel = fis.getChannel();
		byte[] bbuf = new byte[1024];
		int hasRead = 0;
		while((hasRead = fis.read(bbuf))>0){
			fos.write(bbuf,0,hasRead);
		}
		bbuf =  new byte[1024];
		int i1 = fis.read(bbuf);
		System.out.println(new String(bbuf,0,i1)+","+i1);
		fis.read(bbuf);
		System.out.println(bbuf);
		fis.read(bbuf);
		System.out.println(bbuf);
		fis.close();
		fos.close();
		System.out.println("over !!!!");
	}
	
	
	
	
}
