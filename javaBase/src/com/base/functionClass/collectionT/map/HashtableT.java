package com.base.functionClass.collectionT.map;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HashtableT {
	static List<Hashtable<String, String>> hashtableList=new ArrayList<>();
	
	public static void init(){
		for (int i = 0; i < 10; i++) {
			Hashtable<String, String> ht=new Hashtable<>();
			for (int j = 0; j < 5; j++) {
				ht.put(j+"", i+" "+j);
			}
			hashtableList.add(ht);
		}
	}
	
	public static void main(String[] args) {
		init();
		for (int i = 0; i < hashtableList.size(); i++) {
			Hashtable<String, String> ht=hashtableList.get(i);
			for (int j = 0; j < ht.size(); j++) {
				System.out.print(ht.get(j+"")+",");
			}
			System.out.println();
		}
		
//		System.out.println(hashtableList);
	}
}
