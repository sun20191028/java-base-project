package com.base.basic.socket.chapter3.tcp2.moniter;

import java.util.concurrent.ConcurrentHashMap;





import com.alibaba.fastjson.JSON;
import com.base.basic.socket.chapter3.tcp2.GlobalVariable;

public class ReceiveDataMoniter extends Thread{
	
	public void run(){
		StringBuffer stb = new StringBuffer();
		
		ConcurrentHashMap<Integer, String> duty = GlobalVariable.serverDuty;
		for (; ; ) {
			System.out.println("接收数据：" + JSON.toJSONString(duty));
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
