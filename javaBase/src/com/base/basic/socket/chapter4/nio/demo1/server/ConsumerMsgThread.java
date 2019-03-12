package com.base.basic.socket.chapter4.nio.demo1.server;

import com.alibaba.fastjson.JSON;
import com.base.basic.socket.chapter4.nio.demo1.entity.DutyEntity;
import com.base.basic.socket.chapter4.nio.demo1.entity.SocketChannelWrapper;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;

public class ConsumerMsgThread extends Thread{
	
	
	
	public void run(){
		try {
			for(;;){
				DutyEntity duty = GlobalVariable.dutys.poll();
				for(; duty != null; duty = GlobalVariable.dutys.poll()){
					duty.name =  "server response : " +duty.name;
					
					SocketChannelWrapper wrapper = GlobalVariable.mapSocketChannel.get(duty.channel.socket().getPort());
					String response = JSON.toJSONString(duty);
//					System.out.println(response);
					wrapper.queue.add(response);
				}
//				System.out.println("consumer...");
				Thread.currentThread().sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
