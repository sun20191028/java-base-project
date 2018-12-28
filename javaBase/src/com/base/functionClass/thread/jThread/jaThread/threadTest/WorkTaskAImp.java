package com.base.functionClass.thread.jThread.jaThread.threadTest;

public class WorkTaskAImp implements WorkTask {

	protected String param;
    public WorkTaskAImp(){
    }
    public WorkTaskAImp(String param){
        this.param=param;
    }
    public void runTask() {
        // TODO Auto-generated method stub
       // Log.v("=============>Task1", this.param);
        System.out.println("=============>Task1"+this.param);
    }

    public void cancelTask() {
        // TODO Auto-generated method stub
       
    }

    public int getProgress() {
        // TODO Auto-generated method stub
        return 0;
    }

}
