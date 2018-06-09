package com.zzl.acshuzu;

public class FundmentalTest {
	public static void main(String[] args) {
//		int[] ii=new int[]{1,2,3,};
//		System.out.println(ii.length);
		People[] p=new People[2];
		People a=new People();
		a.name="marin";
		a.age=20;
		People b=new People();
		b.name="faker";
		b.age=22;
		p[0]=a;
		p[1]=b;
		System.out.println(a);
		System.out.println(p[0]);
	}
}
