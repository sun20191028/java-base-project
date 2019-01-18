package com.base.basic.socket.chapter4.nio.demo1.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.base.basic.socket.chapter4.nio.demo1.entity.ReadMsgThread;
import com.base.basic.socket.chapter4.nio.demo1.entity.WriteMsgThread;
import com.base.basic.socket.chapter4.nio.demo1.util.GlobalVariable;

public class ServerReadWrite extends Thread{
	public Selector rwSelector;
	public volatile boolean serverIsRun = false;
	
	public void init() throws IOException{
		rwSelector = GlobalVariable.rwSelector;
		serverIsRun = true;
	}
	
	public void run(){//这里面是不存在并发的。
		try {
			while (serverIsRun) {
//				SocketChannel sc = null;
//				while((sc=GlobalVariable.socketChannelQueue.poll()) !=null){
//					sc.register(rwSelector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
//				}
//				
//				if(rwSelector.select(2000) > 0){
//					for (SelectionKey sk : rwSelector.selectedKeys()) {
//						rwSelector.selectedKeys().remove(sk);
//						doEvent(sk);
//					}
//				}
				
				int num = rwSelector.select(2000);
				//但是注册selector 必须在select()方法之后，因为select方法会对 publicKeys加锁，
				//publicKeys 必须加锁才能 新增/删除 键集，publicKeys是set集合，是线程不安全的。必须加锁才能修改。所以注册需要在加锁之后
				SocketChannel sc = null;
				while((sc=GlobalVariable.socketChannelQueue.poll()) !=null){
					sc.register(rwSelector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				}
				
				if(num > 0) {
					Iterator<SelectionKey> it = rwSelector.selectedKeys().iterator();
					while(it.hasNext()) {
						doEvent(it.next());
						it.remove();
					}
				}
				if(GlobalVariable.isNeedWait.get()){
					sleep(1000);
				}
				GlobalVariable.isNeedWait.set(true);
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doEvent(SelectionKey sk) throws IOException{
		if(sk.isReadable()){
			ReadMsgThread readMsgThread = new ReadMsgThread(sk);
			GlobalVariable.threadPoolRead.execute(readMsgThread);
		
		}else if(sk.isWritable()){
			WriteMsgThread writeMsgThread = new WriteMsgThread(sk);
			GlobalVariable.threadPoolWrite.execute(writeMsgThread);
			
		}
	}
	
	
}
