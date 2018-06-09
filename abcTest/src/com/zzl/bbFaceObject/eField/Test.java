package com.zzl.bbFaceObject.eField;

public class Test {
	public static String name;
	public int age;

	public static String getName() {
		return Test.name;
	}

	public static void setName(String name) {
		Test.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
//		this.name="aa";
//		Test.name="aa";
		name="aa";
		this.age = age;
	}

}
