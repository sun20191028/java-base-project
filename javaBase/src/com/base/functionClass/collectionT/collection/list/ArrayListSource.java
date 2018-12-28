package com.base.functionClass.collectionT.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListSource {
	
	public static void main(String[] args) {
		ArrayList<String>  list = new ArrayList<String>();
		list.add("11");
		list.add("22");
		list.add("33");
		list.add("44");
		list.add("55");
		String[] str = new String[list.size()+10];
		String[] str2 = new String[list.size()+10];
		list.toArray(str);
		System.arraycopy(str, 2, str2, 3,4);// 
		System.out.println(Arrays.toString(str));
		System.out.println(Arrays.toString(str2));
		System.out.println(Arrays.toString(Arrays.copyOf(str, 20)));
//		list.add(2,"cc");
//		list.add("dd");
//		list.set(4, "bb");
//		System.out.println(list);
//		list.remove(1);
		
		list.get(1);
		
		
	}
	
	
	
}
