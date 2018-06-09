package com.zzl.halog4j;

import org.apache.log4j.Logger;

public class Log4jTest {
	public static void main(String[] args) {
		String s="aaab,adl,asdfk,asd";
		String[] c=s.split(",");
		Logger logger=Logger.getLogger(Log4jTest.class);
		
		for (String string : c) {
			logger.info(string);
			System.out.println(string);
		}
				
	}

}
