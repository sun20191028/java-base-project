package com.base.functionClass.socket.ftp.myFtp;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

/**
 * ftpPath 要用服务器路径，即网页上访问的路径 ftpClient.setControlEncoding("gbk"); //这个必须
 * 跟系统保存文件的编码一致。
 * 
 * @author liang
 * 
 */
public class FtpTest {
	private String ftpHost = "192.168.31.5"; // ftp服务器地址
	private int ftpPort = 21; // ftp服务员器端口号
	private String ftpUserName = "ftp"; // anonymous匿名用户登录，不需要密码。administrator指定用户登录
	private String ftpPassword = "zhiang"; // 指定用户密码

	private String ftpPath = "/test/"; // ftp文件存放物理路径
//	private String fileName = "test.txt";// 文件名称
	private String fileName = "test测试文件.txt";//文件名称
	private String localPath = "D:/myFtp/local/"; // 文件路径

	FTPConnection ftpConnection = null;

	@Before
	public void before() {
		ftpConnection = new FTPConnection(ftpHost, ftpPort, ftpUserName,
				ftpPassword);
		ftpConnection.init();
	}

	@Test
	public void test() throws Exception {
		ftpConnection.downloadFile(localPath, ftpPath, fileName);

		ftpPath = "/test1/";
		ftpConnection.uploadFile(localPath, ftpPath, fileName);
	}

	public static void main(String[] args) {
		FtpTest ft = new FtpTest();
		ft.ftpConnection = new FTPConnection(ft.ftpHost, ft.ftpPort,
				ft.ftpUserName, ft.ftpPassword);
		ft.ftpConnection.init();

		ft.ftpConnection.downloadFile(ft.localPath, ft.ftpPath, ft.fileName);
	}
}
