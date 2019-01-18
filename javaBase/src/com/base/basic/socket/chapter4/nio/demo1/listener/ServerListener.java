package com.base.basic.socket.chapter4.nio.demo1.listener;

import java.util.Map.Entry;

import com.base.basic.socket.chapter4.nio.demo1.entity.SocketChannelWrapper;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;

/**
 * 通过监控，发现系统运行良好
 * @author liangpro
 *
 */
public class ServerListener extends Thread{

	public void run(){
		
		for(;;){
			
			System.out.println("未处理的任务 【" + GlobalVariable.dutys.size() +"】" );
			for (Entry<Integer, SocketChannelWrapper> entry : GlobalVariable.mapSocketChannel.entrySet()) {
				System.out.println("端口【"+entry.getKey() + "】，未发送的任务有 ："+ entry.getValue().queue.size() + "个");
			}
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
