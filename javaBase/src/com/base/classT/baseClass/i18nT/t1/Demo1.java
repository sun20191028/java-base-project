package com.base.classT.baseClass.i18nT.t1;

import java.util.Locale;
import java.util.ResourceBundle;

public class Demo1 {
	
	public static void main(String[] args) {
//		Locale[] locales = Locale.getAvailableLocales();
//		for (Locale locale : locales) {
//			System.out.println(locale.getDisplayCountry()+" = "+locale.getCountry()+", "+locale.getDisplayLanguage()+" = "+locale.getLanguage());
//		}
		Locale myLocale = Locale.getDefault(Locale.Category.FORMAT);
		
		System.out.println(myLocale.toString());
		
		ResourceBundle bundle = ResourceBundle.getBundle("mess",myLocale);
		System.out.println(bundle.getString("hello"));
		
	}
}
