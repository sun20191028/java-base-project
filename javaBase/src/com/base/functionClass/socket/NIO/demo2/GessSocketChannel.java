//package com.base.functionClass.socket.NIO.demo2;
//
//import java.lang.reflect.Method;
//import java.nio.ByteBuffer;
//import java.nio.channels.ClosedChannelException;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.SocketChannel;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//
///**
// * 根据黄金二级系统平台的通信报文规范，将JDK的SocketChannel进行封装，对报文的接收和发送进行异步处理
// */
//public final class GessSocketChannel
//{
//	//---------------------------------------------------------- 变量定义
//	/** 报文头长度 */
//	private int mHeadLen = 0;
//	
//	/** 选择器 */
//	private Selector mSelector = null ;
//	/** 套接字通道 */
//	private SocketChannel mSocketChannel = null ;
//	/** SelectionKey */
//	private SelectionKey mSelectionKey = null;
//	
//	/**待进行写操作的队列*/
//	//private CycleQueue<ByteBuffer> cqWriteMsg = new CycleQueue<ByteBuffer>(10,4);
//	//modify by csl 2011.7.10 队列修改为支持文件做二级缓存的自定义队列
//	private FileCacheQueueByteBuffer cqWriteMsg = new FileCacheQueueByteBuffer();
//	
//	/**读取一条完整报文后的回调方法*/
//	private Method callReadFullMsgMethod = null;
//	/**读取一条完整报文后的回调对象*/
//	private Object callReadFullMsgObj = null;
//	
//	/**Socket关闭事件的回调方法*/
//	private Method callSocketClosedMethod = null;
//	/**Socket关闭事件的回调对象*/
//	private Object callSocketClosedObj = null;
//
//	/**读取的通信报文头6个字节长度*/
//	private ByteBuffer mHeadByteBuffer = null;
//	/**读取的通信报文缓冲*/
//	private ByteBuffer mReadByteBuffer = null;
//	
//	/**读取的总字节数*/
//	private long mReadBytes = 0;
//	/**写入的总字节数*/
//	private long mWriteBytes = 0;
//	/**开始计量的时间*/
//	private long mStartTime = 0;
//	
//	/**是否对Socket的流量进行统计 */
//	private boolean bIsStatisticsDataStream = false;
//	
//	//-------------------add by csl 2008.11.23
//	/**当前正在发送的报文缓冲*/
//	private ByteBuffer mWriteBuffer = null;
//	/** 是否是使用selector 事件进行写操作 */
//	private boolean bIsWriteByEvent = false;
//	
//	/** 待进行事件变更的SocketChannel，需要外部程序传入 add by csl 2008.11.24 第一个元素：SocketChannel 第二个元素：ops*/
//	private ConcurrentLinkedQueue<Object[]> mSckChlEventChangeQueue = null;
//	
//	
//	/**当前的事件类型 */
//	private int iCurrOps = -1;
//	
//	/**是否是短连接，即只接收一个单包的请求，只发送一个单包*/
//	private boolean bIsShortLink = false;
//	
//	/**是否需要压缩/解压缩报文 add by csl 2009.5.21 */
//	private boolean bIsNeedZip = false;
//	
//	/**附加的对象 add by csl 2009.5.28 */
//	private Object mAttachObj = null; 
//	
//	/** 最后发送数据时间 */
//	private long lastSendDataTime = 0 ;
//	/** 最后接收数据时间 */
//	private long lastRecvDataTime = 0 ;
//	
//	/** 报文通讯头的长度是否使用字节表示 add by csl 2010.5.18 */
//	private boolean bIsUseByteHeadLen = false;
//	
//	/** 是否对请求报文自动进行 解压缩 处理  add by csl 2010.8.10 */
//	private boolean bIsAutoUnZipByReqMsg = true;
//	
//	/** 是否对请求报文自动进行 解密 处理  add by csl 2010.8.10 */
//	private boolean bIsAutoDecryptByReqMsg = true;
//	
//	/**
//	 * 设置该SocketChannel的数据写入是否使用事件触发模式
//	 * @param flag  true:使用事件  flase:不使用事件
//	 * @param vSckChlEventChangeQueue  用于接收Socket事件改变的队列
//	 */
//	public void setWriteByEvent(boolean flag,ConcurrentLinkedQueue<Object[]> vSckChlEventChangeQueue)
//	{
//		this.bIsWriteByEvent = flag;
//		this.mSckChlEventChangeQueue = vSckChlEventChangeQueue;
//	}
//	
//	//---------------------------------------------------------- 加密加关 add by csl 2008.11.11 
//	/** 加密模式，默认不加密 */
//	private byte mEncryptMode = SSLConstant.ENCRYPT_MODE_NO ;
//	
//	/** 会话ID */
//	private String mSessionId = "";
//	
//	/**
//	 * 设置加密信息
//	 * @param iEncryptMode  加密模式
//	 * @param sSessionId    会话ID
//	 */
//	public void setEncryptInfo(byte iEncryptMode,String sSessionId)
//	{
//		this.mEncryptMode = iEncryptMode;
//		this.mSessionId   = sSessionId;
//	}
//	
//	
//	//---------------------------------------------------------- 构造函数
//	/**
//	 * 构造函数
//	 * @param vSelector      Selector
//	 * @param vSocketChannel Socket通道
//	 */
//	public GessSocketChannel(Selector vSelector,SocketChannel vSocketChannel,int vHeadLen) 
//	{
//		this.mSelector      = vSelector;
//		this.mSocketChannel = vSocketChannel;
//		this.mHeadLen       = vHeadLen;
//		
//		mHeadByteBuffer = ByteBuffer.allocate(this.mHeadLen);;
//		
//		mStartTime = System.currentTimeMillis();
//	}
//	
//	/**
//	 * 构造函数
//	 * @param vSelector      Selector
//	 * @param vSocketChannel Socket通道
//	 */
//	public GessSocketChannel(Selector vSelector,SocketChannel vSocketChannel ,SelectionKey vSelectionKey,int vHeadLen) 
//	{
//		this.mSelector      = vSelector;
//		this.mSocketChannel = vSocketChannel;
//		this.mSelectionKey  = vSelectionKey;
//		this.mHeadLen       = vHeadLen;
//		
//		mHeadByteBuffer = ByteBuffer.allocate(this.mHeadLen);;
//		
//		mStartTime = System.currentTimeMillis();
//	}
//	
//	/**
//	 * 设置是否是短连接
//	 * @param flag true:短连接 false:长连接
//	 */
//	public void setIsShortLink(boolean flag)
//	{
//		this.bIsShortLink = flag;
//	}
//
//	/**
//	 * 设置是否需要压缩/解压缩报文
//	 * @param flag
//	 */
//	public void setIsNeedZip(boolean flag)
//	{
//		this.bIsNeedZip = flag;
//	}
//	
//	/**
//	 * 设置报文的通讯长度是否使用字节表示
//	 * @param flag
//	 */
//	public void setIsUseByteHeadLen(boolean flag)
//	{
//		this.bIsUseByteHeadLen = flag;
//	}
//	
//	/**
//	 * 是否对请求报文自动进行 解压缩 处理
//	 * @param flag
//	 */
//	public void setIsAutoUnZipByReqMsg(boolean flag)
//	{
//		this.bIsAutoUnZipByReqMsg = flag;
//	}
//	
//	/**
//	 * 是否对请求报文自动进行 解密 处理
//	 * @param flag
//	 */
//	public void setIsAutoDecryptByReqMsg(boolean flag)
//	{
//		this.bIsAutoDecryptByReqMsg = flag;
//	}
//	
//	//---------------------------------------------------------- 注册回调函数
//	/**
//	 * 设置读取一条完整报文后的回调方法。回调方法应尽可能的快速处理 
//	 * 回调方法原形为：method(SocketChannel vSocketChannel, ByteBuffer sFullMsgBuff);
//	 * 特殊注意：报文包括6字节长度的通讯控制报文
//	 * @param objCall     接收一条完整报文的回调方法的实例
//	 * @param sMethodName  接收一条完整报文的回调方法的名称
//	 */
//	public void regReadFullMsgMethod(Object objCall,String sMethodName) 
//		throws NoSuchMethodException,SecurityException
//	{
//		this.callReadFullMsgMethod = objCall.getClass().getMethod(sMethodName,new Class[]{SocketChannel.class,ByteBuffer.class});
//		this.callReadFullMsgObj = objCall;
//	}
//	
//	/**
//	 * 设置Socket关闭后的回调方法
//	 * 回调方法原形为：method(SocketChannel vSocketChannel);
//	 * @param objCall      接收Socket关闭事件的回调方法实例
//	 * @param sMethodName  接收Socket关闭事件的回调方法名称
//	 */
//	public void regSocketClosedMethod(Object objCall,String sMethodName) 
//		throws NoSuchMethodException,SecurityException
//	{
//		this.callSocketClosedMethod = objCall.getClass().getMethod(sMethodName,new Class[]{SocketChannel.class});
//		this.callSocketClosedObj = objCall;
//	}
//	//---------------------------------------------------------- 读报文的相关方法
//	/**
//	 * 读取数据
//	 */
//	public int doRead() throws Exception
//	{
//		int iReadBytes = 0;
//
//		//判断是否应该读取通信报文头
//		if ( this.mHeadByteBuffer.position() < this.mHeadByteBuffer.limit() )
//		{
//			//读取内容
//			try{
//				int ret = this.mSocketChannel.read(this.mHeadByteBuffer);
//				if ( ret == -1 )
//					sendSocketClosedEvent();
//				else
//					iReadBytes += ret;
//			}catch(Exception e)
//			{
//				//CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
//				sendSocketClosedEvent();
//			}
//			
//		}
//		
//		//读满了报文头
//		if ( this.mReadByteBuffer == null && this.mHeadByteBuffer.position() == this.mHeadByteBuffer.limit() )
//		{
//			try
//			{
//				//获得报文长度
//				int iMsgLen = 0;
//				
//				//modify by csl 2010.9.16 增加对没有通讯头报文的读取支持（默认每次读取1024)
//				if ( this.mHeadLen > 0 )
//				{
//					//modify by csl 2010.5.18 增加对通讯长度为字节数组的支持
//					if ( this.bIsUseByteHeadLen == false )
//						iMsgLen = Integer.parseInt(new String(this.mHeadByteBuffer.array()));
//					else
//						iMsgLen = this.mHeadByteBuffer.getInt(0);
//					//end modify
//				}else
//				{
//					iMsgLen = 1024 ;
//				}
//				
//				if ( iMsgLen < 0 || iMsgLen > 99999999 )
//					throw new NumberFormatException("长度非法！");
//				
//				//生成报文缓冲区
//				this.mReadByteBuffer = ByteBuffer.allocate(this.mHeadLen + iMsgLen);
//				this.mReadByteBuffer.clear();
//				
//				//将通信报文头加入到报文缓冲区内（保证返回的是完整报文）
//				this.mReadByteBuffer.put(this.mHeadByteBuffer.array());
//				
//			}catch(NumberFormatException nfe)
//			{
//				CommUtil.WriteLog(Constant.INFO, "解析通讯头长度[" + new String(this.mHeadByteBuffer.array()) + "]错误");
//				this.sendSocketClosedEvent();
//			}
//		}
//		
//		//读取报文
//		if ( this.mReadByteBuffer != null && 
//				this.mHeadByteBuffer.position() == this.mHeadByteBuffer.limit() )
//		{
//			//读取内容
//			int ret = -1 ;
//			try{
//				ret = this.mSocketChannel.read(this.mReadByteBuffer);
//				if ( ret <= -1 )
//					sendSocketClosedEvent();
//				else
//					iReadBytes += ret;
//			}catch(Exception e)
//			{
//				//CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
//				sendSocketClosedEvent();
//			}
//			
//			//add by csl 2010.9.16 如果通讯头的长度为0，则每次读取都为一个完整报文
//			if (  this.mHeadLen <= 0 && ret > 0)
//			{
//				byte[] bak = this.mReadByteBuffer.array() ;
//				this.mReadByteBuffer = ByteBuffer.allocate(ret);
//				this.mReadByteBuffer.put(bak,0,ret);
//				this.mReadByteBuffer.position(ret);
//				this.mReadByteBuffer.limit(ret);
//			}
//			//end add 
//			
//			//判断是否读取完毕
//			if ( this.mReadByteBuffer.position() == this.mReadByteBuffer.limit() ) 
//			{
//				//如果是短连接，则不再继续接收 add by csl 2009.4.28
//				if ( this.bIsShortLink == true && this.mHeadLen > 0 ) //modify by csl 2010.9.16 有通讯头的报文才不继续接收
//				{
//					this.registerEvent(0);
//				}
//				
//				//回调处理
//				if ( this.callReadFullMsgMethod != null &&
//						this.callReadFullMsgObj != null )
//				{
//					try
//					{
//						//add by csl 2009.5.21 先解压后解密						
//						ByteBuffer unzipBuff = null;
//						if ( this.bIsAutoUnZipByReqMsg == false )
//						{
//							unzipBuff = this.mReadByteBuffer;
//						}else{
//							byte[] unzipBytes = this.unzipReadBytes(this.mReadByteBuffer.array());
//							unzipBuff = ByteBuffer.wrap(unzipBytes);
//						}
//												
//						//modify by csl 2008.11.11  解密
//						ByteBuffer descryptBuff = null;
//						byte stmpEncryptMode = unzipBuff.get(this.mHeadLen);
//						if ( stmpEncryptMode == SSLConstant.ENCRYPT_MODE_NO 
//								|| this.bIsAutoDecryptByReqMsg == false  )
//						{
//							descryptBuff = unzipBuff;
//						}else{
//							byte[] src = DESede.decryptSrcMsg(this.mHeadLen,unzipBuff.array());
//							descryptBuff = ByteBuffer.wrap(src);
//						}
//						
//						this.callReadFullMsgMethod.invoke(this.callReadFullMsgObj,new Object[]{this.mSocketChannel,descryptBuff});
//						
//					}catch(Exception e){
//						CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
//						this.sendSocketClosedEvent();
//					}
//				}
//				
//				//清除
//				this.mHeadByteBuffer.clear();
//				this.mReadByteBuffer = null;
//				
//				this.lastRecvDataTime = System.currentTimeMillis();
//			}
//		}
//		
//		//统计
//		if ( this.bIsStatisticsDataStream )
//		{
//			//本应该对本段代码进行同步，但考虑到同步对性能影响比较大，同时对统计的值也不是要求非常精确，因此可以不同步
//			long currTime = System.currentTimeMillis();
//			long invTime = currTime - this.mStartTime;
//			if ( invTime > Constant.CFG_COLLECT_DATA_STREAM_SIZE_TIME )
//			{
//				this.mStartTime = currTime;
//				this.mWriteBytes = 0;
//				this.mReadBytes  = 0;
//			}
//			
//			this.mReadBytes+= iReadBytes;	
//		}
//
//		return iReadBytes;
//	}
//
//	//---------------------------------------------------------- 写报文的相关方法
//	/**
//	 * 添加待发送的报文，包括通信部分的6位长度
//	 */
//	public void addWriteMsg(StringBuffer sbMsg) 
//	{
//		try{
//			ByteBuffer msgBuff = ByteBuffer.wrap(MsgUtil.StringToBytes(sbMsg.toString()));
//			addWriteMsg(msgBuff);
//		}catch(Exception cce)
//		{
//			CommUtil.WriteLog(Constant.NORMAL_ERROR,cce);
//			sendSocketClosedEvent();
//		}
//	}
//	
//	/**
//	 * 添加待发送的报文，包括通信部分的6位长度
//	 */	
//	public synchronized void addWriteMsg(ByteBuffer msgBuff)
//	{
//		try
//		{
//			ByteBuffer writeBuff = null;
//			
//			//modify by csl 2009.7.5 特殊处理，由于加密后压缩率非常低，因此当后续还需要压缩时，只有未达到压缩的范围时，才进行加密
//			if ( ( this.mEncryptMode == SSLConstant.ENCRYPT_MODE_DEFAULT ||
//					 this.mEncryptMode == SSLConstant.ENCRYPT_MODE_SESSION )
//					   && msgBuff.limit() < Constant.CFG_NOT_ZIP_MAX_SIZE 
//					   && this.bIsNeedZip == true )
//			{
//				byte[] buff = DESede.encryptSrcMsg(this.mHeadLen,this.mEncryptMode, this.mSessionId, msgBuff.array());
//				writeBuff = ByteBuffer.wrap(buff);
//			}else{
//				writeBuff = msgBuff;
//			}
//			
//			//add by csl 2009.5.21 先加密后压缩
//			ByteBuffer zipBuff = null;
//			if ( this.bIsNeedZip )
//			{
//				byte[] zipBytes = this.zipWriteBytes(writeBuff.array());
//				zipBuff = ByteBuffer.wrap(zipBytes);
//			}else{
//				zipBuff = writeBuff;
//			}
//			
//			zipBuff.position(0);
//			if ( this.bIsWriteByEvent == true && this.bIsShortLink == false ){ //事件模式，而且长连接
//				this.cqWriteMsg.add(zipBuff);
//				this.registerEvent(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
//			}
//			else{ //直接写入模式				
//				this.directWrite(zipBuff);
//			}
//			
//			this.lastSendDataTime = System.currentTimeMillis();
//			
//		}catch(Exception cce)
//		{
//			CommUtil.WriteLog(Constant.NORMAL_ERROR,cce);
//			sendSocketClosedEvent();
//		}
//	}
//	
//	/**
//	 * 直接输出，不使用事件
//	 */
//	private  int directWrite(ByteBuffer vMsgBuff)  throws Exception
//	{		
//	
//		if ( this.bIsWriteByEvent == true && this.bIsShortLink == false )
//			throw new GessException(GessErrCode.HJ7001, "当前SocketChannel设置的写入模式设置为使用事件，但是调用了directWrite方法，此操作被禁止!");
//
//		synchronized(this)
//		{
//			//输出
//			while ( vMsgBuff.position() < vMsgBuff.limit())
//			{
//				this.mSocketChannel.write(vMsgBuff);
//			}
//			
//			//统计
//			if ( this.bIsStatisticsDataStream )
//			{
//				long currTime = System.currentTimeMillis();
//				long invTime = currTime - this.mStartTime;
//				if ( invTime > Constant.CFG_COLLECT_DATA_STREAM_SIZE_TIME )
//				{
//					this.mStartTime = currTime;
//					this.mWriteBytes = 0;
//					this.mReadBytes  = 0;
//				}
//				
//				this.mWriteBytes  += vMsgBuff.limit();
//			}
//		}
//		return vMsgBuff.limit();
//	}
//	
//
//	/**
//	 * 将待发送的报文进行单包写操作,
//	 * 一个大报文可能会分很多次写操作完成。
//	 */
//	public int doWrite() throws ClosedChannelException,Exception
//	{	
//		int iWriteBytes = 0;
//
//		if ( this.bIsWriteByEvent == false )
//			throw new GessException(GessErrCode.HJ7001,"当前SocketChannel设置的写入模式设置为不使用事件方式，但是调用了doWrite方法，此操作被禁止!");
//
//		//modify by csl 2009.5.5，事件模式去掉锁机制，提高性能,因为永远只会有一个管理线程调用
//		//synchronized(this) 
//		{
//			//如果当前包已写完，则从队列中取出待写入的数据
//			if ( this.mWriteBuffer == null ||
//					( this.mWriteBuffer != null &&
//					   this.mWriteBuffer.position() >= this.mWriteBuffer.limit() ) )
//			{
//				//清除当前发送缓冲区
//				this.mWriteBuffer = null;
//				
//				//从队列中获取待发送的数据
//				this.mWriteBuffer = this.cqWriteMsg.pool();
//				
//			} 
//			
//			//还有待发送的数据，继续发送
//			if ( this.mWriteBuffer != null  )
//			{
//				try{
//					int ret = this.mSocketChannel.write(this.mWriteBuffer);
//					if ( ret == -1 )
//						sendSocketClosedEvent();
//					else if ( ret == 0 )
//						CommUtil.WriteLog(Constant.NORMAL_ERROR, "写入队列缓冲区满:" + this.mSocketChannel.socket().toString());
//					else
//						iWriteBytes += ret;
//				}catch(Exception e)
//				{
//					CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
//					sendSocketClosedEvent();
//				}
//			}else{
//				//没有可发送的内容，则取消写事件
//				this.registerEvent(SelectionKey.OP_READ);
//			}
//		}
//		
//		if ( this.bIsStatisticsDataStream )
//			mWriteBytes += iWriteBytes;
//		
//		return iWriteBytes;
//	}
//	
//	//---------------------------------------------------------- 其它
//	/**
//	 * 发送Socket关闭事件
//	 */
//	public void sendSocketClosedEvent() 
//	{
//		try{
//			this.registerEvent(0);
//			this.mSocketChannel.close();
//			if ( this.callSocketClosedObj != null && this.callSocketClosedMethod != null )
//			{	
//				this.callSocketClosedMethod.invoke(this.callSocketClosedObj,new Object[]{this.mSocketChannel});
//			}
//		}catch(Exception e){
//			CommUtil.WriteLog(Constant.NORMAL_ERROR,e);
//		}
//	}
//		
//	/**
//	 * 获得选择器
//	 * @return
//	 */
//	public Selector getSelector()
//	{
//		return this.mSelector;
//	}
//	
//	/**
//	 * 获得SocketChannel
//	 * @return
//	 */
//	public SocketChannel getSocketChannel()
//	{
//		return this.mSocketChannel;
//	}
//	
//	/** 设置是否对Socket的通讯流量进行统计 */
//	public void setStatisticsDataStream(boolean b)
//	{
//		this.bIsStatisticsDataStream = b;
//	}
//	
//	/**
//	 * 获得当前SocketChannel读取的总字节数
//	 * @return
//	 */
//	public long getSumReadBytes()
//	{
//		return this.mReadBytes;
//	}
//
//	/**
//	 * 获得当前SocketChannel写入的总字节数
//	 * @return
//	 */
//	public long getSumWriteBytes()
//	{
//		return this.mWriteBytes;
//	}
//	
//	/**
//	 * 获得当前Socket的最近X分钟平均每秒读取的字节数（默认5分钟）
//	 * @return
//	 */
//	public double getReadUseRate()
//	{
//		long currTime = System.currentTimeMillis();
//		long invTime = currTime - this.mStartTime;
//		if ( invTime > Constant.CFG_COLLECT_DATA_STREAM_SIZE_TIME )
//		{
//			this.mStartTime = currTime;
//			this.mWriteBytes = 0;
//			this.mReadBytes  = 0;
//		}
//		
//		invTime = invTime/1000;
//		
//		if ( invTime <= 0 )
//			return this.mReadBytes;
//		else
//			return this.mReadBytes*1.0/(invTime*1.0);
//		
//	}
//	
//	/**
//	 * 获得当前Socket的最近X分钟平均每秒输出的字节数（默认5分钟）
//	 * @return
//	 */
//	public double getWriteUseRate()
//	{
//		long currTime = System.currentTimeMillis();
//		long invTime = currTime - this.mStartTime;
//		if ( invTime > Constant.CFG_COLLECT_DATA_STREAM_SIZE_TIME )
//		{
//			this.mStartTime = currTime;
//			this.mWriteBytes = 0;
//			this.mReadBytes  = 0;
//		}
//		
//		invTime = invTime/1000;
//		
//		if ( invTime <= 0 )
//			return this.mWriteBytes;
//		else
//			return this.mWriteBytes*1.0/(invTime*1.0);
//		
//	}
//	
//	/**
//	 * 获得当前Socket的利用率 （读入总字节＋写入总字节）/ 计量时间（秒）
//	 */
//	public double getUseRate() 
//	{
//		long currTime = System.currentTimeMillis();
//		long invTime = currTime - this.mStartTime;
//		if ( invTime > Constant.CFG_COLLECT_DATA_STREAM_SIZE_TIME )
//		{
//			this.mStartTime = currTime;
//			this.mWriteBytes = 0;
//			this.mReadBytes  = 0;
//		}
//		
//		invTime = invTime/1000;
//		
//		long bytes = this.mReadBytes + this.mWriteBytes;
//		if ( invTime <= 0 )
//			return bytes;
//		else
//			return bytes*1.0/(invTime*1.0);
//	}
//	
//	//add by csl 2008.11.24 写入数据使用事件触发完成
//	public synchronized void registerEvent(int ops) throws GessException,Exception
//	{
//		//CommUtil.writeLog("Socket", Constant.INFO, this.mSocketChannel.socket().toString() + ":准备注册事件[" + ops + "]");
//		
//		if ( iCurrOps == ops )
//			return ;
//		
//		iCurrOps = ops;
//		
//		if ( bIsWriteByEvent == true )
//		{
//			if ( mSckChlEventChangeQueue == null )
//				throw new GessException(GessErrCode.HJ7001,"对SocketChannel注册事件时，mSckChlEventChangeQueue队列不能为空.");
//			if ( this.mSocketChannel.isOpen() == false && ops != 0 )
//			{
//				this.sendSocketClosedEvent();
//			}else
//			{
//				this.mSckChlEventChangeQueue.add(new Object[]{this.mSocketChannel,Integer.parseInt(""+ops)});
//				if ( this.mSckChlEventChangeQueue.peek() != null )
//					this.mSelector.wakeup();
//			}
//		}else{
//			this.mSocketChannel.register(this.mSelector, ops);
//		}
//	}
//	
//	/**
//	 * 解压缩读取报文
//	 * @param vReadBytes  接收到的完整报文字节数组
//	 * @return
//	 */
//	private byte[] unzipReadBytes(byte[] vReadBytes)
//	{
//		try
//		{
//			if (  vReadBytes.length > this.mHeadLen 
//					&& vReadBytes[this.mHeadLen] == 0x1)
//			{
//				byte[] unzipBuff = new byte[vReadBytes.length - this.mHeadLen - 1 ];
//				System.arraycopy(vReadBytes, this.mHeadLen + 1, unzipBuff, 0, unzipBuff.length);
//				return CommUtil.unzip(unzipBuff);
//			}
//		}catch(Exception e)
//		{
//			CommUtil.WriteLog(Constant.INFO, e);
//		}
//		return vReadBytes;
//	}
//	
//	/**
//	 * 压缩输出报文
//	 * @param vWriteBytes  原始完整的输出报文
//	 * @return
//	 */
//	private byte[] zipWriteBytes(byte[] vWriteBytes)
//	{
//		try
//		{
//			if ( this.bIsNeedZip == true 
//					&& vWriteBytes.length > Constant.CFG_NOT_ZIP_MAX_SIZE )
//			{
//				//add by csl 2009.7.5 加密内容不压缩
//				if ( vWriteBytes[this.mHeadLen] == SSLConstant.ENCRYPT_MODE_DEFAULT 
//						|| vWriteBytes[this.mHeadLen] == SSLConstant.ENCRYPT_MODE_SESSION )
//					return vWriteBytes;
//
//				byte[] zipBuff = CommUtil.zip(vWriteBytes);
//				byte[] outBuff = new byte[zipBuff.length + this.mHeadLen + 1];
//				byte[] lenBuff = CommUtil.FILL("" + ( zipBuff.length + 1 ), '0', this.mHeadLen, 'L').getBytes();
//				
//				outBuff[this.mHeadLen] = 0x01;
//				System.arraycopy(lenBuff, 0,outBuff, 0, this.mHeadLen);
//				System.arraycopy(zipBuff, 0, outBuff, this.mHeadLen + 1, zipBuff.length );
//				
//				CommUtil.writeSystemLog(Constant.DEBUG, "对输出报文已压缩[原长度=" + vWriteBytes.length + ",压缩后长度=" + outBuff.length + "]");
//				
//				return outBuff;
//			}
//		}catch(Exception e)
//		{
//			CommUtil.WriteLog(Constant.INFO, e);
//		}
//		return vWriteBytes;
//	}
//
//	//add by csl 2009.5.28
//	/**
//	 * 添加附加到当前Socket上的对象，以便区分不同类型的Socket
//	 * @param obj
//	 */
//	public void setAttachObject(Object obj)
//	{
//		this.mAttachObj = obj;
//	}
//	
//	/**
//	 * 获得附加到当前Socket上的对象
//	 * @return 如果返回null，代表没有
//	 */
//	public Object getAttachObject()
//	{
//		return this.mAttachObj;
//	}
//	//end add
//	
//	public SelectionKey getSelectionKey()
//	{
//		return this.mSelectionKey;
//	}
//	
//	/** 
//	 * 最后发送数据时间
//	 * @return
//	 */
//	public long getLastSendDataTime()
//	{
//		return this.lastSendDataTime;
//	}
//	
//	/**
//	 * 最后接收数据时间
//	 * @return
//	 */
//	public long getLastRecvDataTime()
//	{
//		return this.lastRecvDataTime;
//	}
//	
//	/**
//	 * 检查是否可发送心跳包报文
//	 * @multiple 默认心跳包超时时间的倍数，如：默认心跳包时间为5秒，若输入参数为2，则实际判断是否可发送心跳包的时间为10秒。
//	 * @return
//	 */
//	public boolean checkCanSendTestMsg(int multiple)
//	{
//		long currTime = System.currentTimeMillis();
//		
//		if ( this.lastRecvDataTime <= 0 )
//			this.lastRecvDataTime = currTime;
//		
//		if ( this.lastSendDataTime <= 0 )
//			this.lastSendDataTime = currTime;
//		
//		if ( ( currTime - this.lastRecvDataTime ) >= ( Constant.CFG_SOCKET_TEST_INV_TIME * multiple * 1000 - 100 ) 
//				&& ( currTime - this.lastSendDataTime ) >= ( Constant.CFG_SOCKET_TEST_INV_TIME * multiple * 1000 - 100 ) )
//			return true;
//		else
//			return false;
//	}
//	
//	/**
//	 * 根据最后接收数据的时间，检查Socket是否已超时
//	 * @multiple 默认心跳包超时时间的倍数，如：默认心跳包时间为5秒，若输入参数为30，则如果超过150秒没有接收到数据，则判为超时
//	 */
//	public boolean checkIsRecvDataTimeOut(int multiple)
//	{
//		long currTime = System.currentTimeMillis();
//		
//		if ( this.lastRecvDataTime <= 0 )
//			this.lastRecvDataTime = currTime;
//		
//		if ( this.lastSendDataTime <= 0 )
//			this.lastSendDataTime = currTime;
//		
//		if ( ( currTime - this.lastRecvDataTime ) >= ( Constant.CFG_SOCKET_TEST_INV_TIME * multiple * 1000 - 100 ) ) 
//			return true;
//		else
//			return false;
//	}
//	
//	public String toString()
//	{
//		return this.mSocketChannel.socket().toString() + ",平均流量 = " + this.getUseRate() + " 字节/秒,最后接收数据时间=" + this.lastRecvDataTime + ",最后发送数据时间=" + this.lastSendDataTime ;
//	}
//	
//	/**
//	 * 是否正在发送*/	
//	public boolean isSending()
//	{
//		return mWriteBuffer!=null;
//	}
//	
//	/**
//	 * 获得发送队列的大小
//	 * @return
//	 */
//	public int getSendQueueSize()
//	{
//		return this.cqWriteMsg.getSize();
//	}
//	
//}
//
