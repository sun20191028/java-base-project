package com.base.functionT;

import java.util.Arrays;

public class TestNew {
	
	public static void main(String[] args) {
		TestNew newt = new TestNew();
		newt.println("aa","bb");
		newt.println();
		String[] strs = {"aa","bb"};
		newt.printlns(strs);
//		newt.printlns();
		
	}
	
	private void println(String... locations){//这种调用可以不传参
		String[] strs = locations;
		System.out.println(Arrays.toString(strs));
	}
	
	private void printlns(String[] locations){
		System.out.println(Arrays.toString(locations));
	}
	
}
