package com.base.oClass.ocReflection.classInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SuppressWarnings(value="unchecked")
@Deprecated
@Anno
public class ClassTest {
	private ClassTest(){
		System.out.println("执行无参构造器");
	}
	
	public ClassTest(String name){
		System.out.println("执行构造器，参数name :" +name);
	}
	
	public void info(String str){
		System.out.println("执行info方法,参数str为：" + str);
	}
	private void info(String str,String str1){
		System.out.println("执行info方法,参数str为：" + str + " ,str1 :" + str1);
	}
	Class Inner(){
		return null;
		
	}
	
	
	public static void main(String[] args) {
		
		Class<ClassTest> clazz = ClassTest.class;
		Constructor[] ctors = clazz.getConstructors();
		System.out.println("全部的public构造器");
		for (Constructor constructor : ctors) {
			System.out.println(constructor);
		}
		
		System.out.println("全部的public方法");
		Method[] mtds = clazz.getMethods();
		for (Method method : mtds) {
			System.out.println(method.getName());
		}
		
		System.out.println("全部的注解");
		Annotation[] anns = clazz.getAnnotations();
		for (Annotation annotation : anns) {
			System.out.println(annotation);
		}
		
		
	}
}
