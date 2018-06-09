/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.functionClass.socket.AIO.demo2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author bence
 */
public class Nio2Server {


	private InetAddress address = InetAddress.getLoopbackAddress();
	private int port = 9999;

	
	public static void main(String[] args) {
		new Nio2Server().serve();
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	private void serve() {
		AsynchronousServerSocketChannel sock = null;
		try {
			sock = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
		} catch (IOException e) {
			throw new RuntimeException("Can not listen on host/address. " + port);
		}

		final AsynchronousServerSocketChannel listener = sock;

		listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

					@Override
					public void completed(AsynchronousSocketChannel ch,Void attr) {

						// accept the next connection
						listener.accept(null, this);

						// handle this connection
						handleConnection(ch);
					}

					@Override
					public void failed(Throwable thrwbl, Void attr) {
						throw new RuntimeException("Accept failed.", thrwbl);
					}
				});
	}

	private void handleConnection(AsynchronousSocketChannel ch) {
		try {
			ch.setOption(StandardSocketOptions.SO_SNDBUF, 4 * 1024);
		} catch (IOException ex) {
			Logger.getLogger(Nio2Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		(new Thread(new WriterTask(new Nio2Writer(ch)))).start();
	}

	private static class WriterTask implements Runnable {

		int bandwidthKilobits = 500;
		int writesPerSec = 100;

		private Nio2Writer writer;

		public WriterTask(Nio2Writer writer) {
			this.writer = writer;
		}

		@Override
		public void run() {
			long serial = 0;
			int packetSize = Math.max(16, bandwidthKilobits / 8 * 1024/ writesPerSec);
			System.out.println("packetSize: " + packetSize);

			while (true) {
				ByteBuffer bb = ByteBuffer.allocate(packetSize);
				bb.putLong(packetSize);
				bb.putLong(serial);
				bb.position(0);
				writer.write(bb);

				serial += 1;

				try {
					Thread.sleep(1000 / writesPerSec);
				} catch (InterruptedException ex) {
					Logger.getLogger(Nio2Server.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}

}
