package com.base.classT.baseClass.collectionT.setT;

public class Test {
	
	public static void main(String[] args) {
		String str = "Thread-1 : 41, Thread-1 : 7, Thread-1 : 40, Thread-1 : 6, Thread-1 : 43, Thread-1 : 42, Thread-0 : 29, Thread-1 : 49, Thread-1 : 48, Thread-1 : 45, Thread-1 : 44, Thread-1 : 47, Thread-1 : 46, Thread-0 : 33, Thread-0 : 32, Thread-0 : 35, Thread-0 : 34, Thread-0 : 37, Thread-0 : 36, Thread-0 : 39, Thread-0 : 38, Thread-0 : 31, Thread-0 : 30, Thread-1 : 32, Thread-1 : 31, Thread-1 : 30, Thread-0 : 7, Thread-0 : 8, Thread-0 : 5, Thread-0 : 6, Thread-1 : 39, Thread-1 : 38, Thread-0 : 18, Thread-1 : 37, Thread-0 : 19, Thread-1 : 36, Thread-1 : 35, Thread-1 : 34, Thread-1 : 33, Thread-0 : 24, Thread-0 : 23, Thread-0 : 22, Thread-0 : 21, Thread-0 : 28, Thread-0 : 27, Thread-0 : 26, Thread-0 : 25, Thread-0 : 20, Thread-1 : 8, Thread-1 : 9, Thread-1 : 22, Thread-1 : 23, Thread-1 : 24, Thread-1 : 25, Thread-1 : 26, Thread-1 : 27, Thread-1 : 28, Thread-1 : 29, Thread-1 : 20, Thread-1 : 21, Thread-0 : 15, Thread-0 : 14, Thread-0 : 17, Thread-0 : 16, Thread-0 : 9, Thread-0 : 10, Thread-0 : 13, Thread-0 : 12, Thread-1 : 19, Thread-1 : 13, Thread-0 : 40, Thread-1 : 14, Thread-0 : 41, Thread-1 : 11, Thread-0 : 42, Thread-1 : 12, Thread-1 : 17, Thread-1 : 18, Thread-1 : 15, Thread-1 : 16, Thread-0 : 47, Thread-0 : 48, Thread-0 : 49, Thread-0 : 43, Thread-0 : 44, Thread-1 : 10, Thread-0 : 45, Thread-0 : 46";
//		String str = "1,5,2,3,6,9,7,4";
		String[] strs = str.split(",");
		System.out.println(strs.length);
		for (int i = 0; i < strs.length; i++) {
			for (int j = i +1; j < strs.length; j++) {
				String mid = null;
				if(strs[i].hashCode()>strs[j].hashCode()){
					mid = strs[i];
					strs[i] = strs[j];
					strs[j] = mid;
				}
			}
			
			System.out.println(strs[i]);
		}
		
	}
}
