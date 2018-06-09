//package com.base.functionClass.socket.NIO.demo2;
//
//public class ServerStartup {
//	private boolean bIsInit = false;
//	
//	/**设置监听IP*/
//	private String mListenIP = "127.0.0.1";
//
//	/**设置监听端口*/
//	private int mListenPort = 0;
//	
//	
//	/**
//	 * 设置服务器的监听端口
//	 * @param vTransThreadNum
//	 */
//	public void setListenPort(String vListenIP,int vListenPort)
//	{
//		this.mListenIP   = vListenIP;
//		this.mListenPort = vListenPort;
//	}
//	
//	
//	public synchronized void init() throws Exception{
//		
//		if ( bIsInit == true || this.mListenPort == 0 )
//			return ;
//		
////		//启动交易处理线程组
////		mDealTransTTM = new TransThreadManager();
////		mDealTransTTM.setName("TTM-Trans-" + this.hashCode());
////		mDealTransTTM.setThreadNum(this.mTransThreadNum);
////		mDealTransTTM.setReqRspQueue(this.mTransReqMsgQueue,this.mTransRspMsgQueue);
////		mDealTransTTM.regRecvRspMsgMethod(this,"doRecvTransRspMsg");
////		mDealTransTTM.start();
////
////		//启动Socket管理线程
////		mClientSMT = new SocketManagerThread();
////		mClientSMT.setName("SMT-Trans-" + this.hashCode());
////		mClientSMT.regReadFullMsgMethod(this,"doReadFullReqMsg");
////		mClientSMT.regSocketClosed(this, "doClientSocketSocket");
////		//mClientSMT.setIsNeedZip(true);//add by csl 2009.5.21
////		mClientSMT.start();
////		sSocketManagerThreadList.add(mClientSMT);
//
//		//启动Socket端口监听服务
//		mListenSST = new SocketServerThread();
//		mListenSST.setName("SST-Trans-" + this.hashCode());
//		mListenSST.setListenAddress(this.mListenIP,this.mListenPort);
//		mListenSST.regAcceptMethod(this,"doAcceptNewSocket");
//		mListenSST.init();
//		mListenSST.start();
//		
//		MonitorThread.addThread("交易线程池管理线程[" + this.mListenPort + "]", mListenSST);
//		MonitorThread.addThread("交易端口监听线程[" + this.mListenPort + "]", mListenSST);
//		
//		//CommUtil.WriteLog(Constant.NOTICE, "交易监听服务[" + this.mListenIP + "," + this.mListenPort + "]启动完成");
//		
//		bIsInit = true;
//	}
//}
