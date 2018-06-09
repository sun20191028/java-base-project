package com.zzl.abVariable;

import com.zzl.aaString.User;

public class TestMain {
	public static void main(String[] args) {
		/**
		 * 并不是说==号比的就是地址。equals比的就是实际值。而是==比的就是变量中存的那一部分。而equals比的是变量所引用到的部分。
		 * 这样就变成了==比的是地址，equals比的是实际引用值。
		 */
		String str=new String("lll");
		String st=new String("lll");
		System.out.println(str==st);
		System.out.println(str.equals(st));
		User u=new User();
		User us=new User();
		u.name="lll";
		us.name="lll";
		System.out.println(u.name==us.name);
	}
	
	public String getName(String str){
		String name="marin";
		name=str;
		return name;
	}
}
