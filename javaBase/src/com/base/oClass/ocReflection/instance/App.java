package com.base.oClass.ocReflection.instance;

import java.lang.reflect.Constructor;

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
		
		
		
		System.out.println(entity);
		System.out.println(entity1);
	}
	
	
}
