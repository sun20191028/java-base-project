package com.base.functionClass.socket.tcp.demo3;

/**
 * 负责
 * 服务器启动 和 客户端开启。
 * @author liang
 *
 */
public class ServerMain {
	public static void main(String[] args) {
		Server  server = new Server(30000);
		server.startServerSocket();
		
		
	}
}
