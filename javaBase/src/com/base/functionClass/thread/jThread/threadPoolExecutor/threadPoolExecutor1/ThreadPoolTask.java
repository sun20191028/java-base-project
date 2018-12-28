package com.base.functionClass.thread.jThread.threadPoolExecutor.threadPoolExecutor1;

import java.io.Serializable;

public class ThreadPoolTask implements Runnable, Serializable {  
	  
    private Object attachData;  
  
    ThreadPoolTask(Object tasks) {  
        this.attachData = tasks;  
    }  
  
    public void run() {  
          
        System.out.println("开始执行任务：" + attachData);  
        try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        attachData = null;  
    }  
  
    public Object getTask() {  
        return this.attachData;  
    }
}
