package com.base.functionClass.thread.jThread.threadGroup.threadGroup2;

import java.io.*;

public class ThreadGroupDemo {
	public static void main(String[] args) {
		ThreadGroup threadGroup1 =
		// 这是匿名类写法
		new ThreadGroup("group1") {
			// 继承ThreadGroup并重新定义以下方法
			// 在线程成员抛出unchecked exception
			// 会执行此方法
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName() + ": " + e.getMessage());
			}
		};
		// 这是匿名类写法
		Thread thread1 =
		// 这个线程是threadGroup1的一员
		new Thread(threadGroup1, new Runnable() {
			public void run() {
				// 抛出unchecked异常
				throw new RuntimeException("测试异常");
			}
		});

		thread1.start();
	}
}
