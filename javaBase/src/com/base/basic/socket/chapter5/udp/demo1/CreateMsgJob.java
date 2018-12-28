package com.base.basic.socket.chapter5.udp.demo1;

public class CreateMsgJob extends Thread{
	private String msg = "i";
	
	public void run(){
		try {
			for(;;){
				GlobalVariable.serverDuty.put(msg);
				msg = msg.concat("i");
//				System.out.println(msg);
				Thread.currentThread().sleep(3000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	
}
