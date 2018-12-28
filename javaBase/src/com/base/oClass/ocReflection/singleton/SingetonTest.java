package com.base.oClass.ocReflection.singleton;

import java.lang.reflect.Constructor;

 
public class SingetonTest {
 
	private static SingetonTest singleton = null;
	private int s = 0;
	
	// 构造方法是私有的
	private SingetonTest(){}
	
	// 同步的获取实例方法
	public static synchronized SingetonTest getInstance(){
		// 懒汉模式的单例方法
		if(null == singleton){
			singleton = new SingetonTest();
		}
		return singleton;
	}
	
	
	public int getS() {
		return s;
	}
 
	public void setS(int s) {
		this.s = s;
	}
 
	
}
