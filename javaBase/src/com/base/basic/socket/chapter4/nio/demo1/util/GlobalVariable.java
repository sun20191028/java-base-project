package com.base.basic.socket.chapter4.nio.demo1.util;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.base.basic.socket.chapter4.nio.demo1.entity.DutyEntity;
import com.base.basic.socket.chapter4.nio.demo1.entity.SocketChannelWrapper;


public class GlobalVariable {
	
	public static Selector selector;
	public static Selector rwSelector; //读写监控selector
	public static Charset charset = Charset.forName("UTF-8");
	
	public static volatile AtomicBoolean isNeedWait = new AtomicBoolean(true);// 如果读到，或者写了数据，则不需要休眠，因为通道不是可读状态，都是可写状态，所以需要减速控制
	
	public static ConcurrentHashMap<Integer, SocketChannelWrapper> mapSocketChannel = new ConcurrentHashMap<Integer, SocketChannelWrapper>();
	public static ConcurrentLinkedQueue<SocketChannel> socketChannelQueue = new ConcurrentLinkedQueue<SocketChannel>();
	
	public static ConcurrentLinkedQueue<DutyEntity> dutys = new ConcurrentLinkedQueue<DutyEntity>();
	
	
	// 使用自定义的线程池。
	private static BlockRejectedExecutionHandler handler = new BlockRejectedExecutionHandler();
	public static ThreadPoolExecutor threadPoolRead = new ThreadPoolExecutor(5, 10, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100),Executors.defaultThreadFactory(), handler);
	public static ThreadPoolExecutor threadPoolWrite = new ThreadPoolExecutor(5, 10, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100),Executors.defaultThreadFactory(), handler);
	
	
	
	static{
		try {
			selector = Selector.open();
			rwSelector = Selector.open();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
