package com.zzl.aaString;

public class User {
	public String name;
	
	public static void main(String[] args) {
		String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
		String str="4b9db269c5f978e1264480b0a7619eea";
		
		String[] b=str.split("|");
		
		for (int i = 1; i < b.length; i++) {
//			System.out.println(b[i]);
			for (int j = 0; j < hexDigits.length; j++) {
				if(b[i].equals(hexDigits[j])){
					System.out.print(j+",");
				}
			}
		}
		
		
		
//		int[] ini={4,11,9,13,11,2,6,9,12,5,15,9,7,8,14,1,2,6,4,4,8,0,11,0,10,7,6,1,9,14,14,10};
//		int j=0;
//		while(j<ini.length){
//			int a=0;
//			a=ini[j]*16;
//			j++;
//			while(j<ini.length){
//				int b=ini[j];
//				j++;
//				System.out.print(a+b+",");
//				break;
//			}
//		}
//		int[] inin={75,157,178,105,197,249,120,225,38,68,128,176,167,97,158,234};
		
	}
}
