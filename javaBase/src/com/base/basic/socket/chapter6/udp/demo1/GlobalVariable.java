package com.base.basic.socket.chapter6.udp.demo1;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GlobalVariable {
	
	public static LinkedBlockingQueue<String> serverDuty = new LinkedBlockingQueue(1000);
	
	
}
