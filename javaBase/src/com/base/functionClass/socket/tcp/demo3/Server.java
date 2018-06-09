package com.base.functionClass.socket.tcp.demo3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 所以设计思路可以先定为 
 * 1、服务端启动服务
 * 2、服务端 接收连接，并监测连接总数和连接的ip等相关信息。
 * 3、创立线程处理接收的连接（注意提供相关方法支持。
 * 
 * @author liang
 *
 */
public class Server {
	
//	private String host;
	/*
	 * 记录日志
	 */
	private final static Logger logger = Logger.getLogger(Server.class);
	/* 服务端监听端口 */
	private int port;
	/* 服务器套接字 */
	private ServerSocket serverSocket;
	private ExecutorService executor = Executors.newFixedThreadPool(80);
	/*  */
	public static List<Socket> listSocket = new ArrayList<>();
	
	public Server(int port){
		this.port = port;
	}
	
	public void startServerSocket(){
		try {
			logger.info("创建socket服务开始----------------");
			
			serverSocket = new ServerSocket(port);
			
			logger.info("socket服务创建成功---------开始监听端口请求"+port);
			logger.info("启动socket服务 ----   before accept------");
		} catch (IOException e) {
			logger.error("启动socket 服务失败--原因"+e.getMessage());
		}
		while(true){
			try {
				Socket socket = serverSocket.accept();
				if (socket != null){
					
					listSocket.add(socket);
					executor.execute(new ServerSocketWorkThread(socket));
					
				}else{
					logger.info("接收链接socket为空");
					continue;
				}
			} catch (IOException e) {
				logger.error("socket服务处理错误:"+e.getMessage());
			}
		}
		
	}
}
