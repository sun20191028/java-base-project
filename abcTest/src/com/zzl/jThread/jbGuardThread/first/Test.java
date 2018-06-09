package com.zzl.jThread.jbGuardThread.first;

public class Test {
	public static void main(String[] args) {
		Thread daemonThread = new Thread(new ThreadDemo());
		daemonThread.setName("测试thread");
		// 设置为守护进程
//	        daemonThread.setDaemon(true);
	        daemonThread.start();
	        System.out.println("isDaemon = " + daemonThread.isDaemon());
//	        Thread t = new Thread(new ThreadDemo());
//	        t.start();
	}
}
