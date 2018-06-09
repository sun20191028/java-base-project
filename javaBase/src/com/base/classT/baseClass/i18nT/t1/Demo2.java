package com.base.classT.baseClass.i18nT.t1;

import java.io.UnsupportedEncodingException;

public class Demo2 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "你好8727";
		char[] ch = str.toCharArray();
		for (char c : ch) {
			System.out.print(c+"-->");
			System.out.println(Integer.toBinaryString(c));
		}
		
		
		byte[] bs = str.getBytes("utf-8");
		System.out.println(bs.length);
        for(byte bit : bs){
        	System.out.print(bit+"------");
            char hex = (char) (bit & 0xFF);// & 0xFF
            System.out.println(Integer.toBinaryString(hex));
            
        }
        
//        static String toBinaryString(int i) 
//        static String toHexString(int i) 
//        static String toOctalString(int i) 

//        11100100_10111101_10100000__
//        11100101_10100101_10111101__
//        111000110111110010110111
	}
}
