package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class JudgeEncoding {
	private static String code = "GBK";

	public static String getCodeString(File file) throws Exception {
		InputStream in = new FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65)
			code = "UTF-8";
		else {
			code = "GBK";
		}
		return code;
	}
}
