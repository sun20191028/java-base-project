package com.base.basic.socket.chapter3.tcp2;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GlobalVariable {
	
	public static ConcurrentHashMap<Integer,String> serverDuty = new ConcurrentHashMap();
	public static ConcurrentLinkedQueue<String> clientDuty = new ConcurrentLinkedQueue<String>();
	
	
}
