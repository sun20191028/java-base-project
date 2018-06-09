package com.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * 只支持文件转码，不能够完成对文件夹--项目的转码。
 */
public class GbkToUTF {
	public static String DefaultSrcEncodeFormat = "GBK";
	public static String DefaultDestEncodeFormat = "UTF-8";
	public static String UnsupportedEncodingExceptionError = "文件不支持转码";
	public static String FileNotFoundExceptionError = "找不到文件";
	public static String IOExceptionError = "io异常";
	public static String IsUtf8File = "文件编码格式是TF-8";
	public static String IsNotUtf8File = "文件编码格式不是TF-8";

	public static void main(String args[]) throws Exception {
		// String path1 = args[0];
		System.out.println("================");
		transfer("E:/erong/Test.html", "E:/new.html", "GBK", "UTF-8");
	}

	public static String transfer(String context, String encodeFormat) {
		if (encodeFormat == null || encodeFormat.equals(""))
			encodeFormat = DefaultDestEncodeFormat;

		try {
			byte[] content = context.getBytes();

			String result = new String(content, encodeFormat);
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(UnsupportedEncodingExceptionError);
			e.printStackTrace();
		}

		return "";
	}

	public static void transfer(String srcPath, String destPath,
			String destEncode) throws Exception {
		if (true != isUTF8File(srcPath)) {
			transfer(srcPath, destPath, DefaultSrcEncodeFormat, destEncode);
		}
		transfer(srcPath, destPath, DefaultSrcEncodeFormat, destEncode);
	}

	public static void transfer(String srcPath, String destPath,
			String srcEncode, String destEncode) throws Exception {
		if (destPath == null || destPath.equals(""))
			destPath = srcPath;

		String context = readFile(srcPath, srcEncode);

		context = transfer(context, destEncode);
		writeFile(context, destPath, destEncode);
	}

	public static String readFile(String path, String encodeFormat)
			throws Exception {
		if ((encodeFormat == null || encodeFormat.equals(""))) {
			if (isUTF8File(path)) {
				encodeFormat = DefaultDestEncodeFormat;
			} else {
				encodeFormat = DefaultSrcEncodeFormat;
			}
		}

		try {
			String context = "";
			InputStreamReader isr;
			isr = new InputStreamReader(new FileInputStream(path), encodeFormat);
			BufferedReader br = new BufferedReader(isr);
			String line;

			while ((line = br.readLine()) != null) {
				context += line + "\r\n";
				System.out.println(line);
			}

			br.close();

			return context;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(UnsupportedEncodingExceptionError);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(FileNotFoundExceptionError);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(IOExceptionError);
			e.printStackTrace();
		}
		;

		return "";
	}

	public static void writeFile(String context, String path, String destEncode) {
		File file = new File(path);
		if (file.exists())
			file.delete();
		BufferedWriter writer;

		try {
			FileOutputStream fos = new FileOutputStream(path, true);
			writer = new BufferedWriter(new OutputStreamWriter(fos, destEncode));
			writer.append(context);
			writer.close();
		} catch (IOException e) {
			System.out.println(IOExceptionError);
			e.printStackTrace();
		}
	}

	public static void writeFile(String context, String path) {
		File file = new File(path);
		if (file.exists())
			file.delete();
		Writer writer;

		try {
			writer = new FileWriter(file, true);
			writer.append(context);
			writer.close();
		} catch (IOException e) {
			System.out.println(IOExceptionError);
			e.printStackTrace();
		}
	}

	public static boolean isUTF8File(String path) throws Exception {
		try {
			File file = new File(path);
			String charset = JudgeEncoding.getCodeString(file);

			if (charset.equalsIgnoreCase(DefaultDestEncodeFormat)) {
				System.out.println(IsUtf8File);
				return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(FileNotFoundExceptionError);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(IOExceptionError);
		}

		System.out.println(IsNotUtf8File);
		return false;
	}

}
