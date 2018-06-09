package com.zzl.kaTime;

import java.util.Date;

public class TimeTest {
	public static void main(String[] args) {
//		Date d=new Date();
//		System.out.println(d);
//		long l=System.currentTimeMillis();
//		System.out.println(l);
		
		for (int i = 0; i <= 3; i++) {
			for (int j = 0; j <= 6; j++) {
				if(j==(3-i)){
					System.out.print("*");
				}else if(j==i+3){
					System.out.print("\t*");
				}else{
					System.out.print("\t");
				}
			}
			System.out.println();
		}
		for (int i = 1; i <= 3; i++) {
			for (int j = 0; j <= 6; j++) {
				if(i==j){
					System.out.print("*");
				}else if(j==(6-i)){
					System.out.print("\t*");
				}else{
					System.out.print("\t");
				}
			}
			System.out.println();
		}
		
	}
}
