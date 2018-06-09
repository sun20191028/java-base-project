package com.zzl.eaencode;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;

public class CharsetTest {
	public static void main(String[] args) {
		/**
		 * 将字符直接转换成二进制
		 */
//		String str="张志亮龖";
//		char[] strChar=str.toCharArray();
//		for (int i = 0; i < strChar.length; i++) {
//			System.out.println(strChar[i]+"---->"+doWork(Integer.toBinaryString(strChar[i])));
//		}
		/**
		 * 获得系统支持的所有编码集。
		 */
//		SortedMap<String, Charset> map=Charset.availableCharsets();
//		for (String element : map.keySet()) {
//			System.out.println(element+"------>"+map.get(element));
//		}
		
		Charset charset=Charset.forName("gbk");
		Charset utf=Charset.forName("utf-8");
		CharBuffer cb=CharBuffer.allocate(8);
		System.out.println("孙"+doWork(Integer.toBinaryString('孙')));
		System.out.println("龖"+doWork(Integer.toBinaryString('龖')));
		cb.put('孙');
		cb.put('悟');
		cb.put('空');
		cb.put('刚');
		cb.flip();
//		CharBuffer cb2=cb;//buffer中的东西只能取一次。------------------------
//		cb2.flip();
		
//		ByteBuffer bb=charset.encode(cb);
//		for (int i = 0; i < bb.capacity(); i++) {
//			System.out.print(doWork(Integer.toBinaryString(bb.get(i)))+"  ");
//		}
//		System.out.println();
//		System.out.println("------------");
		ByteBuffer bb=utf.encode(cb);
		int i = 0;
		for (; i < bb.limit(); i++) {
			System.out.print(doWork(Integer.toBinaryString(bb.get(i)))+"  ");
		}
		
		System.out.println("\r\n"+i);
		System.out.println(utf.decode(bb));
		
//		Properties pro=System.getProperties();
//		Iterator<Object> i=pro.keySet().iterator();
//		while(i.hasNext()){
//			System.out.println(i.next().toString());
//		}
		
		
	}
	public static String doWork(String s){
		String str="";
		for (int i = 0; i < s.length(); i++) {
			str+=s.substring(i,i+1);
			if((i+1)%8==0){
				str+='_';
			}
		}
		return str;
		
	}
}
