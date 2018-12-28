package com.base.functionClass.io.ibNio.iechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.sun.xml.internal.ws.api.pipe.NextAction;

/**
 * 这个例子很经典 聊天室服务器 网络多客户端聊天室 功能1： 客户端通过Java NIO连接到服务端，支持多客户端的连接
 * 功能2：客户端初次连接时，服务端提示输入昵称，如果昵称已经有人使用，提示重新输入，如果昵称唯一，则登录成功，之后发送消息都需要按照规定格式带着昵称发送消息
 * 功能3：客户端登录后，发送已经设置好的欢迎信息和在线人数给客户端，并且通知其他客户端该客户端上线
 * 功能4：服务器收到已登录客户端输入内容，转发至其他登录客户端。
 * 
 * @author 1
 * @date Aug 22, 2016 4:02:01 PM
 */
public class ChatRoomServer {

	/** 选择器 */
	private Selector selector;

	ServerSocketChannel serverChannel;
	/***** 端口号 *****/
	private final static int PORT = 9900;

	/******* 在线统计人名或人数 ********/
	private HashSet<String> online = new HashSet<String>();

	/**** 编码 *****/
	private Charset charset = Charset.forName("UTF-8");

	/**** 用户存在提示信息 *****/
	private static String USER_EXIST = "system message: user exist, please change a name";

	/**** 相当于自定义协议格式，与客户端协商好 *****/
	private static String USER_CONTENT_SPILIT = "#@#";

	public static void main(String[] args) {
		try {
			new ChatRoomServer().init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化服务器
	 * 
	 * @author 1
	 * @throws IOException
	 */
	public void init() throws IOException {

		// 打开选择器
		this.selector = Selector.open();

		// 开启服务器端通道，并指定端口号
		/**
		 * 其实这个的设计的强大之处就在于这个ServerSocketChannel
		 * 他将服务器通道绑定到本机指定的端口上之后，不管来多少客户端连接通道。它就能建立多少 通道。 并把其注册到选择器上去，交由选择器监听。
		 * 而非阻塞就体现在选择器的监听上。来一条通道，它加入一条。不管谁的通道来信息，它都能处理。不来或是不连接它也不阻塞。
		 * 但是启动的时候没有通道建立时此线程还是会阻塞在监听通道处。
		 */
		serverChannel = ServerSocketChannel.open(); 
		// ServerSocket serverSocket = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(PORT);
		serverChannel.bind(address);
		// 设置非阻塞模式
		serverChannel.configureBlocking(false);
		// 将通道注册到选择器上
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("server is linstening...");
		Set<SelectionKey> se=selector.keys();
		System.out.println("初始化的时候 所有的SelectionKey的个数为："+se.size());
		
		try {
			while (true) {
				int nums = selector.select(); // 获得被选中的SelectionKey的个数。
				if (nums <= 0) {
					continue;
				}
				// 获得被选中的SelectionKey。
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();	//在selectionKeys上布置迭代器。
				int i = 0;
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();  //这个并不是说把迭代器指到的这个selectionKeys中的这个被选中的键删除。而是从被选中的集合中删除。变成未被选中的selectionKey。
					dealWithSelectionKey(serverChannel, key);//上面只是改变了这个selectionKey的状态，但是它所对应的通道依然存在。
					i++;
				}
				System.out.println("被选择的SelectionKey的个数为："+i);
				// SelectorThread st = new SelectorThread();
				// st.start();
				//
				// /**
				// * 主线程并不影响。
				// */
				// Scanner scan=new Scanner(System.in);
				// while(true){
				// System.out.println("请输入：");
				// System.out.println(scan.nextLine());
				// }
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private class SelectorThread extends Thread {
		public void run() {
			try {
				int j=0;
				while (true) {
					int nums = selector.select(10000); 
					if (nums <= 0) {
						j++;
						System.out.println("独自等待了"+j*10+"秒");
						continue;
					}
					Set<SelectionKey> selectionKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectionKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove(); 
						dealWithSelectionKey(serverChannel, key);
					}
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 处理当前的键值
	 */
	private void dealWithSelectionKey(ServerSocketChannel server,SelectionKey key) throws IOException {
		if (key.isAcceptable()) {
			// 接收客户端
			SocketChannel sChannel = server.accept();
			// 设置非阻塞
			sChannel.configureBlocking(false);
			Set<SelectionKey> se=selector.keys();
			System.out.println("SocketChannel注册到selector之前的所有SelectionKey的数量"+se.size());
			
			// 注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个socketchannel处理。
			sChannel.register(selector, SelectionKey.OP_READ);
			
			Set<SelectionKey> se2=selector.keys();
			System.out.println("SocketChannel注册到selector之后的所有SelectionKey的数量"+se.size());
			
			// 将此对应的channel设置为准备接收其他客户端的请求
			key.interestOps(SelectionKey.OP_ACCEPT);
			System.out.println("Server is listening from client :"+ sChannel.socket().getRemoteSocketAddress());
			sChannel.write(charset.encode("Please input your name:"));
		}
		// 处理来自客户端的数据读取请求
		else if (key.isReadable()) {
			// 得到该key对应的channel，其中有数据需要读取
			SocketChannel sc = (SocketChannel) key.channel();
			StringBuffer content = new StringBuffer();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			try {
				// 得到客户端传过来的消息
				while (sc.read(buffer) > 0) {
					buffer.flip();
					content.append(charset.decode(buffer));
				}
				// 将此对应的channel设置为准备下一次接受数据
				key.interestOps(SelectionKey.OP_READ);
			} catch (Exception e) {
				e.printStackTrace();
				key.cancel();
				sc.close();
			}
			// 如果内容不为空
			if (content.length() > 0) {
				// 拆分规则
				String[] msgArr = content.toString().split(USER_CONTENT_SPILIT);
				// 注册名字
				if (msgArr != null && msgArr.length == 1) {
					// 用户已经存在，则直接返回
					if (online.contains(msgArr[0])) {
						sc.write(charset.encode(USER_EXIST));
					} else {
						String name = msgArr[0];
						online.add(name);

						int onlineNum = this.onlineTotal();
						String msg = "welcome " + name
								+ " to chat room,current online people num is:"
								+ onlineNum;
						// 通知所有的人
						broadCast(selector, null, msg);
					}
				}
				// 聊天内容
				else if (msgArr != null && msgArr.length > 1) {
					String name = msgArr[0];
					String message = content.substring(name.length()
							+ USER_CONTENT_SPILIT.length());
					message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date()) + name + " say -->" + message;
					if (online.contains(name)) {
						broadCast(selector, sc, message);
					}
				}
			}
		}
	}

	/**
	 * 广播，通知所有人，不管是否被选中，只要是连上的client、但是不播报给自己           instanceof是判断是否为它的实例。
	 */
	private void broadCast(Selector selector, SocketChannel except, String msg) throws IOException {
		for (SelectionKey key : selector.keys()) {
			Channel channel = key.channel();
			if (channel instanceof SocketChannel && channel != except) {
				SocketChannel socketChannel = (SocketChannel) channel;
				socketChannel.write(charset.encode(msg));
			}
		}
	}

	/**
	 * 得到在线总人数
	 */
	private int onlineTotal() {

		int num = 0;
		for (SelectionKey key : this.selector.keys()) {
			Channel targetchannel = key.channel();
			if (targetchannel instanceof SocketChannel) {
				num++;
			}
		}
		return num;
	}

}