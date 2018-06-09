package com.GUI.awt;

import java.awt.Color;
import java.awt.Frame;

public class MyFrame extends Frame{
	
	public MyFrame(String str){
		super(str);
	}
	
	public static void main(String[] args) {
		MyFrame mf = new MyFrame("Hello over here !");
		mf.setSize(600, 600);
		mf.setBackground(Color.red);
		mf.setVisible(true);
		
	}
}
