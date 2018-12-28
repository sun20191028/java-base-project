package com.base.functionClass.thread.jThread.jaThread.threadTest;

public class WorkTaskImp implements WorkTask {

	 protected String param;
	    public WorkTaskImp(){
	    }
	    public WorkTaskImp(String param){
	        this.param=param;
	    }
	    public void runTask() {
	        // TODO Auto-generated method stub
	        System.out.println("=============>Task0"+this.param);
	    }

	    public void cancelTask() {
	        // TODO Auto-generated method stub
	       
	    }

	    public int getProgress() {
	        // TODO Auto-generated method stub
	        return 0;
	    }
	    

}
