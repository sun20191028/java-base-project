package com.base.classT.baseClass.collectionT.setT;

class A{
	public int age;
//	public boolean equals(Object o){
//		return true;
//	}
}

class D extends A{
	public String name;
}

class B{
	public int hashCode(){
		return 1;
	}
}
class C{
//	public boolean equals(Object o){
//		return true;
//	}
	
	public int hashCode(){
		return 1;
	}
}


public class HashSetDemo {
	
	public static void main(String[] args) {
		A a1 = new A();
		A a2 = new A();
		System.out.println(a1.hashCode()+","+a2.hashCode());
		C c1 = new C();
		C c2 = new C();
		System.out.println(c1.hashCode()+","+c2.hashCode());
		System.out.println((c1==c2)+","+c1.equals(c2));
		System.out.println(a1.hashCode());
		System.out.println(a1.age);
		a1.age = 10;
		System.out.println(a1.age);
		System.out.println(a1.hashCode()+","+a2.hashCode()+","+(a1==a2)+","+a1.equals(a2));
		
		a1 = new A();
		System.out.println(a1.hashCode()+","+(a1==a2)+","+a1.equals(a2));
		a1 = new D();
		System.out.println(a1.age);
		System.out.println(a1.hashCode()+","+(a1==a2)+","+a1.equals(a2));
	}
}
