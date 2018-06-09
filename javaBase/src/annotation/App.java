package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 程序入口
 * @author liang
 *
 */

public class App {
	
	public static void main(String[] args) {
		
//		try {
//			process("annotation.MyTest");
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		try {
			prase("annotation.MyTest");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void prase(String clazz) throws ClassNotFoundException{
		Class clazzc = Class.forName(clazz);
		Annotation[] annotations = clazzc.getAnnotations();//这个得到的是类上的所有注解
		for (Annotation annotation : annotations) {
//			if(annotation.annotationType())
			System.out.println(annotation.annotationType());
		}
		
		
		
	}
	
	public static void process(String clazz) throws SecurityException, ClassNotFoundException{
		int passed = 0 ;
		int failed = 0 ;
		Class clazzc = Class.forName(clazz);
		Annotation annotationClass = clazzc.getAnnotation(AnnotationClass.class);
		if(null != annotationClass){
			System.out.println(annotationClass.annotationType());
		}else{
			System.out.println(clazz + "没有类注释");
		}
		for (Method m  : clazzc.getMethods()) {
			if(m.isAnnotationPresent(Testable.class)){
				Testable cl = m.getAnnotation(Testable.class);//这个得到的是 m方法上的 指定注解
				Annotation[] annots = m.getAnnotations(); //这个是得到m方法上的 所有注解
				
				try {
					m.invoke(null);
					passed ++ ;
				} catch (Exception e) {
					failed ++ ;
					System.out.println(m + " 运行异常 ：" + e.getCause());
				} 
				
				
			}
		}
		System.out.println("共运行了：" + (passed + failed) + "个方法，其中 ：\n "
				+ "失败了： " + failed + "个，\n"
				+ "成功了：" + passed + "个！" );
	}
}	
