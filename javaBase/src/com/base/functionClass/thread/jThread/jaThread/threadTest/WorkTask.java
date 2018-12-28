package com.base.functionClass.thread.jThread.jaThread.threadTest;

public interface WorkTask {
	public void runTask();// 执行工作任务
	// public int compareTo(mJob job);

	public void cancelTask();

	public int getProgress();
}
