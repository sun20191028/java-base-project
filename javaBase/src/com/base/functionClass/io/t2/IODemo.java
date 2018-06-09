package com.base.functionClass.io.t2;

import java.io.File;
import java.util.ResourceBundle;

public class IODemo {
	public static void main(String[] args) {
		
		File file = new File("src/gess.properties");
		
		System.out.println(file.exists());
		
		File file2 = new File("../a.properties");
		
		System.out.println(file2.exists());
		
		ResourceBundle res =  ResourceBundle.getBundle("gess");
		
		System.out.println(res.getString("hello"));
		
		
	}
}
