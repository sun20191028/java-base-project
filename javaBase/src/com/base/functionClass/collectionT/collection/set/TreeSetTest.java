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
		set.comparator();
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
		
		
		TreeSet<Entity> tree = new TreeSet<Entity>(new ComparableExamp());
		tree.add(new Entity(1,9));
		tree.add(new Entity(3,6));
		tree.add(new Entity(5,1));
		tree.add(new Entity(9,20));
		
		Iterator<Entity> ite = tree.iterator();
		while (ite.hasNext()) {
			Entity entity = ite.next();
			System.out.println(entity);
			
		}
	}
	
}
