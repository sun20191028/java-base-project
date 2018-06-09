//package com.base.functionClass.socket.NIO.demo2;
//
//import java.math.BigDecimal;
//import java.net.Socket;
//import java.util.Collection;
//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.base.util.properties.PropUtil;
//
//
///**
// * 后台服务监控线程
// * 监控内容包括：
// *  1. 广播服务的在线客户数
// *  2. 线程情况
// *  3. 网络流量
// *  4. 交易业务的执行情况
// *  5. 检查死锁
// *  6. 检查内存缓存数据
// * @author aps-csl 2008.11.28
// */
//public class MonitorThread extends Thread
//{
//	/** 监控信息输出级别：简单 */
//	public static final int INFO_LEVEL_SIMPLE = 0;
//	/** 监控信息输出级别: 详细 */
//	public static final int INFO_LEVEL_DETAIL = 1;
//	
//	/** 监控信息输出频率（单位：秒 ) */
//	private static int iPrintFrequencyTime = PropUtil.getConfigByInt("MONITOR_PRINT_FREQUENCY_TIME",30*60);
//	/** 监控信息级别 */
//	private static int iMonitorInfoLevel   = PropUtil.getConfigByInt("MONITOR_INFO_LEVEL",INFO_LEVEL_SIMPLE);
//	/** 是否检查循环调用情况 */
//	private static boolean bIsRunCycleMonitor = PropUtil.getConfigByBoolean("MONITOR_RUN_CYCLE",false);
//	/** 内存使用情况打印的间隔时间（秒）*/
//	private static int iPrintMemoryTime = PropUtil.getConfigByInt("MONITOR_PRINT_MEMORY_TIME", 10*60);
//
//	/** 垃圾内存回收间隔时间(分钟) */
//	private static int iRunSystemGcInvTime = PropUtil.getConfigByInt("RUN_SYSTEM_GC_INV_TIME", 120);
//	
//	
//	/** 被监控的的Map集合 */
//	private final static ConcurrentHashMap<String,Map> sMaps = new ConcurrentHashMap<String,Map>();
//	/** 被监控的Collection 集合 */
//	private final static ConcurrentHashMap<String,Collection> sCollections = new ConcurrentHashMap<String,Collection>();
//	/** 被监控的循环 */
//	private final static ConcurrentHashMap<String,Long> sCycles = new ConcurrentHashMap<String,Long>();
//	/** 被监控的线程 */
//	private final static ConcurrentHashMap<String,Thread> sThreads = new ConcurrentHashMap<String,Thread>();
//	/** 被监控的Socket */
//	private final static ConcurrentHashMap<String,GessSocketChannel> sSockets = new ConcurrentHashMap<String,GessSocketChannel>();
//	/** 被监控的普通Socket */
//	private final static ConcurrentHashMap<String,Socket> sNormalSockets = new ConcurrentHashMap<String,Socket>();
//	/** 被监控的文件队列 */
//	private final static ConcurrentHashMap<String,AFileCacheQueue> sFileCacheQueues = new ConcurrentHashMap<String,AFileCacheQueue>();
//	
//	/** 是否动行 */
//	private boolean bIsRun = true;
//	
//	/** 上一次运行系统内存回收的时间 */
//	private static long lastGcTime = System.currentTimeMillis();
//	
//	/** 上一次监控时间 */
//	private static long lastTime = System.currentTimeMillis();
//
//	/** 上次读取配置时间 */
//	private static long lastReadTime = System.currentTimeMillis();
//	
//	/** 上次打印内存情况的时间 */
//	private static long lastPrintMemory = System.currentTimeMillis();
//	
//	/** 当前监控时间 */
//	private static long currTime = 0;
//	
//
//	
//	public MonitorThread()
//	{
//		
//	}
//	
//
//	/**
//	 * 线程方法
//	 */
//	public void run()
//	{
//		String sName = "MonitorThread";
//		StringBuilder sbOut = new StringBuilder();
//		
//		while ( bIsRun )
//		{
//			try
//			{
//				MonitorThread.addCycleInvoke(sName);
//				Thread.sleep(500);
//				currTime = System.currentTimeMillis();
//				
//				//每10秒，重新读取配置信息
//				if ( ( currTime - lastReadTime ) > 10*1000 )
//				{
//					//重新读取配置信息
//					iPrintFrequencyTime = CommUtil.getConfigByInt("MONITOR_PRINT_FREQUENCY_TIME",30*60);
//					iMonitorInfoLevel = CommUtil.getConfigByInt("MONITOR_INFO_LEVEL",INFO_LEVEL_SIMPLE);
//					bIsRunCycleMonitor = CommUtil.getConfigByBoolean("MONITOR_RUN_CYCLE",false);
//					iRunSystemGcInvTime = CommUtil.getConfigByInt("RUN_SYSTEM_GC_INV_TIME", 120);
//					iPrintMemoryTime = CommUtil.getConfigByInt("MONITOR_PRINT_MEMORY_TIME", 10*60);
//				}
//				
//				//每60秒，打印一次内存使用情况
//				if ( ( currTime - lastPrintMemory) > iPrintMemoryTime * 1000 )
//				{
//					Runtime runtime = Runtime.getRuntime();
//					BigDecimal bd_mem1 = new BigDecimal(String.valueOf((runtime.totalMemory()-runtime.freeMemory())/1024.0/1024.0)).setScale(2,BigDecimal.ROUND_HALF_UP);
//					CommUtil.writeSystemLog(Constant.NOTICE, "内存使用情况监控：内存占用[" + bd_mem1.toString() + "]M");
//					lastPrintMemory = currTime ;
//				}
//				
//				//每半个小时运行一次系统内存回收
//				if ( currTime - lastGcTime > iRunSystemGcInvTime * 60 * 1000 )
//				{					
//					Runtime runtime = Runtime.getRuntime();
//					BigDecimal bd_mem1 = new BigDecimal(String.valueOf((runtime.totalMemory()-runtime.freeMemory())/1024.0/1024.0)).setScale(2,BigDecimal.ROUND_HALF_UP);
//					CommUtil.writeSystemLog(Constant.NOTICE, "系统开始运行垃圾内存回收,内存占用[" + bd_mem1.toString() + "]M");
//					lastGcTime = currTime;
//					System.gc();
//					Thread.sleep(3000);
//					runtime = Runtime.getRuntime();
//					BigDecimal bd_mem2 = new BigDecimal(String.valueOf((runtime.totalMemory()-runtime.freeMemory())/1024.0/1024.0)).setScale(2,BigDecimal.ROUND_HALF_UP);
//					CommUtil.writeSystemLog(Constant.NOTICE, "垃圾内存回收完毕,内存占用[" + bd_mem2.toString() + "]M");
//				}
//				
//				//进行系统检查
//				if ( currTime - lastTime > iPrintFrequencyTime * 1000  )
//				{ 					
//					sbOut.append("\n//-------------------------- 监控程序 开始进行检测  ---\n" );
//					//输出线程状态
//					sbOut.append("//------- 开始检测线程情况... \n");
//					checkThreads(iMonitorInfoLevel,sbOut);
//					
//					sbOut.append("//------- 开始检测Map使用情况... \n");
//					checkMap(iMonitorInfoLevel,sbOut);
//					
//					sbOut.append("//------- 开始检测Collection使用情况... \n");
//					checkCollection(iMonitorInfoLevel,sbOut);
//
//					sbOut.append("//------- 开始检测AFileCacheQueue 使用情况... \n");
//					checkFileCacheQueue(iMonitorInfoLevel,sbOut);
//
//					sbOut.append("//------- 开始检测循环调用情况... \n");
//					checkCycle(iMonitorInfoLevel,sbOut);
//					
//					sbOut.append("//------- 开始检测网络情况... \n");
//					checkNetwork(iMonitorInfoLevel,sbOut);
//					
//					sbOut.append("//-------------------------- 监控程序 结束进行检测  ---\n" );
//					CommUtil.writeSystemLog(Constant.INFO, sbOut.toString());
//					
//					//清除相应信息
//					if ( sbOut.length() > 0 )
//						sbOut.delete(0, sbOut.length()-1);
//					
//					sCycles.clear();
//					lastTime = currTime;
//
//				}
//			}catch(Exception e)
//			{
//				CommUtil.WriteLog(Constant.BADLY_ERROR, e);
//			}
//		}
//	}
//	
//	//-----------------------------------------  添加相关的监控对象
//	/**
//	 * 添加待监控的Map对象
//	 * @param sName  名称，须唯一
//	 * @param map    被监控的Map对象
//	 */
//	public static void addMap(String sName,Map map)
//	{
//		sMaps.put(sName, map);
//	}
//	
//	/**
//	 * 移除被监控的Map对象
//	 * @param sName 添加监控时使用的名称
//	 */
//	public static void removeMap(String sName)
//	{
//		sMaps.remove(sName);
//	}
//	
//	/**
//	 * 添加待监控的队列
//	 * @param sName 对列名称，必须唯一
//	 * @param Collection 被监控的对列
//	 */
//	public static void addCollection(String sName,Collection queue)
//	{
//		sCollections.put(sName, queue);
//	}
//	
//	/**
//	 * 移除被监控的队列
//	 * @param sName 添加监控时使用的名称
//	 */
//	public static void removeCollection(String sName)
//	{
//		sCollections.remove(sName);
//	}
//	
//	/**
//	 * 添加待监控的文件队列
//	 * @param sName 对列名称，必须唯一
//	 * @param Collection 被监控的对列
//	 */
//	public static void addFileCacheQueue (String sName,AFileCacheQueue  queue )
//	{
//		sFileCacheQueues.put(sName, queue);
//	}
//	
//	/**
//	 * 移除被监控的文件队列
//	 * @param sName 添加监控时使用的名称
//	 */
//	public static void removeFileCacheQueue (String sName)
//	{
//		sFileCacheQueues.remove(sName);
//	}
//	
//	/**
//	 * 添加待监控的线程
//	 * @param sName 线程名称，必须唯一
//	 * @param Thread 被监控的线程
//	 */
//	public static void addThread(String sName,Thread t)
//	{
//		sThreads.put(sName, t);
//	}
//	
//	/**
//	 * 移除被监控的队列
//	 * @param sName 线程名称，必须唯一
//	 */
//	public static void removeThread(String sName)
//	{
//		sThreads.remove(sName);
//	}
//	
//	/**
//	 * 添加待监控的Socket
//	 * @param sName 线程名称，必须唯一
//	 * @param gsc   被监控的Socket
//	 */
//	public static void addSocket(String sName,GessSocketChannel gsc)
//	{
//		gsc.setStatisticsDataStream(true);
//		sSockets.put(sName, gsc);
//	}
//	
//	/**
//	 * 移除被监控的Socket
//	 * @param sName 线程名称，必须唯一
//	 */
//	public static void removeSocket(String sName)
//	{
//		sSockets.remove(sName);
//	}
//	
//	/**
//	 * 添加待监控的普通Socket
//	 * @param sName 线程名称，必须唯一
//	 * @param socket   被监控的Socket
//	 */
//	public static void addNormalSocket(String sName,Socket socket)
//	{
//		sNormalSockets.put(sName, socket);
//	}
//	
//	/**
//	 * 移除被监控的普通Socket
//	 * @param sName 线程名称，必须唯一
//	 */
//	public static void removeNormalSocket(String sName)
//	{
//		sNormalSockets.remove(sName);
//	}
//	
//	/**
//	 * 添加循环调用
//	 * @param sName 循环名称
//	 */
//	public static void addCycleInvoke(String sName)
//	{
//		if ( bIsRunCycleMonitor )
//		{
//			Long value = sCycles.get(sName);
//			if ( value == null )
//			{
//				value = 0L;
//				sCycles.put(sName, value);
//			}
//			if ( value.longValue() < Long.MAX_VALUE - 2 )
//			{
//				value++;
//				sCycles.put(sName, value);
//			}
//		}
//	}
//	
//	/**
//	 * 移除循环调用的监控
//	 * @param sName 被移除的循环名称
//	 */
//	public static void removeCycleInvoke(String sName)
//	{
//		sCycles.remove(sName);
//	}
//	
//	//----------------------------------------- 对被监控的对象进行检查
//	
//	/** 检查线程信息 */
//	public void checkThreads(int level,StringBuilder sbOutResult)
//	{
//		
//		//获得所有子线程
//		Thread[] threads = getAllThreads();
//		
//		//输出情况
//		for ( int i = 0 ; i < threads.length ; i++ )
//		{
//			Thread t = threads[i];
//			if ( t != null )
//			{
//				sbOutResult.append("第" + i + "个线程：[" + t.getName() + "," + t.getPriority());
//				if ( t.isAlive() )
//					sbOutResult.append(",线程正常]\n");
//				else
//					sbOutResult.append(",线程终止]\n");
//			}
//		}
//
//	}
//	
//	/** 获得活动经程 */
//	public static Thread[] getAllThreads()
//	{
//		ThreadGroup tgRoot = null;
//		ThreadGroup tgCurr = Thread.currentThread().getThreadGroup();
//		
//		//获得根线程组
//		while ( tgCurr != null )
//		{
//			tgRoot = tgCurr;
//			tgCurr = tgRoot.getParent();
//		}
//		
//		//获得所有子线程
//		Thread[] threads = new Thread[tgRoot.activeCount() * 2 ];
//		tgRoot.enumerate(threads);
//		
//		return threads;
//	}
//	
//	/** 检查Map 信息 */
//	public void checkMap(int level,StringBuilder sbOutResult)
//	{
//		int index = 0;
//		Enumeration keys = sMaps.keys();
//		while ( keys.hasMoreElements() )
//		{
//			index++;
//			Object objKey = keys.nextElement();
//			Map tmpMap = sMaps.get(objKey);
//			if ( tmpMap != null )
//			{
//				sbOutResult.append("第" + index + "个Map:[" + objKey + "," + tmpMap.size() + "]\n");
//				if ( level == INFO_LEVEL_DETAIL && tmpMap.size() > 0)
//				{
//					sbOutResult.append("-- 明细信息... \n");
//					int subIndex = 0;
//					Iterator<?> it = tmpMap.keySet().iterator();
//					while ( it.hasNext() )
//					{
//						subIndex++;
//						Object subObjKey = it.next();
//						Object subObjValue = tmpMap.get(subObjKey);
//						sbOutResult.append("-- " + subIndex + ":[" + subObjKey + "][" + subObjValue + "]\n");
//					}
//				}
//			}
//		}
//	}
//		
//	/** 检查Collection 信息 */
//	public void checkCollection(int level,StringBuilder sbOutResult)
//	{
//		int index = 0;
//		Enumeration keys = sCollections.keys();
//		while ( keys.hasMoreElements() )
//		{
//			index++;
//			Object objKey = keys.nextElement();
//			Collection tmpCollection = sCollections.get(objKey);
//			if ( tmpCollection != null )
//			{
//				sbOutResult.append("第" + index + "个Collection:[" + objKey + "," + tmpCollection.size() + "]\n");
//				if ( level == INFO_LEVEL_DETAIL && sbOutResult.length() > 0 )
//				{
//					sbOutResult.append("-- 明细信息... \n");
//					Object[] objs = tmpCollection.toArray();
//					for ( int i = 0 ; i < objs.length ; i++ )
//					{
//						sbOutResult.append("-- " + i + ":[" + objs[i] + "]\n");
//					}				
//				}
//			}
//		}
//	}
//	
//	/** 检查FileCacheQueue  信息 */
//	public void checkFileCacheQueue (int level,StringBuilder sbOutResult)
//	{
//		int index = 0;
//		Enumeration keys = sFileCacheQueues.keys();
//		while ( keys.hasMoreElements() )
//		{
//			index++;
//			Object objKey = keys.nextElement();
//			AFileCacheQueue tmpFileCacheQueue = sFileCacheQueues.get(objKey);
//			if ( tmpFileCacheQueue != null )
//			{
//				sbOutResult.append("第" + index + "个Collection:[" + objKey + "," + tmpFileCacheQueue.getSize() + "]\n");
//				if ( level == INFO_LEVEL_DETAIL && sbOutResult.length() > 0 )
//				{
//					sbOutResult.append("-- 明细信息... \n");								
//				}
//			}
//		}
//	}
//		
//	/** 检查 循环 */
//	public void checkCycle(int level,StringBuilder sbOutResult)
//	{
//		int index = 0;
//		Enumeration<String> keys = sCycles.keys();
//		while ( keys.hasMoreElements() )
//		{
//			index++;
//			Object objKey = keys.nextElement();
//			Long num = sCycles.get(objKey);
//			if ( num != null )
//				sbOutResult.append("第" + index + "个Cycle:[" + objKey + "][" + (currTime - lastTime )/1000 + "秒调用" + num.longValue() + "次]\n");
//		}
//	}
//	
//	/** 检测 网络流量 */
//	public void checkNetwork(int level,StringBuilder sbOutResult)
//	{
//		int index = 0;
//		Enumeration<String> keys = sSockets.keys();
//		while ( keys.hasMoreElements() )
//		{
//			index++;
//			Object objKey = keys.nextElement();
//			GessSocketChannel gsc = sSockets.get(objKey);
//			if ( gsc != null )
//				sbOutResult.append("第" + index + "个Socket:[" + gsc.toString()+ "]\n");
//		}
//
//	}
//	
//	//-------------------------获得被监控的对象集合
//	
//	public static ConcurrentHashMap<String,Map> getMaps()
//	{
//		return sMaps;
//	}
//	
//	public static ConcurrentHashMap<String,Collection> getCollections()
//	{
//		return sCollections;
//	}
//	
//	public static ConcurrentHashMap<String,AFileCacheQueue> getFileCacheQueues()
//	{
//		return sFileCacheQueues;
//	}
//	
//	public static ConcurrentHashMap<String,Long> getCycles()
//	{
//		return sCycles;
//	}
//	
//	public static ConcurrentHashMap<String,Thread> getThreads()
//	{
//		return sThreads;
//	}
//	
//	public static ConcurrentHashMap<String,GessSocketChannel> getSockets()
//	{
//		return sSockets;
//	}
//	
//	public static ConcurrentHashMap<String,Socket> getNormalSockets()
//	{
//		return sNormalSockets ;
//	}
//	
//	public static long getCycleInvokeTime()
//	{
//		return (currTime - lastTime )/1000 ;
//	}
//	
//	/**
//	 * 关闭监控程序
//	 */
//	public void close()
//	{
//		bIsRun = false;
//	}
//}