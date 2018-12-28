package com.base.basic.socket.chapter3.tcp2.moniter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.base.basic.socket.chapter3.tcp2.SocketHelpThread;

/**
 * socket 监控线程
 * @author liangpro
 *
 */
public class SocketThreadMoniter extends Thread{
	public Map<Integer,SocketHelpThread> syncSet = Collections.synchronizedMap(new HashMap<Integer, SocketHelpThread>());
	
	public volatile boolean isRun = true;
	
	public void moniterThread(int num,SocketHelpThread thread){
		syncSet.put(num, thread);
	}
	
	
	public void run(){
		while(isRun){
			System.out.println(syncSet.toString());
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
