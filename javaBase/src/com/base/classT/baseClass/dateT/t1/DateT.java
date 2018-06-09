package com.base.classT.baseClass.dateT.t1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateT {

	public static void main(String[] args) {
//		Date date = new Date();
//		System.out.println(date.getTime());
//		System.out.println(date.toString());
//		
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(2013, 7, 31);
//		cal.set(Calendar.MONTH, 8);
//		System.out.println(cal.getTime());
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 360);  //计算失效时间
//		Timestamp times = new Timestamp(calendar.getTimeInMillis());
//		String effect_date = calendar.get(Calendar.YEAR)+""+calendar.get(Calendar.MONTH)+""+calendar.get(Calendar.DATE);
		Date date = new Date(calendar.getTimeInMillis());
		SimpleDateFormat SDFormat = new SimpleDateFormat("YYYY-MM-dd");
		String dat = SDFormat.format(date);
		System.out.println(dat);
//		cf.setEffect_date(dat);
		
	}
}
