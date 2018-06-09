package com.zzl.aaString;

public class StringTest {
	public static String str="";
	public static void main(String[] args) {
		
		
//		System.out.println(str.trim());
//		System.out.println(str.length());
//		User u=new User();
//		System.out.println((u.userName).trim());//报空指针
//		System.out.println((u.userName).length());//报空指针
		
		String ss="adfk|askljf";
		String[] s=ss.split("\\|");
		for (String string : s) {
			System.out.println(string);
		}
		
//		String st="哈alfd";
//		char[] c=new char[st.length()];
//		int k=0;
//		for (int i = 0; i < st.length(); i++) {
//			c[i]=st.charAt(i);
//			k=k+1;
//			if(c[i]>255){
//				k=k+1;
//			}
//		}
//		System.out.println(k);
//		System.out.println(c);
//		System.out.println(st.length());
		
		/*
		 * 去除空白字符
		 */
		
//		String str="adal\rlsfj\nads  adfas\r\tads";
//		System.out.println(str);
//		str=str.replace("\r", "");
//		str=str.replace("\n", "");
//		str=str.replace("\t", "");
//		str=str.replaceAll("\\s+", "");
//		str=str.trim();
//		char s=(char)0;
//		String st=String.valueOf(s);
//		str = str.replace(st, "");
////		str.replaceAll("\\u00A0","");
//		char[] name = new char[str.length()];
//		for (int i = 0; i < name.length; i++) {
//			name[i]=str.charAt(i) ;
//			System.out.println(name[i]);
//		}
//		System.out.println(str);
//		char c=' ';
//		System.out.println((int)c);

//		String lenth = "0000";
//		byte[] b = lenth.getBytes();
//		System.out.println(b);
//
//		String str = "你好java";
//		System.out.println(str.length());
//		byte[] bstr = str.getBytes("GBK");
//		System.out.println(bstr.length);
		
	}
}
