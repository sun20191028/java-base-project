package com.base.functionClass.io.file;

import java.io.File;

public class FileTest {
	
	public static void main(String[] args) {
		
//		File file1 = new File("D:/jar/a/b");
//		file1.mkdirs();
		
		String path = "";
		path = "D:/jar";
		File file = new File(path);
		System.out.println(file);
//		String[] list = file.list();
//		for (String string : list) {
//			System.out.println(string);
//		}
		
//		File[] list = file.listFiles();
//		for (File file2 : list) {
//			System.out.println(file2.getName()+"--------"+file2.lastModified()+"--------"+file2.length());
//		}
//		
		
//		System.out.println("#####\r\n\r\n\r\n");
//		File[] packag = File.listRoots();
//		for (File file2 : packag) {
//			System.out.println(file2.getAbsolutePath()+"---"+file2.getName());
//			
//		}
	}
	
	
}
