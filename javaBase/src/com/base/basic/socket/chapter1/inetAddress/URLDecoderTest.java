package com.base.basic.socket.chapter1.inetAddress;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDecoderTest {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//将 application/x-www-form-urlencoded 字符串转换成普通字符串
		String keyWord = URLDecoder.decode("%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4");
		System.out.println(keyWord);
		//将普通字符串转成 application/x-www-form-urlencoded 字符串
		String urlStr = URLEncoder.encode("阿里巴巴", "utf-8");
		System.out.println(urlStr);
	}
	
	
}
