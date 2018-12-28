package com.base.functionClass.io.iaio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * io 之性能测试
 * 字符流虽然操作简单，但是，如果不需要解读文件，仅仅是拷贝，字节流性能高太多，而且文件越大，性能越有优势。
 * IO是面向流的，NIO是面向缓冲区的
 * NIO的buffer可以使用直接内存缓冲区，该缓冲区不在JVM中，性能会比JVM的缓冲区略好，
 * 不过会增加相应的垃圾回收的负担，因为JVM缓冲区的性能已经足够好，所以除非在对缓冲有特别要求的地方使用直接缓冲区，尽量使用JVM缓冲。
 * 
 * 结论：
 * 	仅仅是访问文件，nio并不会比io优秀多少
 * 	但是 除了FileChannel，其他的channel都能以非阻塞状态运行，ServerSocketChannel。
 * 
 * 另外一个问题。大量的字符写入，是 字节性能高还是字符性能高。
 * 
 * @author liangpro
 *
 */
public class IOCapabilityTest {
	
	public static void main(String[] args) {
		File sourceFile = new File("src/com/base/functionClass/io/iaio/charByte.txt");
		File destinationFile = new File("E:/destination.txt");
		try {
			if(!destinationFile.exists()){
				destinationFile.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime  = System.currentTimeMillis();
		try {
//			FileWriter write = new FileWriter(sourceFile);
//			String str = "我有一只小毛驴，我从来也不骑,我有一只小毛驴";
//			for (int i = 0; i < 1000; i++) {
//				StringBuffer stb = new StringBuffer();
//				for (int j = 0; j < 18000; j++) {
//					stb.append(str);
//				}
//				write.write(stb.toString());
//			}
			
//			fileOutputStreamTest(sourceFile,destinationFile);
//			bufferedWriterTest(sourceFile,destinationFile);
//			bufferedInputStreamTest(sourceFile,destinationFile);
			
//			nioTest(sourceFile,destinationFile);
			
//			filesTest(sourceFile, destinationFile);
			fileChannelTest(sourceFile, destinationFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("消耗时间：" + (endTime-startTime));
	}
	
	/**
	 * 消耗时间：1168
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void filesTest(File sourceFile,File destinationFile) throws IOException{
		Files.copy(sourceFile.toPath(), new FileOutputStream(destinationFile));
	}
	
	/**
	 * 消耗时间：1015
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void fileChannelTest(File fromFile, File toFile) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;
        try {
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(toFile);
            //得到fileInputStream的文件通道
            fileChannelInput = fileInputStream.getChannel();
            //得到fileOutputStream的文件通道
            fileChannelOutput = fileOutputStream.getChannel();
            //将fileChannelInput通道的数据，写入到fileChannelOutput通道
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (fileChannelInput != null)
                    fileChannelInput.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (fileChannelOutput != null)
                    fileChannelOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 消耗时间：1582ms
	 * 消耗时间：1439ms
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void fileOutputStreamTest(File sourceFile,File destinationFile) throws IOException{
		FileInputStream input = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(destinationFile);
		byte[] buffer = new byte[10240];
		int num = 0;
		while((num = input.read(buffer))>0){
			out.write(buffer);
		}
		out.flush();
		out.close();
		input.close();
	}
	
	/**
	 * 消耗时间：1474
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void bufferedInputStreamTest(File sourceFile,File destinationFile) throws IOException{
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(sourceFile));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destinationFile));
		byte[] buffer = new byte[10240];
		int num = 0;
		while((num = input.read(buffer))>0){
			out.write(buffer);
		}
		out.flush();
		out.close();
		input.close();
	}
	
	
	
	
	
	
	/**
	 * 消耗时间：3462 --600M
	 * 消耗时间：69880 --1200M
	 * 消耗时间：67206  每写入一次 out.flush() 一次
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void bufferedWriterTest(File sourceFile,File destinationFile) throws IOException{
		BufferedReader input = new BufferedReader(new FileReader(sourceFile));
		BufferedWriter out = new BufferedWriter(new FileWriterWithEncoding(destinationFile,"GBK",true), 1024);
//		outputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile,true), "GBK"), 1024);
		char[] buffer = new char[10240];
		int num = 0;
		while((num = input.read(buffer))>0){
			out.write(buffer);
			out.flush();
		}
		out.close();
		input.close();
	}
	
	/**
	 * 消耗时间：1453
	 * @param sourceFile
	 * @param destinationFile
	 * @throws IOException
	 */
	public static void nioTest(File sourceFile,File destinationFile) throws IOException{
		FileInputStream input = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(destinationFile);
		FileChannel inChannel = input.getChannel();
		FileChannel outChannel = out.getChannel();
		ByteBuffer buffer =ByteBuffer.allocate(10240);
		
		int num = 0;
		while((num = inChannel.read(buffer))>0){
			buffer.flip();
			outChannel.write(buffer);
			buffer.clear();
		}
		outChannel.close();
		inChannel.close();
		
	}
}
