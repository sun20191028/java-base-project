package com.base.basic.socket.chapter4.nio.demo1.entity;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;
import com.base.basic.socket.chapter4.nio.demo1.util.Tool;

public class ReadMsgThread implements Runnable{
	
	public SelectionKey sk;
	
	public ReadMsgThread(SelectionKey sk){
		this.sk = sk;
	}
	
	
	@Override
	public void run() {
		try {
			SocketChannel channel = (SocketChannel)sk.channel();
			String msg = Tool.readChannel(channel, GlobalVariable.charset);
			System.out.println("server read : " + msg);
			sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);//读完数据之后，需要将SelectionKey 设置成准备监听下一次读取
			DutyEntity duty = JSON.parseObject(msg, DutyEntity.class);
//			DutyEntity duty = new DutyEntity();
//			duty.msg = msg;
			duty.uniqueId = UUID.randomUUID().toString();//作为数据包的唯一标识,暂时没用
			duty.channel = channel;
			GlobalVariable.dutys.add(duty);
			GlobalVariable.isNeedWait.set(false);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
