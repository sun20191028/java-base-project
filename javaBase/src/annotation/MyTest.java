package annotation;

@AnnotationClass
public class MyTest {
	@Testable
	public static void m1() {

	}

	public static void m2() {

	}
	@Testable
	public static void m3() {
		throw new IllegalArgumentException("参数出错了");
	}

	public static void m4() {

	}
	@Testable
	public static void m5() {

	}
	
	public static void m6() {

	}
	
	@Testable
	public static void m7() {
		throw new RuntimeException("程序业务出错");
	}
}
