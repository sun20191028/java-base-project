package com.base.classT;

class SingleTon {
	private static SingleTon singleTon = new SingleTon();
	public static int count1;
	public static int count2 = 0;

	public SingleTon() {
		count3 = 1;
		count1++;
		count2++;
	}

	{
		count1 += 1;
	}
	public int count3 = count1 + count2;
	
	{
		count3 += 3;
	}

	public static SingleTon getInstance() {
		return singleTon;
	}
}

public class Test {
	public static void main(String[] args) {
		SingleTon singleTon = SingleTon.getInstance();
		System.out.println("count1=" + singleTon.count1);
		System.out.println("count2=" + SingleTon.count2);
		System.out.println("count2=" + singleTon.count2);
		System.out.println("count3=" + singleTon.count3);
	}
}
