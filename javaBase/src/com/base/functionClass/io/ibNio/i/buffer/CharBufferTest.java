package com.base.functionClass.io.ibNio.i.buffer;

import java.nio.CharBuffer;

public class CharBufferTest {

	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(16);//分配一块缓冲区
		System.out.println("allocate -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
		buffer.put("缓存buff");//向缓冲区写数据
		System.out.println("put -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
		buffer.flip();//将缓冲区从写模式切换到读模式　　
		System.out.println("flip -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
		System.out.println(buffer.get());
		System.out.println(buffer.get());
		buffer.compact();//从读数据切换到写模式，数据不会被清空，会将所有未读的数据copy到缓冲区头部，后续写数据不会覆盖，而是在这些数据之后写数据
		// 所以此时的buff是[buffff...],未读的数据是buff
		System.out.println("compact -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
		buffer.put("####");
		buffer.flip();
		while(buffer.hasRemaining()){
			buffer.get();//向缓冲区读数据　
		}
		System.out.println("get -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
		buffer.clear();//从读模式切换到写模式，不会清空数据，但后续写数据会覆盖原来的数据，即使有部分数据没有覆盖，没覆盖的数据也会遗忘。
		System.out.println("clear -- [" + buffer + "] " + buffer.position() + " , " + buffer.limit() + " , " + buffer.capacity());
	}
}
