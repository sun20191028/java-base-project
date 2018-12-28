package com.base.functionClass.collectionT.collection.list;

import java.util.LinkedList;
import java.util.List;

public class LinkedListSource {
	
	public static void main(String[] args) {
		
		
		/**
		 * linkedlist 虽然没有维护索引，但是依然可以通过下标获取值，
		 * 	获取值的逻辑，是通过遍历，依次向下数到index处，然后取node值
		 */
		LinkedList<String> list = new LinkedList<String>();
		list.add("java");
		list.add("android");
		list.add("python");
		list.add("php");
		list.get(1);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		list.isEmpty();
		list.remove(1);
		
	}
	
}
