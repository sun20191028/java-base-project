package com.base.functionT.faceObject.t1;

public class BlockTest {
	static{
//		name = "zhangsan";
		interesting = "badminton";
	}
	{
		age =  5;
//		age = age + 5;
//		System.out.println(age);
		interesting = "badminton";
		String sex = "body";//代码块中声明的变量有效范围仅仅是代码块。
	}
	String sex ;
	static String interesting;
	public int age = 10;
	public String name;
	
	/**
	 * 也就是说默认初始化在，构造器初始化之前。
	 * @param name
	 * @param age
	 */
	public BlockTest(String name, int age){
		
		System.out.println("age:"+this.age+",name:"+this.name);
		this.age = age;
		this.name = name;
//		this.interesting = 
		System.out.println("age:"+this.age+",name:"+this.name);
	}
	
	public BlockTest(){
		
	}
	
	public static void main(String[] args) {
		BlockTest bt1 = new BlockTest();
		System.out.println(bt1.age);
//		BlockTest bt = new BlockTest("zhangsan", 21);
	}
}
