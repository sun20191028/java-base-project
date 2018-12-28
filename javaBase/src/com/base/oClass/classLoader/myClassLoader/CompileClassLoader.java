package com.base.oClass.classLoader.myClassLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CompileClassLoader extends ClassLoader{
	
	private byte[] getBytes(String fileName) throws Exception{
		File file = new File(fileName);
		long len = file.length();
		byte[] raw = new byte[(int)len];
		FileInputStream fin = new FileInputStream(file);
		int r = fin.read(raw);
		if(r!=len){
			throw new IOException("无法读取全部文件：");
		}
		return raw;
		
	}
	private boolean compile(String javaFile) throws Exception{
		System.out.println("正在编译：" + javaFile + " ... ");
		Process proc = Runtime.getRuntime().exec("javac" + javaFile);
		proc.waitFor();
		
		int ret = proc.exitValue();
		return ret == 0;
		
		
	}
	
	
	protected Class<?> findClass(String name){
		Class clazz = null;
		String fileStub = name.replace(".", "/");
		String javaFilename = fileStub + ".java";
		String classFilename = fileStub + ".class";
		File javaFile = new File(javaFilename);
		File classFile = new File(classFilename);
		
		if(javaFile.exists()&& (!classFile.exists()||javaFile.lastModified() > classFile.lastModified())){
			try {
				if(!compile(javaFilename)||!classFile.exists()){
					throw new ClassNotFoundException("ClassNotFoundException : " + javaFilename);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(classFile.exists()){
			byte[] raw = null;
			try {
				raw = getBytes(classFilename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clazz = defineClass(name, raw, 0,raw.length);
		}
		return clazz;
	}
	
	public static void main(String[] args) {
//		CompileClassLoader.g
	}
	
	
	
}
