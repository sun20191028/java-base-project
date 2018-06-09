package com.zzl.bbFaceObject.eField;

public class Test1 {
	static String aa;
	static String bb;
	public static void swap(String a,String b){
		String tmp=a;
		a=b;
		b=tmp;
		aa=b;
	}
	public static void main(String[] args) {
		String a="aa";
		String b="bb";
		bb=a;
		swap(a,b);
		System.out.println(bb.equalsIgnoreCase(aa));
	}
	
}
