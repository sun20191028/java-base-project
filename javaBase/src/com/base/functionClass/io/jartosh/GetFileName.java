package com.base.functionClass.io.jartosh;

import java.io.File;

public class GetFileName {
	public static void main(String[] args) {
		String path = "D:/jar";
		File file = new File(path);
		
		String[] fileName = file.list();
		
		for (int i = 0; i < fileName.length; i++) {
			
			
			System.out.println(":./lib/"+fileName[i]);
		}
		
		
	}
}
