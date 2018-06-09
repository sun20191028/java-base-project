package com.base.functionT.faceObject.t2;

public class Demo {
	public static void main(String[] args) {
		ThreadGroup topGroup = null;
		ThreadGroup thg = Thread.currentThread().getThreadGroup();
		while (thg != null) {  
		    topGroup = thg;  
		    thg = thg.getParent();  
		}
		System.out.println(topGroup.getName());
		int estimatedSize = topGroup.activeCount() * 2; 
		Thread[] slackList = new Thread[estimatedSize];  
		
		int actualSize = topGroup.enumerate(slackList); 
		
		
		Thread[] list = new Thread[actualSize];  
		System.arraycopy(slackList, 0, list, 0, actualSize);  
		System.out.println("Thread list size == " + list.length);  
		for (Thread thread : list) {  
		    System.out.println(thread.getName());  
		}  
	}
}
