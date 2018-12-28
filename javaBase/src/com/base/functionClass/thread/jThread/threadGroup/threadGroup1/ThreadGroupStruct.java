package com.base.functionClass.thread.jThread.threadGroup.threadGroup1;

/**
 * 		system{
 * 			main{
 * 				main
 * 			}
 * 			Reference Handler
 * 			Finalizer
 * 			Signal Dispatcher
 * 			Attach Listener
 * 			main
 * 		}
 * 
 * 
 * @author liangpro
 */
public class ThreadGroupStruct {
	
	public static void main(String[] args) {
		Thread m=Thread.currentThread();
		ThreadGroup mGroup=m.getThreadGroup(); //得到当前线程的线程组。
		ThreadGroup parentGroup1 = mGroup.getParent();
		System.out.println(mGroup.getName());
		System.out.println(parentGroup1.getName());
		
		printThread(mGroup);
		printThreadGroup(mGroup);
		printThread(parentGroup1);
		printThreadGroup(parentGroup1);
		
		
	}
	
	public static void printThread(ThreadGroup group){
		StringBuffer buffer = new StringBuffer();
		Thread[] arr = new Thread[group.activeCount()];
		group.enumerate(arr);							//将线程组中活动的线程复制到指定数组中。
		buffer.append(group.getName() + "：含有的子线程{\r\n\t");
		for (Thread thread : arr) {
			buffer.append(thread.getName() + " , ");		//输出:main 和 myThread
		}
		buffer.append("\r\n}");
		System.out.println(buffer);
	}
	public static void printThreadGroup(ThreadGroup group){
		StringBuffer buffer = new StringBuffer();
		int num = group.activeGroupCount();
		ThreadGroup[] groupArray = new ThreadGroup[num];
		group.enumerate(groupArray);
		buffer.append(group.getName() + "：含有的子线程组{\r\n\t");
		for (ThreadGroup g : groupArray) {
			buffer.append(g.getName() + " , ");		//输出:main 和 myThread
		}
		buffer.append("\r\n}");
		System.out.println(buffer);
	}
}
