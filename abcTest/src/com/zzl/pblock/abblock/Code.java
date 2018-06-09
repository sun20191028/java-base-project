package com.zzl.pblock.abblock;

public class Code {
	{
		System.out.println("Code的构造块");
	}

	static {
		System.out.println("Code的静态代码块");
	}

	public Code() {
		System.out.println("Code的构造方法");
	}
	
//	int i=6;
	{
		i=4;
	}
	int i=6;
	public static void main(String[] args) {
		System.out.println(new Code().i);
	}
}
