package com.zzl.jaCollection.aIterator.hashSetIterator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class IteratorTest {
	public static void main(String[] args) {
		Collection<String> books=new HashSet<>();
		books.add("aaaaa");
		books.add("bbbb");
		books.add("cccc");
		books.add("bbbb");
		
		Iterator it=books.iterator();
		while(it.hasNext()){
			String book=(String)it.next();
			if(book.equals("bbbb")){
				books.remove("bbbb");//error
			}
			book="测试字符串";
		}
		
		System.out.println(books);
	}
}
