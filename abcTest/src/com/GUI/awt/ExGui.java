package com.GUI.awt;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;

public class ExGui{         
	private Frame f;         
	private Button b1;         
	private Button b2;          
	public static void main(String args[]){             
		ExGui that = new ExGui();             
		that.go();     
	}
	
	public void go(){              
		f = new Frame("GUI example");             
		f.setLayout(new FlowLayout());              //设置布局管理器为FlowLayout             
		b1 = new Button("Press Me");  				//按钮上显示字符"Press Me"             
		b2 = new Button("Don't Press Me");             
		f.add(b1);             
		f.add(b2);             
		f.pack();               //紧凑排列，其作用相当于setSize()，即让窗口             尽量小，小到刚刚能够包容住b1、b2两个按钮             
		f.setVisible(true);         
	}   
}
