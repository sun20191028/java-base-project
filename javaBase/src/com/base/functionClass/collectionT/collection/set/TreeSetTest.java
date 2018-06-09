package com.base.functionClass.collectionT.collection.set;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Set;

public class TreeSetTest {
	
	public static void main(String[] args) {
		
//		Set<String> set = new TreeSet();
//		set.add("10");
//		set.add("5");
//		set.add("8");
//		set.add("100");
//		set.add("-12");
//		
//		Iterator<String> it = set.iterator();
//		while (it.hasNext()) {
//			String str = it.next();
//			System.out.println(str);
//			
//		}
		TreeSet set = new TreeSet();
//		set.comparator();
		set.add(10);
		set.add(5);
		set.add(8);
		set.add(100);
		set.add(-12);
		
		Iterator it = set.iterator();
		while (it.hasNext()) {
			int str = (int)it.next();
			System.out.println(str);
			
		}
		
	}
	
}
