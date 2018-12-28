package com.base.basic.baseClass.regularExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. 如何创建正则表达式
 * 2. 如何使用正则表达
 * 	Pattern pa = Pattern.compile("((13\\d)|(15\\d))\\d{8}");
		Matcher m = pa.matcher(str);
		while(m.find()){
			System.out.println(m.group());
		}
	3. String 自带一些正则匹配
 * @author liangpro
 *
 */
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
		
//		
//		String str = "lad\"{j";
//		str = str.replace("\"{", "{");
//		System.out.println(str);
//		
		String dataStr = "\"msg\":\"{\"data\":{\\,\\\\}}\"";
//		"msg":"{"data":{\,\\}}"
		System.out.println(dataStr);
		dataStr = dataStr.replaceAll("\\\\", "");// 正则表达式需要解析两次，第一次 java的字符串解析，第二次正则表达式解析
		System.out.println(dataStr);		// " 对于java是特殊字符，但对于正则表达式不是特殊字符，所以 \" 就可以匹配
											// { 对于正则表达式 需要转义 ，所以 正则表达式是 \{ ,而 \ 对于java是需要转义， 所以需要 \\
		dataStr = dataStr.replaceAll("\"\\{", "{"); // 对于java需要转义 ，正则表达式也需要转义的，则需要转义两次  ///X
													// 例如 ////
		System.out.println(dataStr);
		
		
		String str1 = "啦肯德基广龙达科技示范";
		System.out.println(str.contains("广"));
		Pattern pattern = Pattern.compile("广");
		Matcher matcher = pattern.matcher(str1);
		System.out.println(matcher.find());

	}
}
