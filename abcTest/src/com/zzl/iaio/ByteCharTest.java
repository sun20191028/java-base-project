package com.zzl.iaio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteCharTest {
	
	public static void main(String[] args) throws IOException {
		File file =new File("src/com/zzl/iaio/charByte.txt");
		if(!(file.exists())){
			file.createNewFile();
		}
		FileOutputStream fos=new FileOutputStream(file);
		String str="我有一只小毛驴，我从来也不骑";
		byte[] b=new byte[4];
//		fos.write(b);
		
		fos.flush();
		fos.close();
		
	}

}
