package com.base.classT.baseClass.collectionT.setT;

import java.util.HashSet;

public class UnSafeHashSet {
	public static HashSet hashSet = new HashSet();
	
	
	public static void main(String[] args) {
		ThreadDemo demo1 = new ThreadDemo();
		ThreadDemo demo2 = new ThreadDemo();
		demo1.start();
		demo2.start();
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(hashSet);
		
	}
	
	
	
	
}
