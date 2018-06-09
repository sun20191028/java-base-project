package com.base.functionClass.io.file;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

public class Demo1 {

	public static void main(String[] args) {
		
		File file = new File("file_test");
		out.println(file.exists());
		out.println(file.isFile());
		out.println(file.isDirectory());
		out.println(file.canWrite());
		out.println(file.canRead());
		out.println(file.isAbsolute());
		
		File file2 = new File("file_test/filei.txt");
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File file3 = new File("file_test/fileii.txt");
		if(!file3.exists()){
			try {
				file3.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if(!file.exists()){
			file.mkdir();
			file.mkdirs();
			
		}
		
		File[] f = file.listFiles();
		String[] str = file.list();
		for (File ff : f) {
			out.println(ff.getAbsolutePath());
		}
		
		File[] roots = file.listRoots();
		for (File file4 : roots) {
			System.out.println(file4);
		}
		
//		out.println(file1.getName());
//		out.println(file1.getPath());
//		out.println(file1.getAbsoluteFile());
//		out.println(file1.getAbsolutePath());
//		out.println(file1.getParent());
//		out.println(file1.isAbsolute());
//		
//		File[] files = file1.listRoots();
//		for (File file2 : files) {
//			System.out.println(file2);
//		}
	}
	
}
