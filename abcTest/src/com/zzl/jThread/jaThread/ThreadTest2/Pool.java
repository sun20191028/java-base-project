package com.zzl.jThread.jaThread.ThreadTest2;

import java.util.concurrent.Executor;

public interface Pool {
	Executor getExecutor();

	void destroy();
}
