package com.base.functionClass.collectionT.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Demo1 {
	public static void main(String[] args) {
//		List<String> list = new ArrayList<>();
//		list.add("aaa");
//		list.add("bb");
//		list.add("疯狂java讲义");
//		Iterator it = list.iterator();
//		while(it.hasNext()){
//			String str = (String)it.next();
//			if(str.equals("bb"))
////				it.remove();
//				list.remove(str);
//			
//		}
//		System.out.println(list.toString());
		/**
		 * set 不能在iterator时，修改集合元素。
		 */
		Collection<String> list = new HashSet<>();
		list.add("aaa");
		list.add("bb");
		list.add("疯狂java讲义");
		Iterator it = list.iterator();
		while(it.hasNext()){
			String str = (String)it.next();
			if(str.equals("bb"))
//				it.remove();
				list.remove(str);
			
		}
		System.out.println(list.toString());
		
//		HashSet<String> list = new HashSet<>();
//		list.add("aaa");
//		list.add("bb");
//		list.add("疯狂java讲义");
//		Iterator it = list.iterator();
//		while(it.hasNext()){
//			String str = (String)it.next();
//			if("bb".equals(str))
//				it.remove();
//		}
//		System.out.println(list.toString());
//		String
//		Integer
	}
}
