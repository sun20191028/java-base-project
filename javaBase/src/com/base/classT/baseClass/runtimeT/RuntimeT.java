package com.base.classT.baseClass.runtimeT;

public class RuntimeT {
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		
		System.out.println(runtime.availableProcessors());
		System.out.println(runtime.freeMemory());
		System.out.println(runtime.totalMemory());
		System.out.println(runtime.maxMemory());
	}
	
	
	
	
	
}
