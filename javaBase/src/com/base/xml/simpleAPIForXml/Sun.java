package com.base.xml.simpleAPIForXml;

public class Sun extends Father{
	
	public Sun(String id,String name){
		super("cc", "dd");
		System.out.println("sun id: " + id + ",name :" + name);
		
	}
	
	
	public static void main(String[] args) {
		String str = "adsaa你好";
		Sun s = new Sun("aa", "bb");
		System.out.println(str.getBytes().length);
		
		
	}
	
}
