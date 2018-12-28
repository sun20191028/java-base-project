package com.base.functionClass.io.icCharset;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.net.util.Base64;

public class Unicode {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		for (int i = 0; i < 0xff; i++) {
//			for (int j = 0; j < 0xff; j++) {
//				ByteBuffer bb = ByteBuffer.allocate(2);
//				bb.put((byte)(i*16));
//				bb.put((byte)j);
//				bb.flip();
//				CharBuffer cb = Charset.forName("utf-8").decode(bb);
//				System.out.print(cb + ",");
//			}
//			System.out.println();
//		}//所以试图利用java去打印所有的字符集，是不可行的
		// 但是另一种，class文件是utf-8字符集的。可以查看class的文件的二进制数据，依然不行，保存的都是编码的字符
		
		CharBuffer cb1 = CharBuffer.allocate(16);// 这种得到的是字符编码，就是传输保存的数据。
		cb1.put("张");// 而字符集是运行时的数据
		cb1.flip();
		ByteBuffer bb1 = Charset.forName("utf-8").encode(cb1);
		while (bb1.hasRemaining()) {
			byte b = bb1.get();
			System.out.print(b + ":" + Integer.toHexString(b) + ",");
		}
		System.out.println();
	}
	
	
}
