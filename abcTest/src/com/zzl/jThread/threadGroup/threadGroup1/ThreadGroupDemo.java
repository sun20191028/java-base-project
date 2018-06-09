package com.zzl.jThread.threadGroup.threadGroup1;

public class ThreadGroupDemo {
	public static void main(String[] args) {
		Thread m=Thread.currentThread();
//		System.out.println(m.getPriority()+"--");
		ThreadGroup mGroup=m.getThreadGroup(); //得到当前线程的线程组。
		
		/**
		 * 获得线程组的信息
		 */
//		System.out.println(mGroup);
//		System.out.println(mGroup.activeCount());
//		System.out.println(mGroup.activeGroupCount());
//		System.out.println(mGroup.getName());
//		System.out.println(mGroup.getMaxPriority());
//		System.out.println(mGroup.getParent());
//		System.out.println(mGroup.getParent().getName());
//		System.out.println(mGroup.isDaemon());
		
//		mGroup.list();

		/**
		 * 之所以用这个，会出问题，那是意外thread中的start方法运行太快了，还没来得及运行线程组的方法，就结束了
		Thread t1=new Thread(mGroup,"myThread");
		t1.start();
		ThreadGroup parent=mGroup.getParent();//得到当前线程的父线程组
		ThreadGroup sun=new ThreadGroup(mGroup, "main线程组的子线程组");
		Thread s=new Thread(sun,"main的子线程组中的线程");
		s.start();
		mGroup.list();
		 */
		TestThread t1=new TestThread(mGroup,"myThread");
		t1.start();
		ThreadGroup parent=mGroup.getParent();//得到当前线程的父线程组
		ThreadGroup sun=new ThreadGroup(mGroup, "main线程组的子线程组");
		TestThread s=new TestThread(sun,"main的子线程组中的线程");
		s.start();
//		System.out.println(t1.getPriority()+"--");
		mGroup.list();
//		
		//通过线程组可以知道,这个线程组中有多少条线程是运行着的,有多少条线程,每条线程是什么都可以获取到.
//		Thread[] arr = new Thread[mGroup.activeCount()];
//		m.enumerate(arr);								//将线程组中活动的线程复制到指定数组中。
//		for (Thread thread : arr) {
//		    System.out.println(thread.getName());		//输出:main 和 myThread
//		}
		
//		ThreadGroup parent = mGroup.getParent();//获得父线程组.
//      parent.list();
        /*   main线程的父线程组中有system和垃圾回收相关的线程..
            java.lang.ThreadGroup[name=system,maxpri=10]  //进程中最顶级的线程组 system
                Thread[Reference Handler,10,system]		//负责处理引用
                Thread[Finalizer,8,system]		//负责调用Finalizer方法
                Thread[Signal Dispatcher,9,system]  //负责分发内部事件
                Thread[Attach Listener,5,system]
                java.lang.ThreadGroup[name=main,maxpri=10]  //主线程的线程组
                    Thread[main,5,main] 		//主线程中的线程组中的主线程
         */
		
	}
}
