package com.zzl.ibNio.i.buffer;

import java.nio.CharBuffer;
/**
 * 记住顺序一定是
 * 1、创建buffer
 * 2、写入数据put（）/将通道中的数据写入buffer--read（）或者整个映射到buffer中map（）
 * 3、为从buffer中读取数据做准备 flip（）
 * 4、读取数据 get（）/将buffer中的数据下发入通道---write（）
 * 5、为写入数据做准备 clear（）
 * @author Au
 *
 */
public class BufferTest {
	public static void main(String[] args) {
		CharBuffer buff=CharBuffer.allocate(8);
		buff.put('a');
		buff.put('1');
		buff.flip();
		System.out.println(buff.get());
		int i=buff.position();
		buff.clear();
		
		System.out.println(i);
		buff.position(i);
		buff.put('b');
		buff.flip();
		while(buff.position()<buff.limit()){
			System.out.println(buff.get());
		}
	}
}
