package com.zzl.laFtpclient;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 
import org.apache.commons.net.ftp.FTPReply;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 

/** 
* Apache commons-net 试用一把，看看FTP客户端工具做的好用不 
* 
* @author : leizhimin，2008-8-20 14:00:38。<p> 
*/ 
public class FtpTest { 
    public static void main(String[] args) { 
        testUpload(); 
//        testDownload(); 
    } 

    /** 
     * FTP上传单个文件测试 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 
        int reply;
        try { 
            ftpClient.connect("192.168.22.119",21); 
            ftpClient.login("admin", "admin"); 
            reply = ftpClient.getReplyCode();  
            if (!FTPReply.isPositiveCompletion(reply)) {  
            	ftpClient.disconnect();  
            	System.out.println("success");  
            }  
            
            File srcFile = new File("C:\\grace.jpg"); 
            fis = new FileInputStream(srcFile); 
            
            //设置上传目录 
            ftpClient.changeWorkingDirectory("/admin/pic"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("utf-8"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("3.gif", fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
            	fis.close();
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 

    /** 
     * FTP下载单个文件测试 
     */ 
//    public static void testDownload() { 
//        FTPClient ftpClient = new FTPClient(); 
//        FileOutputStream fos = null; 
//
//        try { 
//            ftpClient.connect("192.168.22.120",21); 
//            ftpClient.login("admin", "admin"); 
//
//            String remoteFileName = "/admin/pic/3.gif"; 
//            fos = new FileOutputStream("c:/down.gif"); 
//
//            ftpClient.setBufferSize(1024); 
//            //设置文件类型（二进制） 
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
//            ftpClient.retrieveFile(remoteFileName, fos); 
//        } catch (IOException e) { 
//            e.printStackTrace(); 
//            throw new RuntimeException("FTP客户端出错！", e); 
//        } finally { 
//            IOUtils.closeQuietly(fos); 
//            try { 
//                ftpClient.disconnect(); 
//            } catch (IOException e) { 
//                e.printStackTrace(); 
//                throw new RuntimeException("关闭FTP连接发生异常！", e); 
//            } 
//        } 
//    } 
} 
