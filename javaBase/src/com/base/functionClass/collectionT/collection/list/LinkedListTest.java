package com.base.functionClass.collectionT.collection.list;

import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {
	
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		
		list.add("java");
		list.add("android");
		list.add("python");
		list.add("php");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		list.isEmpty();
//		list.remove(1);
		
	}
	
}
