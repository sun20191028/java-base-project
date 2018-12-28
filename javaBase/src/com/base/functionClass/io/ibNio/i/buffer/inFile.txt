package com.base.functionClass.io.nio;

import java.nio.CharBuffer;

public class BufferTest {
	public static void main(String[] args) {
		CharBuffer buff = CharBuffer.allocate(16);
		System.out.println(buff.capacity());
		System.out.println(buff.limit());
		System.out.println(buff.position());
//		System.out.println(buff.mark());
		buff.put('a');
		buff.put('b');
		buff.put('c');
		System.out.println(buff.capacity());
		System.out.println(buff.limit());
		System.out.println(buff.position());
		System.out.println("--filp----");
		buff.flip();
		System.out.println(buff.capacity());
		System.out.println(buff.limit());
		System.out.println(buff.position());
		char[] ch = new char[8];
		int hasChar = 0;
		while(buff.hasRemaining()){
			System.out.print(buff.get() + " , ");
		}
		System.out.println();
		System.out.println(buff.capacity());
		System.out.println(buff.limit());
		System.out.println(buff.position());
		
		System.out.println("--clear----");
		buff.clear();
		System.out.println(buff.capacity());
		System.out.println(buff.limit());
		System.out.println(buff.position());
	}
}
