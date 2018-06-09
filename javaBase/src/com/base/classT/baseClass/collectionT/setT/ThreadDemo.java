package com.base.classT.baseClass.collectionT.setT;

public class ThreadDemo extends Thread{

	@Override
	public void run(){
		for (int i = 0; i < 50; i++) {
			UnSafeHashSet.hashSet.add(this.getName() + " : "+i);
		}
	}
}
