package com.base.functionT.faceObject.t1;

public class Demo1 {
	public static final int age = 21;
	public static String sex ;
	public String love;
//	static{
//		age = 22;
//	}
//	public final String name = "zhangsan";
//	{
//		name = "lisi";
//	}
	
	
	public static void main(String[] args) {
		String str = "aa"+ "bb";
		String str1 = str.intern();
		System.out.println(str1);
		System.out.println(age);
//		age = 22;
		System.out.println(sex);
		System.out.println(new Demo1().love);
		
		final String occupation;
		
		occupation = "Marketing";
//		occupation = "programmer";
		
		
	}
}
class A{
	
	public String name;
	
	
}