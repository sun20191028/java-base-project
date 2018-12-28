package com.base.functionClass.io.ibNio.iaNioTcp;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {
	
	public static void main(String args[]) throws Exception {
		String server = "127.0.0.1"; // Server name or IP address
		// Convert input String to bytes using the default charset
		byte[] argument = "0123456789abcdefghijklmnopqrstuvwxyz".getBytes();

		int servPort = 5500;

		// Create channel and set to nonblocking
		SocketChannel clntChan = SocketChannel.open();
		clntChan.configureBlocking(false);

		// Initiate connection to server and repeatedly poll until complete
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("."); // Do something else
			}
		}
//		Buffer
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		while (totalBytesRcvd < argument.length) {
			if (writeBuf.hasRemaining()) {
				clntChan.write(writeBuf);
			}
			if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}
			totalBytesRcvd += bytesRcvd;
			System.out.print("."); // Do something else
		}

		System.out.println("Received: " + // convert to String per default
											// charset
				new String(readBuf.array(), 0, totalBytesRcvd).length());
		clntChan.close();
	}
}