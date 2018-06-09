package com.base.functionT.T.t1;

import java.util.ArrayList;

public class Demo1 {
	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		al.add("疯狂java讲义");
		al.add("9");
		al.add(9);
		
		for (int i = 0; i < al.size(); i++) {
			System.out.println(((String)al.get(i)).length());
		}
//		Number
		
	}
}
