package com.base.functionClass.collectionT.collection.list;

import java.util.ArrayList;
import java.util.List;

public class ListDemo {

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("aa");
		list.get(0);
//		String[] arrayStr =  (String[])list.toArray(); //切记 String[] 数组并非 Object[] 数组的子类
		Object[] arrayStr =  list.toArray();
		System.out.println(arrayStr.length);
		list.isEmpty();
		list.size();
		String[] arrayStr1 = new String[8];
		arrayStr1[0] = "java";
		
		test();
		
	}

	public static void test() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		List<String> list2 = new ArrayList<String>();
		list2.add("4");
		list2.add("2");
		list2.add("3");
		List<String> list3 = new ArrayList<String>(list);
		List<String> list4 = new ArrayList<String>(list2);
		list.removeAll(list2);
		System.out.println(list);
		System.out.println(list2);
		System.out.println(list3);
		System.out.println(list4);
	}
}
