package com.base.oClass.ocReflection.method;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class App {
	
	
	/**
	 * 私有的 构造器都 获取不到。
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Class<EntityClass> clazz = EntityClass.class;
//		EntityClass entity = clazz.newInstance(); //要求公有的默认构造器
		Constructor cons = clazz.getDeclaredConstructor();// 获得无参的构造器
		Constructor constr = clazz.getDeclaredConstructor(String.class);// 获得无参的构造器
		cons.setAccessible(true);//setAccessible 可以 打破 private 的控制。需要getDeclaredConstructor，而不能是getConstructor
		constr.setAccessible(true);
		
		EntityClass entity = (EntityClass) cons.newInstance();
		EntityClass entity1 = (EntityClass) constr.newInstance("zhangsan");
		
//		System.out.println(entity);
//		System.out.println(entity1);
		
		Method info = clazz.getDeclaredMethod("info");//无参方法
		info.invoke(entity);
		
		Method info1 = clazz.getDeclaredMethod("info",String.class);//无参方法
		info1.invoke(entity1,"lisi");
		
		
		/**
		 * 访问 成员变量的值。
		 */
		Field namefield = clazz.getDeclaredField("name");
		namefield.setAccessible(true);
		namefield.set(entity1, "wangwu");
		Field ageField = clazz.getDeclaredField("age");
		ageField.setAccessible(true);
		ageField.set(entity1, 25);
		System.out.println(entity1);
		
		
	}
	
	
}
