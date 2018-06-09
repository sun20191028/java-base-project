package com.base.functionT.signT;

public class Demo1 {
	public static void main(String[] args) {
//		double dou1 = 2.1415;
//		double dou2 = 8.8374;
//		System.out.println(dou1*4);
//		double div = dou2/dou1;
//		System.out.println(div);
//		double dd  = dou2%dou1;
//		System.out.println(dd);
//		
//		double a = 5.2;
//		double b = 3.1;
//		double c = a%b;
//		System.out.println(c);
		
//		int a = 5;
//		System.out.println(a++ +5+ ++a +5+a);
		
		outer:
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 10; j++) {
					
					if(i==3&&j==5){
//						System.out.print(i+"-"+j+",");
//						break outer;
						System.out.println();
						continue outer;
					}
					System.out.print(i+"-"+j+",");
				}
				System.out.println();
			}
		
		
		
	}
}
