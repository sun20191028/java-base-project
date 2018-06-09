package com.base.classT.arrayT.t1;

public class ArrayT {
	public int[] in = new int[8];
	public int  in2;
	
	public void say(){
		
		for (int a : in) {
			System.out.println(a);
		}
		System.out.println("print over!");
		
	}
	
	public void say2(){
		System.out.println(in2);
	}
	
	public static void main(String[] args) {
		ArrayT at = new ArrayT();
		at.say();
		at.say2();
	}
}
