package com.base.functionClass.collectionT.collection.set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 集合的删除一定要用 iterator，
 * 需要迭代器去维护索引。
 * @author liangpro
 *
 */
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
