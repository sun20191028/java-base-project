package com.base.classT.baseClass.bigDecimalT;

public class Test3 {
	
	
	public static void main(String[] args) {
//		System.out.println(~0010101);
		
		StringBuilder s = new StringBuilder("012345678 ");
		if(s.length() == 10){
			s.insert(10, "abcdef");
			s.delete(3, 8);
			System.out.println(s.indexOf("c"));
		}
		args[0] = "MyJava";
//		args[1] = 'c';
		
	}
	
	
}
