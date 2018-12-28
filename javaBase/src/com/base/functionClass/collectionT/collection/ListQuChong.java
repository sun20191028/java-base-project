package com.base.functionClass.collectionT.collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class ListQuChong {
//	public void say(){
//		HashMap<K, V>
//	}
	
	public static void main(String[] args) {
		List<String> vector=new Vector<String>(); //这里的vector可以换成ArrayList或者LinkedList，效果都一样     
		vector.add("Hello");     
		vector.add("world");     
		vector.add("Hello");         
		System.out.println("去重之前vector中的集合为:"+vector.toString());     
		
		Set<String> set=new HashSet<String>(vector);//利用set去重
		vector=new Vector<String>(set);
		System.out.println("去重之后vector中的集合为:"+vector.toString());
	}
}
