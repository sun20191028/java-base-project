package com.zzl.pblock.abblock;
/**
 * 静态代码块:在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。
 * 每个静态代码块只会执行一次。
 * 由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
 * 注意：1 静态代码块不能存在于任何方法体内。2 静态代码块不能直接访问静态实例变量和实例方法，需要通过类的实例对象来访问。
 * @author Au
 */
/**
 * 静态代码块 >主方法
 * 构造块>构造方法
 * 局部代码块或普通代码块  一般由的语句执行顺序由他们在代码中出现的次序决定
 * @author Au
 *
 */
public class CodeBlock03 {
	{
		System.out.println("CodeBlock03的构造块");
	}

	static {
		System.out.println("CodeBlock03的静态代码块");
	}

	public CodeBlock03() {
		System.out.println("CodeBlock03的构造方法");
	}

	public static void main(String[] args) {
		System.out.println("CodeBlock03的主方法");
		new Code();
		new Code();
		new CodeBlock03();
		new CodeBlock03();
	}

	
}
/*
 * CodeBlock03的静态代码块 
 * CodeBlock03的主方法
 * Code的静态代码块
 * Code的构造块 
 * Code的构造方法 
 * Code的构造块
 * Code的构造方法 
 * CodeBlock03的构造块 
 * CodeBlock03的构造方法 
 * CodeBlock03的构造块 
 * CodeBlock03的构造方法
 */
