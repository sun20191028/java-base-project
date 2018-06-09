package com.zzl.pblock.abblock;
/**
 * 局部代码块： 在方法或语句中出现的{}就称为普通代码块
 * 普通代码块和一般的语句执行顺序由他们在代码中出现的次序决定
 * @author Au
 *
 */
public class CodeBlock01 {
	int i;
	public void show(){
		
//		i=8;
//		{
//			i=5;
//		}
		System.out.println(i);
	}
	public static void main(String[] args) {
		new CodeBlock01().show();
		{
			int x = 3;
			System.out.println("1,普通代码块内的变量x=" + x);
		}

		int x = 1;
		System.out.println("主方法内的变量x=" + x);

		{
			int y = 7;
			System.out.println("2,普通代码块内的变量y=" + y);
		}
	}
}

/*
 * 运行结果： 
 * 1,普通代码块内的变量x=3 
 * 主方法内的变量x=1 
 * 2,普通代码块内的变量y=7
 */
