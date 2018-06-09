package com.base.functionClass.io.nio;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class FileChannelTest {
	
	public static void main(String[] args) {
		File inFile = new File("src/com/base/functionClass/io/nio/inFile.txt");
		File outFile = new File("src/com/base/functionClass/io/nio/outFile.txt");
		System.out.println(inFile.length());
		System.out.println(outFile.exists());
//		copyFile(inFile, outFile);
		copyFileByByteBuffer(inFile, outFile);
		
	}

	/**
	 * 普通的IO 复制文件
	 * @param inFile
	 * @param outFile
	 * @throws IOException
	 */
	public static void copyFileByByteArray(File inFile,File outFile) throws IOException{
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(inFile);
			out = new FileOutputStream(outFile);
			byte[] by = new byte[1024];
			int hasRead = 0;
			while((hasRead = in.read(by)) != -1 ){
				out.write(by,0,hasRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeable(in);
			closeable(out);
		}
	}
	
	/**
	 * 通过ByteBuffer 有利于 控制内存，如果复制文件过大，通过映射将占用大量的内存，影响其他程序的性能
	 * @param inFile
	 * @param outFile
	 */
	public static void copyFileByByteBuffer(File inFile,File outFile){
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			inChannel = new FileInputStream(inFile).getChannel();
			outChannel = new FileOutputStream(outFile).getChannel();
			ByteBuffer buff = ByteBuffer.allocate(1024);
			
			while(inChannel.read(buff) != -1){
				buff.flip();
				outChannel.write(buff);
				buff.clear();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(inChannel!=null){
					inChannel.close();
				}
				if(outChannel!=null){
					outChannel.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void copyFileByMappedByteBuffer(File inFile,File outFile){
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			FileInputStream inputStream = new FileInputStream(inFile);
			FileOutputStream outputStream = new FileOutputStream(outFile);
			inChannel = inputStream.getChannel();
			outChannel = outputStream.getChannel();
			MappedByteBuffer buff = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFile.length());
			buff.flip();
			outChannel.write(buff);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				if(inChannel!=null){
					inChannel.close();
				}
				if(outChannel!=null){
					outChannel.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void closeable(Closeable closeable) throws IOException{
		if(closeable != null){
			closeable.close();
		}
	}
	
}
