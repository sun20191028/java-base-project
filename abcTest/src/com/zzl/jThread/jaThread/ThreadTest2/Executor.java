package com.zzl.jThread.jaThread.ThreadTest2;

public interface Executor {
	void setTask(Task task);

	Task getTask();

	void startTask();
}
