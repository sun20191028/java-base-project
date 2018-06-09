package com.zzl.jThread.jaThread.ThreadTest2;

public class ExecutorImpl extends Thread implements Executor{
	 private Task task;  
     private Object lock = new Object();  
     //private boolean loop = true;  
     public ExecutorImpl(){}  
     public Task getTask() {  
         return this.task;  
     }  

     public void setTask(Task task) {  
         this.task = task;  
     }  
     public void startTask(){  
         //System.out.println("start here");  
         synchronized(lock){  
             lock.notify();  
         }  
     }  
     public void run(){  
         //get a task if any  
         //then run it  
         //then put self to pool  
//         while(!isShut){  
//             synchronized(lock){  
//                 try {  
//                     lock.wait();//wait for resource  
//                 } catch (InterruptedException e) {  
//                     e.printStackTrace();  
//                 }  
//             }  
//             getTask().execute();//execute the task  
//             synchronized(pool){//put it self to the pool when finish the task  
//                 pool.addFirst(ExecutorImpl.this);  
//                 pool.notifyAll();  
//             }  
//         }  
     }  
}
