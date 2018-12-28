package com.base.functionClass.thread.jThread.jaThread.forth;

public class ThreadLocalTest {
	public static void main(String[] args) {
		Account at=new Account("初始名");
		new MyTest(at,"线程甲").start(); //线程名是线程
		new MyTest(at,"线程乙").start(); //ThreadLocal.get是获得线程局部变量中当前线程副本的值。 首先是线程中的       局部变量。
		//这两个则是对应 线程当中的内容来说。
		//Thread.currentThread() 得到的 是当前线程
		//Thread.currentThread().getName() 得到的是当前线程的名称   ==这两个都是对于线程级别的来说
		
		/**
		 * 那么现在的问题是，如果不用ThreadLocal 如何获取线程中的变量值。=====》直接通过对象.get()不就获取到了
		 * 而通过ThreadLocal维护的变量，则可以直接通过 ThreadLocal的对象.get() 直接获得被修饰变量的值。
		 * 
		 * 使用ThreadLocal只是维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本
		 * 也许把它命名为ThreadLocalVariable更容易让人理解一些。Thread的局部变量====》线程局部变量。作用范围仅仅是在线程内部。
		 * 
		 * 一个对象可以同时在两个线程直接被使用，在此处对象的范围大于线程。实例变量的范围大于线程局部变量。
		 */
	}
}
