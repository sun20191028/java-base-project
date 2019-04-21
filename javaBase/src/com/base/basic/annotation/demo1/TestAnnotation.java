package com.base.basic.annotation.demo1;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention 只能修饰 Annotation定义，指Annotation 可以保留多长时间
//@Retention(value = RetentionPolicy.CLASS)//运行java程序时，jvm不能获取Annotation信息，这是默认值
@Retention(value = RetentionPolicy.RUNTIME)// 运行代码是，通过反射可以获取Annotation信息，
//@Retention(value = RetentionPolicy.SOURCE)// 只保留在源码中，编译时直接丢弃

//@Target 只能修饰 Annotation定义，表示此Annotation 可以修饰哪些单元
//@Target(value = ElementType.ANNOTATION_TYPE)// 表示此Annotation只能修饰 Annotation
//@Target(value = ElementType.CONSTRUCTOR)// 表示此Annotation只能修饰 构造器
//@Target(value = ElementType.FIELD)// 表示此Annotation只能修饰 成员变量
//@Target(value = ElementType.LOCAL_VARIABLE)// 表示此Annotation只能修饰 局部变量
//@Target(value = ElementType.METHOD)// 表示此Annotation只能修饰 方法
//@Target(value = ElementType.PACKAGE)// 表示此Annotation只能修饰 包定义
//@Target(value = ElementType.PARAMETER)// 表示此Annotation只能修饰 参数
//@Target(value = ElementType.TYPE)// 表示此Annotation只能修饰 类、接口（包括注解类型）、枚举
@Target({ElementType.TYPE,ElementType.METHOD})

@Documented// 表示此Annotation 类，将被javadoc工具提取成文档
@Inherited// 表示此Annotation类，具有继承性

public @interface TestAnnotation {

}



