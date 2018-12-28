package com.base.Unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

// 方法一：我们可以令我们的代码“受信任”。运行程序时，使用bootclasspath选项，指定系统类路径加上你使用的一个Unsafe路径
//java -Xbootclasspath:/usr/jdk1.7.0/jre/lib/rt.jar:. com.mishadoff.magic.UnsafeClient
/**
 * 获取Unsafe 的实例。
 * @author liangpro
 *
 */
public class UnsafeUtil {
	
	public static Unsafe getUnsafeInstance() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafeInstance.setAccessible(true);
		return (Unsafe) theUnsafeInstance.get(Unsafe.class);
	}
	
	public static Unsafe getUnsafe() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (Unsafe) field.get(null);
	}
	
}
