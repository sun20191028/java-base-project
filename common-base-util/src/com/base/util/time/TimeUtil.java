package com.base.util.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public static String getNowFormatTime(String pattern){
		return new SimpleDateFormat(pattern).format(new Date());
	}
	
	public static String getStandardTimeToSecond(){
		return getNowFormatTime("yyyy-MM-dd HH:mm:ss");
	}

	public static String getStandardTimeToDay(){
		return getNowFormatTime("yyyy-MM-dd");
	}
	
}
