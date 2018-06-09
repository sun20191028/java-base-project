package com.zzl.aaString;

public class 常量池 {
	public static void main(String[] args) {
		
//		Integer in1 = 1;
//		Integer in2 = 1;
//		System.out.println(in1==in2);//true
//		
		Integer in3 = 128;
		Integer in4 = 128;
		System.out.print(in3==in4);//false
//		System.out.print(",");
//		System.out.println(in3.equals(in4));//true
		
		Integer in5 = new Integer(12);
		Integer in6 = new Integer(12);
		Integer in7 = new Integer(121);
		System.out.print(in5==in6);//false
		System.out.print(",");
		System.out.println(in5.equals(in6));//true
		System.out.println(in5.getClass().getName() + "@" + Integer.toHexString(in5.hashCode())+","
				+in6.getClass().getName() + "@" + Integer.toHexString(in6.hashCode())+","
				+in7.getClass().getName() + "@" + Integer.toHexString(in7.hashCode()));
		
//		String str1 = "abcdefg";
//		String str2 = "abcdefg";
//		System.out.println(str1==str2);//true
		
		String str3 = new String("abcdefg");
		String str4 = new String("abcdefg");
		String str5 = new String("abcdefggh");
		System.out.print(str3==str4);//false
		System.out.print(",");
		System.out.println(str3.equals(str4));//true
		System.out.println(str3.getClass().getName() + "@" + Integer.toHexString(str3.hashCode())+","
				+str4.getClass().getName() + "@" + Integer.toHexString(str4.hashCode())+","
				+str5.getClass().getName() + "@" + Integer.toHexString(str5.hashCode()));
		
		People pp1 = new People("zhangsan");
		People pp2 = new People("lisi");
		System.out.print(pp1==pp2);//false
		System.out.print(",");
		System.out.println(pp1.equals(pp2)); //false
		System.out.println(pp1.getClass().getName() + "@" + Integer.toHexString(pp1.hashCode())+","
				+pp2.getClass().getName() + "@" + Integer.toHexString(pp2.hashCode()));
		
		
		People pp3 = new People();
		People pp4 = new People();
		System.out.print(pp3==pp4);//false
		System.out.print(",");
		System.out.println(pp3.equals(pp4)); //false
		System.out.println(pp3.getClass().getName() + "@" + Integer.toHexString(pp3.hashCode())+","
				+pp4.getClass().getName() + "@" + Integer.toHexString(pp4.hashCode()));
	}
}

class People{
	String name;
	
	People(String name){
		this.name = name;
	}
	
	People(){
		
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 1;
	}
}