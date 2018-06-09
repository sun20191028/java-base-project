package com.zzl.bbFaceObject.bExtendsObject;

public class TestExtendsObject {
	public static void main(String[] args) {
		Derived dd = new Derived();
//		dd.name;
		System.out.println(dd.age);
		System.out.println(((Parent)dd).name);
		
		
	}
}
