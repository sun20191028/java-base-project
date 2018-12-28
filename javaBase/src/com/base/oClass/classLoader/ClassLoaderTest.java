package com.base.oClass.classLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class ClassLoaderTest {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
//		ClassLoader cl=ClassLoader.getSystemClassLoader();
//		cl.loadClass("Testera ");
//		System.out.println("aaa");
		/**
		 * 根类加载器
		 */
//		URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
//		for (int i = 0; i < urls.length; i++) {
//			System.out.println(urls[i].toExternalForm());
//		}
//		
//		ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
//		System.out.println(systemLoader);
//		
//		Enumeration<URL> eml = systemLoader.getResources("");
//		while (eml.hasMoreElements()) {
//			System.out.println(eml.nextElement()); 
//		}
//		
//		ClassLoader extensionLoader = systemLoader.getParent();
//		System.err.println("扩展类加载器：" + extensionLoader);
//		System.out.println("扩展类加载器的加载路径：" + System.getProperty("java.ext.dirs"));
//		System.out.println("扩展类加载器的parent:" + extensionLoader.getParent());
		
		
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		System.out.println("系统类装载器:" + systemClassLoader);
//		Launcher$AppClassLoader
		ClassLoader extClassLoader = systemClassLoader.getParent();
		System.out.println("系统类装载器的父类加载器——扩展类加载器:" + extClassLoader);
//		Launcher$extClassLoader
//		Launcher
		ClassLoader bootClassLoader = extClassLoader.getParent();
		System.out.println("扩展类加载器的父类加载器——引导类加载器:" + bootClassLoader);
	}
}
