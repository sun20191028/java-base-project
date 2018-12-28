package com.base.functionClass.io.iaio;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;

public class Demo1 {
	
	public static void main(String[] args) throws IOException {
//		try {
//			InputStream is = new FileInputStream("Demo1.java");
//			
//			Reader read = new FileReader("Demo1.java");
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		PrintWriter pw = new PrintWriter("Demo1.java");
		String src = "从明天起，做一个幸福的人！";
		StringReader sr = new StringReader(src);
		int hasRead = 0;
		char[] buffer = new char[32];
		while((hasRead=sr.read(buffer)) > 0){
			System.out.println(new String(buffer,0,hasRead));
		}
		
//		InputStreamReader
//		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("test.txt"));
//		osw.write("从明天起，做一个幸福的人！\r\n喂马 ，劈柴，周游世界。面朝大海，春暖花开");
//		osw.flush();
		
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test.txt")));
		bw.write("从明天起，做一个幸福的人！\r\n喂马 ，劈柴，周游世界。面朝大海，春暖花开");
		bw.flush();
	}
	
}
