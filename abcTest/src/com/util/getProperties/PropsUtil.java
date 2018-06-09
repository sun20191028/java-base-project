package com.util.getProperties;

import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropsUtil {
	public static void main(String[] args) {
		PropsUtil pu=new PropsUtil();
		pu.getProperties1();
	}
	
	/**
	 * 我的resourceBundle
	 */
	public void getProperties1(){
//		ResourceBundle pro=ResourceBundle.getBundle("mysrc");//默认在src文件下
//		System.out.println(pro.getString("DB_TEST_SQL"));
		ResourceBundle pro=ResourceBundle.getBundle("com.myServer");//绝对路径。
		System.out.println(pro.getString("DB_URL_HIS"));
		
//		ResourceBundle pro=ResourceBundle.getBundle("E:/my.properties");//暂时不能读取D盘路径
//		System.out.println(pro.getString("DB_URL_HIS"));
	}
	
}
