package com.base.basicDataTypes.volatileDemo;

public class Test {
	public static void main(String[] args) {
		String reg =  "^EBOSS_TCMP_MSISDN_R_SUM_20181121.[0-9]{3}$";
		
		String str = "EBOSS_TCMP_MSISDN_R_SUM_20181121.163";
		System.out.println(str.matches(reg));
		
	}
}
