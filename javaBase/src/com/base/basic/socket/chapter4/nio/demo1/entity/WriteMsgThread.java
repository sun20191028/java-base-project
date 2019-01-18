package com.base.basic.socket.chapter4.nio.demo1.entity;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;
import com.base.basic.socket.chapter4.nio.demo1.util.Tool;


public class WriteMsgThread implements Runnable {
	
	private SelectionKey sk;
	
	public WriteMsgThread(SelectionKey sk){
		this.sk = sk;
	}
	
	
	public void run(){
		SocketChannel sc = (SocketChannel) sk.channel();
		SocketChannelWrapper wrapper = GlobalVariable.mapSocketChannel.get(sc.socket().getPort());
		String resp = "";
		while((resp = wrapper.queue.poll()) != null){
			try {
				System.out.println("server write == " + resp);
				Tool.writeChannel(wrapper.channel, GlobalVariable.charset, resp);
				sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				GlobalVariable.isNeedWait.set(false);;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
