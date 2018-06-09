package com.base.functionClass.socket.ftp;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

public class FtpTest {
    
	static String ftpHost= "192.168.31.5"; //ftp服务器地址
    static int ftpPort = 21;//ftp服务员器端口号
    static String ftpUserName = "ftp";//anonymous匿名用户登录，不需要密码。administrator指定用户登录
    static String ftpPassword = "zhiang";//指定用户密码
    static String ftpPath = "D:/myFtp/test1"; //ftp文件存放物理路径
    static String filePath=""; //文件路径
    static String fileName="";//文件名称
    

    @Test
    public void test() throws Exception {
        filePath="D:/myFtp/test/tt";
        fileName="test文档.txt";
        FtpUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, filePath, fileName);
        
        filePath="D:/myFtp/test1";
        fileName="upload.txt";
        FileInputStream input=new FileInputStream(new File(filePath+File.separatorChar+fileName));
        FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
    }

    public static void  main(String[] args) throws Exception {
        filePath="D:/myFtp/test/tt";
        fileName="test文档.txt";
        FtpUtil.downloadFile(ftpHost, ftpUserName, ftpPassword, ftpPort,
                ftpPath, filePath, fileName);
        
        filePath="D:/myFtp/test1";
        fileName="upload.txt";
        FileInputStream input=new FileInputStream(new File(filePath+File.separatorChar+fileName));
        FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, input);
    }
}

