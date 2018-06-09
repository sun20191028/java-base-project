package com.base.functionClass.socket.ftp.myFtp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;


public class FTPConnection {
	private final static Logger logger = Logger.getLogger(FTPConnection.class);
	
	private static final String FTP_CHARSET = "GBK";
	private static final String LOCAL_CHARSET = "UTF-8";
	private String 	ftpHost = "127.0.0.1"; // ftp服务器地址
	private int 	ftpPort = 21;// ftp服务员器端口号
	private String 	ftpUserName;// anonymous匿名用户登录，不需要密码。administrator指定用户登录
	private String 	ftpPassword;// 指定用户密码
	private FTPClient ftpClient;
	
	/**
	 * 可以理解为组合，所以通过构造器将其初始化
	 * 因为这4个参数是连接所必须的
	 * @param ftpHost
	 * @param ftpPort
	 * @param ftpUserName
	 * @param ftpPassword
	 */
	public FTPConnection(String ftpHost,int ftpPort,String ftpUserName,String ftpPassword){
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.ftpUserName = ftpUserName;
		this.ftpPassword = ftpPassword;
	}
	
	/**
	 * 初始化连接
	 * @throws IOException
	 */
	public void init(){
		ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpHost, ftpPort);
			ftpClient.login(ftpUserName, ftpPassword);
            //判断是否连接成功
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply))
            {
            	closeFtp();
                System.out.println("FTP server refused connection.");
                return;
            }
			ftpClient.setControlEncoding(FTP_CHARSET); //这个必须  跟系统保存文件的编码一致。
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
		} catch (SocketException e) {
			closeFtp();
			logger.error("连接FTP失败..");
			e.printStackTrace();
		} catch (IOException e) {
			closeFtp();
			logger.error("连接FTP失败..");
			e.printStackTrace();
		}
	}
	
	public FTPFile[] getFilesArray(String ftpPath){
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles(ftpPath);
			if(null != files && files.length >0){
				for (FTPFile ftpFile : files) {
					System.out.println(ftpFile.getName());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return files;
	}
	/**
	 * 下载文件
	 * @param ftpPath	-- 文件在ftp 服务器中的路径
	 * @param fileName	-- 需要下载的文件名称
	 * @param localPath	-- 本地存放路径 ，本地依然使用 ftp中的文件名称
	 * @throws IOException
	 */
	public void downloadFile(String localPath,String ftpPath,String fileName){
		File localFile = new File(localPath + File.separatorChar + fileName);
		OutputStream os = null;
		try{
			ftpClient.changeWorkingDirectory(ftpPath);
			os = new FileOutputStream(localFile);
			FTPFile[] files = ftpClient.listFiles(ftpPath);
			fileName = new String(fileName.getBytes(LOCAL_CHARSET),FTP_CHARSET);
			boolean flag = ftpClient.retrieveFile(fileName, os);
		     
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
		}finally{
			closeIo(os);
		}
	}
	
	/**
	 * 上传文件
	 * @param localPath 本地路径
	 * @param fileName	本地文件
	 * @param ftpPath	上传路径
	 */
	public void uploadFile(String localPath,String ftpPath,String fileName) {
		FileInputStream input = null;
		try {
			ftpClient.changeWorkingDirectory(ftpPath);
			input=new FileInputStream(new File(localPath+File.separatorChar+fileName));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");
			ftpClient.storeFile(fileName, input);
			System.out.println("upload succes!");
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + localPath + "文件");
			e.printStackTrace();
		} catch (IOException e1) {
			logger.error("文件读取错误。");
			e1.printStackTrace();
		}finally{
			closeIo(input);
		}
	}
	
	
	
	public void closeIo(Closeable io){
		try {
			if(null != io){
				io.close();
			}
		} catch (IOException e) {
			logger.error("关闭io流失败", e);
		}
	}

	
	/**
	 * 关闭连接
	 */
	public void closeFtp(){
		try {
			if(null != ftpClient){
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			logger.error("用户 :" + ftpUserName + "关闭Host ：" + ftpHost + "的连接失败", e);
		}
	}

}
