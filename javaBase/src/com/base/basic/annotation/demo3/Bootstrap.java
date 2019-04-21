package com.base.basic.annotation.demo3;

import java.io.File;

import com.base.basic.annotation.demo3.annotation.Component;
import com.base.basic.annotation.demo3.engine.AnnotationConfigApplicationContext;
import com.base.basic.annotation.demo3.test.service.IService;
import com.base.basic.annotation.demo3.test.service.ServiceImpl;

public class Bootstrap {
	
	public static void main(String[] args) throws Exception {
		
		File file = new File(".");
		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		
		
		Class c = Class.forName("com.base.basic.annotation.demo3.test.service.ServiceImpl");
		
		Class c1 = ServiceImpl.class;
		Component co1 = (Component) c1.getAnnotation(Component.class);
		Component co = (Component) c.getAnnotation(Component.class);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		IService service = (IService) context.getBean(ServiceImpl.class);
		System.out.println(service.getFlag());
		
		
	}
	
}
