package com.zzl.iaio.dataStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestDataStream {
	public static void main(String[] args) throws Exception {
		  
		  FileOutputStream fos=new FileOutputStream("data.txt");
		  BufferedOutputStream bos=new BufferedOutputStream(fos);
		  DataOutputStream dos=new DataOutputStream(bos);
		  
		  FileInputStream fis=new FileInputStream("data.txt");
		  BufferedInputStream bis=new BufferedInputStream(fis);
		  DataInputStream dis=new DataInputStream(bis);
		  
		  String str="你好hi";
		  dos.writeUTF(str);   //按UTF-8格式写入
		  dos.writeChars(str); //按字符写入
		  //按字节写入有两种方法，第一种方法只能适应无汉字的情况；
		  //因为方法1在写入时会把所有的字符都按1个字节写入，而汉字的表示需要2个字节，
		  //这就造成了数据的丢失，读入时就会出现乱码。
		  //而方法2在将字符串转换为字节数组时就把汉字字符变为了2个字节，
		  //写入文件的时候也会按2个字节的文字写入，这样读取的时候就不会出现问题
		  dos.writeBytes(str);//方法1：将整个字符串按字节写入
		  byte[] b=str.getBytes();
		  dos.write(b);  //方法2：将字符串转换为字节数组后再逐一写入
		  dos.close();
		  
		  //按UTF-8格式读取
		  System.out.println(dis.readUTF());
		  
		  //字符读取
		  char [] c=new char[4];
		  for(int i=0;i<4;i++){     
		   c[i]=dis.readChar();   //读取4个字符
		  }
		  System.out.print(new String(c,0,4));
		  System.out.println();
		  
		  //字节读取
		  byte [] b1=new byte[4];
		  dis.read(b1); //读取4个字节
		  System.out.print(new String(b1,0,4));//输出时会出现乱码 ,即：有中文时，不能按字节读取
		  System.out.println();
		  
		  byte [] b2=new byte[1024];
		  int len=dis.read(b2); //按字节读取剩余的内容
		  System.out.println(new String(b2,0,len));
		 }
}
