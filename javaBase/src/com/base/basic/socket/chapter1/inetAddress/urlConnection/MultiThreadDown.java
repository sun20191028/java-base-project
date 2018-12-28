package com.base.basic.socket.chapter1.inetAddress.urlConnection;

import java.io.IOException;

public class MultiThreadDown {
	public static void main(String[] args) throws IOException {
		String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544612967700&"
				+ "di=e15e6c49983f535243bf2ecf6adbae24&imgtype=0&src=http%3A%2F%2"
				+ "Fpic.baike.soso.com%2Fugc%2Fbaikepic2%2F1297%2F20170422171931-724996084.jpg%2F0";
		final DownUtil downUtil = new DownUtil(url,"ios.jpg", 4);
		downUtil.download();
		new Thread(){
			public void run(){
				while(downUtil.getCompleteRate()<1){
					System.out.println("已完成：" + downUtil.getCompleteRate());
					try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("已完成：" + downUtil.getCompleteRate());
			}
		}.start();
	}
}
