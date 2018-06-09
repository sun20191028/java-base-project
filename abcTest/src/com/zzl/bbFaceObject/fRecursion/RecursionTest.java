package com.zzl.bbFaceObject.fRecursion;

public class RecursionTest {
	public static int fn(int n){
		if(n==20){
			return 1;
		}else if(n==21){
			return 4;
		}else{
			return fn(n+2)-2*fn(n+1);
		}
	}
	public static void main(String[] args) {
		System.out.println(fn(10));
	}
}

