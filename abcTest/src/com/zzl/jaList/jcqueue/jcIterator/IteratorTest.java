package com.zzl.jaList.jcqueue.jcIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IteratorTest {
	public static void main(String[] args) {
		Set<String> books=new HashSet<>();
		books.add("english");
		books.add("crazyJava");
		books.add("javaCore");
		Iterator<String> iterator=books.iterator();
		while(iterator.hasNext()){
			if(iterator.next().equals("crazyJava")){
				iterator.remove();
			}
		}
		for (String string : books) {
			System.out.println(string);
		}
	}
}
