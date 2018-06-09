package com.zzl.oClass.ocReflection;

import java.lang.reflect.Method;
/**
 * 反射的invoke方法！
 * @author Au
 *
 */
public class ReflecttionTest {
	public static void main(String[] args) {
		try {
			Class c=Class.forName("java.util.HashSet");
			Object o=c.newInstance();
			Method[] methods=c.getDeclaredMethods();
			for(Method method:methods){
				System.out.println(method);
			}
			Method m1=c.getMethod("add", Object.class); //类对象调用getMethod方法  通过传入参数名 和父类的类对象。获得指定方法名的方法
			m1.invoke(o, "cyq");	//方法调用invoke 通过传入对象 和参数  其实就是 对象调用该方法传入 指定参数。
			m1.invoke(o, "hello");
			m1.invoke(o, "java");
			System.out.println(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
