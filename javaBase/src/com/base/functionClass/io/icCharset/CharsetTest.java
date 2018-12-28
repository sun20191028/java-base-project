package com.base.functionClass.io.icCharset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.SortedMap;

/**
 * charset
 * @author liangpro
 *
 */
public class CharsetTest {
	
	public static void main(String[] args) {
//		SortedMap<String, Charset> map = Charset.availableCharsets();
//		for (Entry<String, Charset> charset : map.entrySet()) {
//			System.out.println(charset.getKey() + " --> " + charset.getValue());
//		}
		Charset charset = Charset.forName("GBK");
		CharBuffer buff = CharBuffer.allocate(16);
		buff.put("我有一只小毛驴");
		buff.flip();
		ByteBuffer bf = charset.encode(buff);
		System.out.println(buff);
		charset.decode(bf);
		bf.flip();
		System.out.println(Arrays.toString(bf.array()));
		
		
	}
	
}
