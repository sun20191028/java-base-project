package com.base.functionClass.thread;

/**
 * 启动线程 线程组的  树形结构。
 * system线程组中	有main线程
 * 				有main线程组
 * 					main线程组中有main主线程
 * 
 * @author liang
 *
 */
public class Demo1 {
	public static void main(String[] args) {
		
//		RunnableTest rt = new RunnableTest();
//		new Thread(rt).start();;
		Thread th = Thread.currentThread();
//		System.
//		Runtime.getRuntime();
//		Thread.
		ThreadGroup currGroup = Thread.currentThread().getThreadGroup();
		/**
		 * 在当前线程组中创建 两个线程组。 成为 main线程组的子线程组。
		 */
		ThreadGroup group2 = new ThreadGroup("**22**");
		ThreadGroup group3 = new ThreadGroup("**33**");
		
		System.out.println("当前线程组为："+currGroup.getName());
		
		Thread[] currThreadArray = new Thread[currGroup.activeCount()];
		ThreadGroup[] currGroupArray = new ThreadGroup[currGroup.activeGroupCount()];
		
        int actualSize = currGroup.enumerate(currThreadArray); //当前线程组 含有的 所有子线程
        System.out.println(currGroup.getName()+"线程组含有的子线程为：\r\n[");
		for (Thread thread : currThreadArray) {
			System.out.println("\t"+thread.getName());
		}
		System.out.println("]");
		
		int groupSize = currGroup.enumerate(currGroupArray);//当前线程组 含有的 所有子线程组
		System.out.println(currGroup.getName()+"线程组含有的子线程组为：\r\n[");
		for (ThreadGroup threadGroup : currGroupArray) {
			System.out.println("\t"+threadGroup.getName());
		}
		System.out.println("]");
		
		while(currGroup.getParent() != null){
			currGroup = currGroup.getParent();
		}
		
		ThreadGroup rootGroup = currGroup;
		
		System.out.println("当前线程组的顶级线程组为："+rootGroup.getName());
		System.out.println("根线程组的子线程个数："+rootGroup.activeCount());
		
		Thread[] rootThreadArray = new Thread[rootGroup.activeCount()];
		ThreadGroup[] rootGroupArray = new ThreadGroup[rootGroup.activeGroupCount()];
        // 获取根线程组的所有线程    
        int actual = rootGroup.enumerate(rootThreadArray); 
        System.out.println(rootGroup.getName()+"线程组含有的子线程为：\r\n[");
		for (Thread thread : rootThreadArray) {
			System.out.println("\t"+thread.getName()+"---"+thread.isDaemon());
		}
		System.out.println("]");
		int groupSize0 = rootGroup.enumerate(rootGroupArray);
		System.out.println(rootGroup.getName()+"的子线程组为2：\r\n[");
		for (ThreadGroup threadGroup : rootGroupArray) {
			System.out.println("\t"+threadGroup.getName());
		}
		System.out.println("]");
//		System.out.println(tg.isDaemon());
		
		System.out.println("\r\n\r\n##################");
		Runtime run = Runtime.getRuntime();
		System.out.println(run.availableProcessors()+","+run.totalMemory()/1024/8+","+run.freeMemory()/1024/8);
		
		/**
		 * Reference Handler 
		 * Finalizer 
		 * Signal Dispatcher 
		 * Attach Listener
		 * 也就是说这几个线程是 属于 system根线程组的 都在javaw进程中。
		 * 
		 */
	}
}
