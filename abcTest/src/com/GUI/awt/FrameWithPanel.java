package com.GUI.awt;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;

public class FrameWithPanel extends Frame{
	
	public FrameWithPanel(String str){
		super(str);
	}
	
	public static void main(String[] args) {
		FrameWithPanel fwp = new FrameWithPanel("Frame with Panel");
		Panel pa = new Panel();
		fwp.setSize(200,200);          
		fwp.setBackground(Color.red);                  //框架fr的背景颜色设置为红色        
		fwp.setLayout(null);                  //取消布局管理器 
		pa.setSize(100,100);          
		pa.setBackground(Color.yellow);                  //设置面板pan的背景颜色为黄色         
		fwp.add(pa); //用add方法把面板pan添加到框架fr中        
		fwp.setVisible(true); 
		
	}
}
