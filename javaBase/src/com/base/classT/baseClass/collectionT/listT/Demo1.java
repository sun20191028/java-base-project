package com.base.classT.baseClass.collectionT.listT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Demo1 {
	public static void main(String[] args) {
//		Collection<E>
//		Object
//		Map<K, V>
//		Iterator<E>
		
//		Collection<String> books = new HashSet<>();
		List<String> books = new ArrayList();
		books.add("aa");
		books.add("bb");
		books.add("cc");
		System.out.println(books);
		Iterator bs = books.iterator();
		while(bs.hasNext()){
			String str = (String)bs.next();
			if(str.equals("aa")){
//				books.remove(str);
				bs.remove();
			}
//			System.out.println(bs.next());
//			System.out.println(bs.next());
		}
		
		
//		for (String string : books) {
//			if(string.equals("aa")){
//				books.remove(string);
//			}
//		}
		
//		for (int i = 0; i < books.size(); i++) {
//			String str = books.get(i);
//			if(str.equals("aa")){
////				books.remove(str);
//				books.remove(i);
//			}
//		}
		
		System.out.println(books);
	}
}
