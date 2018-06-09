package com.zzl.ibNio.ibFileChannel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		
		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buff = ByteBuffer.allocate(48);
		buff.clear();
		buff.put(newData.getBytes());
		buff.flip();
		while(buff.hasRemaining()) {
			inChannel.write(buff);
		}

	}

}
