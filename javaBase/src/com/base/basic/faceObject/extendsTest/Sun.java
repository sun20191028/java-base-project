package com.base.basic.faceObject.extendsTest;

/**
 * 未指定，则子类默认调用父类的无参构造器，不管子类用的是有参构造器，还是无参构造器
 * @author liangpro
 *
 */
public class Sun extends Father{
	
	public Sun(String id,String name){
//		super("cc", "dd");
		System.out.println("sun id: " + id + ",name :" + name);
		
	}
	
	
	public static void main(String[] args) {
		String str = "adsaa你好";
		Sun s = new Sun("aa", "bb");
		System.out.println(str.getBytes().length);
		
		
	}
	
}
