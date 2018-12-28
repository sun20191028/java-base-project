package com.base.classT.baseClass.stringT.t1;

public class SwapTest {
	
	private String a = "aa";
	
	private String b = "bb";
	
	private String st1 = new String("11");
	private String st2 = new String("22");
	
	public void swap(String z,String f){
		String temp = z;
		z = f;
		f = temp;
		System.out.println(z + " , " + f);
	}
	
	public static void main(String[] args) {
		SwapTest s = new SwapTest();
		s.swap(s.a, s.b);
		System.out.println(s.a + " --,-- " + s.b);
		
		s.swap(s.st1, s.st2);
		System.out.println(s.st1 + " --,-- " + s.st2);
	}
	
	
}
