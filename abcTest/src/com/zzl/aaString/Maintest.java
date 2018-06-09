package com.zzl.aaString;

public class Maintest {
//	run as ==> run configurations ==> arguments ==> program arguments里面填 参数 以空格分开 ==>apply ==> run
	public static void main(String[] args) {
        System.out.println(args.length);
        for(int i=0;i<args.length;i++)
        {
            System.out.println("--"+args[i]);
		}
	}
}
