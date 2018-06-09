package com.ghj.packageofclient;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * 将GBK编码格式的文件转换为UTF-8编码格式的文件
 * 
 * 首先原文件要是GBK格式，而不能是UTF-8格式，若是utf-8，解码的时候就出错了。
 * 其次，只会复制java文件，切记不要把总结文件弄丢了。
 * 
 * 这个是基于Apache开发的jar包，开源真好。
 */
public class GBKToUTF8 {
	
	public static void main(String[] args) throws Exception {
	    
	    String gbkDirPath = "D:\\workspace\\1-1interceptDemo\\src";//GBK编码格式源码文件路径
	    String utf8DirPath = "D:\\UTF8\\src";//转为UTF-8编码格式源码文件保存路径

	    @SuppressWarnings("unchecked")
		Collection<File> gbkFileList =  FileUtils.listFiles(new File(gbkDirPath), new String[]{"java"}, true);//获取所有java文件
	    for (File gbkFile : gbkFileList) {
		    String utf8FilePath = utf8DirPath + gbkFile.getAbsolutePath().substring(gbkDirPath.length());//UTF-8编码格式文件保存路径
		    FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(gbkFile, "GBK"));//使用GBK编码格式读取文件，然后用UTF-8编码格式写入数据
	    }
	    System.out.println("success!!");
	}
}