package com.zzl.jThread.jbGuardThread.first;

public class ThreadDemo implements Runnable{
     public void run() {
//	  while (true) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         }
//	   }    
	}
}
