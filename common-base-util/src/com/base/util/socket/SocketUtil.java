package com.base.util.socket;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import com.base.util.BaseUtil;


public class SocketUtil {
	

	/**
	 * 读取 前4个字段为 数据长度的  输入流数据
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readLengthValue(InputStream inputStream)
			throws IOException {
		byte[] lengthByte = new byte[4];
		int length = inputStream.read(lengthByte);// 读取前4位，前4位是整个 数据的长度。
		if(length != 4)
			throw new EOFException();
		int receiveLength = BaseUtil.byteArrayToInt(lengthByte);
		int bufferSize = (receiveLength < 4096) ? receiveLength : 4096;
		byte[] read = read(inputStream, receiveLength, bufferSize);
		return read;
	}
	
	/**
	 * 读取指定 长度的 输入流数据。一次连接 只能读一次，只能通过 客户端关闭 输出流，服务端才能跳出阻塞。
	 * @param inputStream
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(InputStream inputStream, int bufferSize)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[bufferSize];
		int num = inputStream.read(buffer);
		while (num != -1) {
			baos.write(buffer, 0, num);
			num = inputStream.read(buffer);
		}
		baos.flush();
		return baos.toByteArray();
	}

	/**
	 * 以bufferSize为单位，读取length的数据。
	 * @param inputStream
	 * @param length
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(InputStream inputStream, int length,
			int bufferSize) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[bufferSize];
		int totalNum = 0;
		int num = 0;
		int readLength = bufferSize;
		while (totalNum < length) {
			num = inputStream.read(buffer, 0, readLength);
			if (num <= 0) {
				break;
			}
			baos.write(buffer, 0, num);
			totalNum += num;
			readLength = (length - totalNum > bufferSize) ? bufferSize : length - totalNum;
		}
		baos.flush();

		return baos.toByteArray();
	}

	/**
	 * 发送 前4个字段为 数据长度的  数据
	 * @param outputStream
	 * @param sendBytes
	 * @throws IOException
	 */
	public static void sendLengthValue(OutputStream outputStream,
			byte[] sendBytes) throws IOException {
		byte[] sendxml=addLength(sendBytes);
		outputStream.write(sendxml);
		outputStream.flush();
	}
	public static byte[] addLength(byte[] bytes) {
		int totalLength = bytes.length + 4;
		byte[] lengthedArray = new byte[totalLength];
		System.arraycopy(BaseUtil.intToByteArray(bytes.length), 0, lengthedArray, 0, 4);
		System.arraycopy(bytes, 0, lengthedArray, 4, bytes.length);
		return lengthedArray;
	}
	
	
	public static Socket createNewSocket(String host, int port,int connectTimeout, int readTimeout) throws SocketException,
			IOException {
		Socket socket = new Socket();
		socket.setReuseAddress(true);
		socket.setSoLinger(true, 0);
		socket.setSoTimeout(readTimeout);
		socket.connect(new InetSocketAddress(host, port), connectTimeout);
		return socket;
	}
}
