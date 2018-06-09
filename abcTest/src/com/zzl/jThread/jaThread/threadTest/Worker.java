package com.zzl.jThread.jaThread.threadTest;

public class Worker extends Thread {
	public boolean isrunning=false;
    private WorkTask nowTask; // 当前任务
    private Object threadTag;// 线程标识
    //获取线程标识key
    public Object getThreadTag() {
        return threadTag;
    }      
    public synchronized void setWorkTask(WorkTask task) {
        this.nowTask = task;
    }

    public synchronized void setIsRunning(boolean flag) {
        this.isrunning = flag;
        if (flag) {
            this.notify();
        }
    }

    public Worker(Object key) {
        System.out.println("正在创建工作线程...线程编号" + key.toString());
        this.threadTag = key;
        // this.state=CREATESTATE;
    }

    public boolean getIsrunning() {
        return isrunning;
    }

    public synchronized void run() {
        System.out.println("工作线程" +  this.getThreadTag()  + "初始化成功");
        while (true) {
            if (!isrunning) {
                try {
                    System.out.println("工人" + this.getThreadTag() + "任务完成回归线程池");
                    this.wait();
                } catch (InterruptedException e) {
                    System.out.println("线程被阻挡");
                    e.printStackTrace();
                }
            } else {
                //try {
                    nowTask.runTask();
                    setIsRunning(false);
                    System.out.println("工人" +this.getThreadTag()  + "开始工作");
                    //this.sleep(3000);
            //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                
                //this.notify();
                //break;
            }
        }
    }
}
