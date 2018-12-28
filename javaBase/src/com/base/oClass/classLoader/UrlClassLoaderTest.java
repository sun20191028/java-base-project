package com.base.oClass.classLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import sun.applet.AppletClassLoader;

public class UrlClassLoaderTest {
	
	private static Connection conn;
	
	public static Connection getconn(String url,String user,String pass) throws Exception{
		if(null == conn){
			URL[] urls = {new URL("file:mysql-connector-java-5.1.7-bin.jar")};
			URLClassLoader urlClassLoader = new URLClassLoader(urls);
			Driver driver = (Driver)urlClassLoader.loadClass("com.mysql.jdbc.Driver").newInstance();
			Properties props = new Properties();
			props.setProperty("user", user);
			props.setProperty("password", pass);
			conn = driver.connect(url, props);
		}
		return conn;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getconn("jdbc:mysql://localhost:3306/mysql", "root", ""));
		
		
//		AppletClassLoader
	}
	
	
}
