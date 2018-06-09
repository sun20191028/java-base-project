package com.base.classT.baseClass.regularExpression.t1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo1 {
		
//	public static void main(String[] args) {
//
//		String regex = "\\w+\\s+,\\s+\\w+!";
//		String str = "Hello , Java!";
//		
//		System.out.println(str.matches(regex));
//		
//		String arr[] = str.split("\\s+,\\s");
//		System.out.println(arr[0]+"\n"+arr[1]);
//		
//		Pattern p = Pattern.compile("");
//		
//		
//	}
	
	public static void main(String[] args) {
		String str = "我想求购一本《疯狂Java讲义》，尽快联系我13500006666"
				+ "交朋友，电话号码13511113333"
				+ "出售二手电脑，联系方式13890009999"
				+ "电话：0794-7654389";
		Pattern pa = Pattern.compile("((13\\d)|(15\\d))\\d{8}");
		Matcher m = pa.matcher(str);
		while(m.find()){
			System.out.println(m.group());
		}
	}
}
