package annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(value=RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.RUNTIME)  //两种都可以
@Target(ElementType.METHOD)
public @interface Testable {
//	Annotation
//	Object
	
	
	
}
