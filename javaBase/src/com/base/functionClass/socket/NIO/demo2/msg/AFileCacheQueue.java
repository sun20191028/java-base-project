//package com.base.functionClass.socket.NIO.demo2.msg;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//import java.util.concurrent.ConcurrentLinkedQueue;
//
//
///**
// * 支持文件二级缓存的线程安全的字符串消息队列（只支持文本字符串）
// * @author csl 2011.6.8
// *
// */
//public abstract class AFileCacheQueue 
//{
//	public static final int SIZE_TYPE_UPDATE_MEMORY = 1;
//	public static final int SIZE_TYPE_UPDATE_FILE   = 2;
//	public static final int SIZE_TYPE_GET_SUMSIZE   = 3;
//	
//	//内存保留部分的队列
//	protected ConcurrentLinkedQueue<Object> mMemoryDataQueue = new ConcurrentLinkedQueue<Object>();
//	//缓存文件的内存长度索引
//	protected ArrayList<int[]> mFileDataIndex = new ArrayList<int[]>();
//	
//	//允许内存中缓存的最大数量
//	protected int iMemoryMaxSize = 10000 ;
//	//队列总大小
//	protected int iSumSize = 0 ;
//	//内存队列大小
//	protected int iMemorySize = 0;
//	//文件队列大小
//	protected int iFileSize = 0 ;
//	
//	//是否已使用文件缓存
//	protected boolean bIsUseFileCache = false;
//	//文件输出流
//	protected FileOutputStream fos = null ;
//	
//	protected Object objLockFileStream     = new Object();
//	
//	static
//	{
//		AFileCacheQueue.writeLog("清理缓存文件->开始...");
//		
//		//创建缓存中径
//		File dir = new File(Constant.CACHE_PATH);
//		if ( dir.isDirectory() == false || dir.exists() == false )
//		{
//			if ( dir.mkdirs() )
//				AFileCacheQueue.writeLog("创建缓存路径->成功：" + dir.getPath() ) ;
//			else
//				AFileCacheQtueue.writeLog("创建缓存路径->失败：" + dir.getPath() ) ;
//		}
//		
//		//获得所有的缓存文件并清理
//		File[] files = dir.listFiles();
//		for ( int i = 0 ; i < files.length ; i++ )
//		{
//			if ( files[i].isFile() && files[i].getName().endsWith(".fcq.cache"))
//			{
//				if ( files[i].delete() )
//					AFileCacheQueue.writeLog("删除缓存文件->成功：" + files[i].getPath() );
//				else
//					AFileCacheQueue.writeLog("删除缓存文件->失败：" + files[i].getPath() );
//			}
//		}
//		
//		AFileCacheQueue.writeLog("清理缓存文件->结束...");
//		
//	}
//	
//	protected AFileCacheQueue()
//	{
//		this.iMemoryMaxSize = CommUtil.getConfigByInt("FILE_CACHE_QUEUE_MAX_SIZE",10000);
//	}
//	
//	protected AFileCacheQueue(int iMaxSize)
//	{
//		this.iMemoryMaxSize = iMaxSize ;
//	}
//	
//	/**
//	 * 向队列中添加消息内容
//	 * @param msg 字节数组
//	 */
//	protected void addObject(Object obj) throws Exception
//	{
//		boolean bIsWriteToFile = false;
//		
//		synchronized(this)
//		{
//			if ( this.bIsUseFileCache == false )
//			{
//				// 未使用文件缓存 的情况处理
//				if ( this.iSumSize < iMemoryMaxSize )
//				{
//					//当前大小在上限范围内时，直接写入内存
//					bIsWriteToFile = false;
//					this.iSumSize    += 1;
//					this.iMemorySize += 1;
//				}else
//				{
//					//当前大小超出上限范围时，启用文件缓存
//					AFileCacheQueue.writeLog("已超出内存队列上限，开始使用文件缓存：");
//					this.writeSizeInfo();				
//					bIsWriteToFile = true;
//					this.bIsUseFileCache = true;
//					this.iSumSize  += 1;
//					this.iFileSize += 1;
//				}
//	
//			}else
//			{
//				// 已使用文件缓存的情况处理，直接写入文件
//				bIsWriteToFile = true;
//				this.iSumSize  += 1;
//				this.iFileSize += 1;
//			}
//		}
//		
//		if ( bIsWriteToFile == true )
//			this.writeToFile(obj);
//		else
//			this.mMemoryDataQueue.add(obj);
//		
//	}
//	
//	/**
//	 * 获取队列头
//	 */ 
//	protected Object pollObject(boolean bIsDelete) throws Exception
//	{
//		synchronized(this)
//		{
//			//无内容时直接返回
//			if ( this.iSumSize <= 0 )
//				return null ;
//			
//			//如果使用了文件缓存，且内存已读完，则先装载缓存文件内容
//			if ( this.bIsUseFileCache == true && this.iMemorySize <= 0 )
//			{
//				AFileCacheQueue.writeLog("内存队列已读取完毕，开始装载文件缓存到内存中...");
//				this.writeSizeInfo();
//				this.loadFile();
//				this.writeSizeInfo();
//			}
//			
//			// 返回 内存队列中的内容
//			if ( bIsDelete )
//			{
//				this.iSumSize    += -1;
//				this.iMemorySize += -1;				
//			}			
//		}
//		
//		if ( bIsDelete )
//		{
//			//modify by csl 2011.12.7 大压力情况下，当iSumSize的值已经大于1，但由于是多线程操作，
//			//                          poll的结果可能为NULL，如果不加以判断，会导致队列中的值漏掉
//			for ( ;;)
//			{
//				Object obj = this.mMemoryDataQueue.poll();
//				if ( obj != null )
//					return obj;
//			}
//			//end modify 
//		}else
//		{
//			return this.mMemoryDataQueue.peek();
//		}
//		
//	}
//	
//	/** 获得队列大小 */
//	public int getSize()
//	{
//		return this.iSumSize ;
//	}
//		
//	/** 写队列日志 */
//	protected static void writeLog(String sLogMsg)
//	{
//		CommUtil.writeLog("FileCacheQueue", Constant.INFO,sLogMsg);
//	}
//	
//	/** 打印队列大小情况 */
//	protected void writeSizeInfo()
//	{
//		AFileCacheQueue.writeLog("队列大小情况：iSumSize=" + this.iSumSize + ",iMemorySize=" + this.iMemorySize + ",iFileSize=" + this.iFileSize + ".");
//	}
//	
//	/** 将对象写入缓存文件 */
//	protected void writeToFile(Object obj) throws Exception
//	{
//		synchronized(this.objLockFileStream)
//		{
//			if ( fos == null )
//			{
//				File file = new File(Constant.CACHE_PATH + "/" + this.hashCode() + ".fcq.cache" );
//				fos = new FileOutputStream(file,true);
//			}
//			
//			byte[] msg = this.transforToBytes(obj);
//			this.mFileDataIndex.add(new int[]{0,msg.length});
//			fos.write(msg);
//		}
//	}
//	
//	/** 将缓存的内容读取到内存队列中 */
//	protected void loadFile() throws Exception
//	{
//		synchronized(this.objLockFileStream)
//		{
//			if ( fos != null )
//			{
//				fos.flush();
//				fos.close();
//				fos = null ;
//			}
//			
//			FileInputStream fis = null ;
//			int i = 0 ;
//			try
//			{
//				File file = new File(Constant.CACHE_PATH + "/" + this.hashCode() + ".fcq.cache" ) ;
//				fis = new FileInputStream(file);
//				for ( i = 0 ; i < this.mFileDataIndex.size()  && this.iMemorySize < this.iMemoryMaxSize ; i++ )
//				{
//					int[] idx = this.mFileDataIndex.get(i);
//					if ( idx[0] == 1 ) //已读取过
//					{
//						fis.skip(idx[1]);
//					}else
//					{
//						byte[] msg = new byte[idx[1]];
//						int len = fis.read(msg);
//						if ( len != msg.length )
//							throw new Exception("严重错误：实际从缓存文件中读取的长度与预期长度不符！");
//						
//						this.mMemoryDataQueue.add(this.transforToObject(msg));
//						idx[0] = 1; //本条记录已读取
//						this.iFileSize   += -1;
//						this.iMemorySize += 1;
//					}
//				}
//				fis.close();
//				fis = null ;
//				
//				//如果全部读取完毕，则删除文件
//				if ( i == this.mFileDataIndex.size() )
//				{
//					if ( file.delete() == true )
//					{
//						this.bIsUseFileCache = false;
//						this.mFileDataIndex.clear();						
//						AFileCacheQueue.writeLog("文件缓存全部读取，已删除缓存文件：" + file.getPath());
//					}else
//					{
//						AFileCacheQueue.writeLog("文件缓存全部读取，但删除缓存文件失败：" + file.getPath());
//					}
//				}
//				
//			}finally
//			{
//				if ( fis != null )
//					fis.close();
//			}
//		}
//		
//	}
//	
//	/** 将对象转为 字节数组
//	 * @param obj  队列中存储的对象
//	 * @return
//	 */
//	protected abstract byte[] transforToBytes(Object obj) throws Exception ;
//	
//	/** 将从缓存文件中读取的内容转为 队列中的对象
//	 * @param msg 将从缓存文件中读取的内容
//	 * @return
//	 */
//	protected abstract Object transforToObject(byte[] msg) throws Exception ;
//}
//
//
//
