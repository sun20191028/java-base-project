package com.zzl.capathTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class ClasspathTest {
//	public void show(){
//		Class c=this.getClass();
////		ClasspathTest.getClass();//并没有，那么this不仅仅是指代当前类
	
//		c.getResource("");
//	}
	public static void main(String[] args) throws IOException {
//		File f = new File("");
//		String absolutePath = f.getAbsolutePath();
//		System.out.println(absolutePath);		//得到的就是无任何规则的路径
		File file=new File("test.txt");			//无任何路径，直接写文件，则此文件在该项目下面。当然也可以按此写绝对路径
		
//		URL url= ClasspathTest.class.getClassLoader().getResource("");
//		System.out.println(url);				//file:/D:/workspace/abcTest/bin/
//		System.out.println(url.getFile());		//也可以通过此方法获取，由此可知，URL应该是键值对的形式
//		File file=new File(url.getFile()+"test.txt");
//		String[] url1=url.toString().split("/",2);
//		System.out.println(url1[1]);
//		File file=new File(url1[1]+"test.txt");		//得到类的类路径也是可以的
		
//		File file=new File("classpath：test.txt");
		BufferedReader br=new BufferedReader(new FileReader(file));
		String st=null;
		while((st=br.readLine())!=null){
			System.out.println(st);
//			System.out.println(br.readLine()+22);	//此处可证每一行只能被读一次。
		}
		
		

	}

}
