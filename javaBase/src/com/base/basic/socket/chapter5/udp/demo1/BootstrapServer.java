package com.base.basic.socket.chapter5.udp.demo1;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * udp 是不可靠的 非连接 性的通信。
 * 发送
 * 		只需要确定 使用哪个端口发送 数据报（新建DatagramSocket）
 * 		然后发送一个 数据报（新建数据报，数据报中包含 发送数据和 目标InetAddress，ip）  -- 只是发送出去，不管接收端有没有接收，
 * 			** 没有接收的不缓存。tcp没有接收的可以续接，不会丢失数据，而udp，后续注册的接收端，接收不到发送到之前广播的数据 **
 * 接收
 * 		指定在哪个端口接收（新建DatagramSocket）
 * 		然后DatagramSocket接收数据报（一个空的DatagramPacket）
 * 		
 * @author liangpro
 *
 */
public class BootstrapServer {

	public static void main(String[] args) {
		try {
			/**
			 * 创建消息线程，比如获取需要发布的 天气信息
			 */
			CreateMsgJob job = new CreateMsgJob();
			job.start();
			
			/**
			 * 接收注册线程， 如果客户端需要接收广播，需要先把自己的客户端信息 注册到 广播服务器
			 */
			BroadCastAcceptThread acceptThread = new BroadCastAcceptThread(new DatagramSocket(3333));//接收的socket 一定要指定端口，否则发送端不知道往哪里发
			acceptThread.start();
			
			/**
			 * 将需要广播的消息，发送到 已经注册了的客户端。
			 */
			BroadCaseSendMsgThread sendMsgThread = new BroadCaseSendMsgThread(new DatagramSocket());
			sendMsgThread.start();
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
