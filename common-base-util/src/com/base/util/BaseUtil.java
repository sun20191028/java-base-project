package com.base.util;

import java.util.Arrays;

public class BaseUtil {
	public static void main(String[] args) {
//		int i = 1024;
//		System.out.println( Arrays.toString(intToByteArray(i)));
//		byte[] by = {0,0,4,0};
//		System.out.println(byteArrayToInt(by));
		
	}
	
	
	/**
	 * int 转换成 byte数组。
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] intBytes = new byte[4];
		intBytes[0] = (byte) (i >> 24 & 0xFF); //右移24位，然后取低8位
		intBytes[1] = (byte) (i >> 16 & 0xFF); //右移16位，然后取低8位,也就是取 17位~24位之间的 二进制数。
		intBytes[2] = (byte) (i >> 8 & 0xFF);
		intBytes[3] = (byte) (i & 0xFF);
		return intBytes;
	}
	
	/**
	 * 4为 byte数组 转换成 int。
	 * 若为{0,0,0,0} 则返回    0
	 * @param i
	 * @return
	 */
	public static int byteArrayToInt(byte[] by) {
		int ch1 = by[0];
		int ch2 = by[1];
		int ch3 = by[2];
		int ch4 = by[3];
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}
}
