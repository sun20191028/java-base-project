package com.base.functionT.instanceOfTest;

public class Demo1 {
	public static void main(String[] args) {
		
		Object heo = "Hello";
		
		System.out.println(heo instanceof Object);
		System.out.println(heo instanceof String);
		System.out.println(heo instanceof Math);
		
		System.out.println(heo instanceof Comparable);
		
	}
}
