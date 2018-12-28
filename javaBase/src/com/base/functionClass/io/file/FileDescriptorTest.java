package com.base.functionClass.io.file;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDescriptorTest {
	
	public static void main(String[] args) {
		
		try {
		    FileOutputStream out = new FileOutputStream(FileDescriptor.out);
		    out.write('A');
		    out.close();
		} catch (IOException e) {
			
		}
		
		
	}
	
	
	
}
