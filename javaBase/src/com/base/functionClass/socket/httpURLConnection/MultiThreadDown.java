package com.base.functionClass.socket.httpURLConnection;

import java.io.IOException;

public class MultiThreadDown {
	public static void main(String[] args) throws IOException {
		final DownUtil downUtil = new DownUtil("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=5&spn=0&di=44525432210&pi=0&rn=1&tn=baiduimagedetail&is=4273137015%2C2051554123&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=3577784738%2C4111720376&os=4104289939%2C3704718083&simid=0%2C0&adpicid=0&lpn=0&ln=1985&fr=&fmq=1505111434785_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=17&oriquery=&objurl=http%3A%2F%2Fimg.zybus.com%2Fuploads%2Fallimg%2F140523%2F1-140523155107.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgjh54wgvj6_z%26e3Bv54AzdH3Fyt6jgp7h7AzdH3F0bmcb_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0", "ios.png", 4);
		downUtil.download();
//		new Thread(
//				fuction(){
//					while(downUtil.getCompleteRate()<1){
//						
//					}
//		}).start();
	}
}
