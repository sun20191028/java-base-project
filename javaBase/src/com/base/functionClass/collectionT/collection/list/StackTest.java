package com.base.functionClass.collectionT.collection.list;

import java.util.Stack;

public class StackTest {
	
	public static void main(String[] args) {
		Stack stack = new Stack();
		stack.push(8);
		stack.push(19);
		stack.push(3);
		stack.push(9);
		
		System.out.println(stack.peek());
		System.out.println(stack);
		System.out.println(stack.pop());
		System.out.println(stack);
	}
	
}
