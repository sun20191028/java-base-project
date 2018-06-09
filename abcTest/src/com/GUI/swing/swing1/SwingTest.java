package com.GUI.swing.swing1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SwingTest extends JFrame{

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingTest window = new SwingTest();
					window.frame.setVisible(true);
					JPanel panel = new JPanel();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingTest() {
		getContentPane().setLayout(null);
		
		JButton one = new JButton("1");
		one.setBounds(98, 123, 46, 35);
		getContentPane().add(one);
		
		textField = new JTextField();
		textField.setBounds(98, 37, 214, 35);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton two = new JButton("2");
		two.setBounds(154, 123, 46, 35);
		getContentPane().add(two);
		
		JButton three = new JButton("3");
		three.setBounds(210, 123, 46, 35);
		getContentPane().add(three);
		
		JButton button = new JButton("4");
		button.setBounds(98, 168, 46, 35);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("5");
		button_1.setBounds(154, 168, 46, 35);
		getContentPane().add(button_1);
		
		JButton button_2 = new JButton("6");
		button_2.setBounds(210, 168, 46, 35);
		getContentPane().add(button_2);
		
		JButton button_3 = new JButton("7");
		button_3.setBounds(98, 213, 46, 35);
		getContentPane().add(button_3);
		
		JButton button_4 = new JButton("8");
		button_4.setBounds(154, 213, 46, 35);
		getContentPane().add(button_4);
		
		JButton button_5 = new JButton("9");
		button_5.setBounds(210, 213, 46, 35);
		getContentPane().add(button_5);
		
		JButton button_6 = new JButton("0");
		button_6.setBounds(154, 257, 46, 35);
		getContentPane().add(button_6);
		
		JButton button_7 = new JButton("<--");
		button_7.setBounds(98, 258, 46, 35);
		getContentPane().add(button_7);
		
		JButton button_8 = new JButton("=");
		button_8.setBounds(210, 258, 46, 35);
		getContentPane().add(button_8);
		
		JButton button_9 = new JButton("+");
		button_9.setBounds(266, 123, 46, 35);
		getContentPane().add(button_9);
		
		JButton button_10 = new JButton("-");
		button_10.setBounds(266, 168, 46, 35);
		getContentPane().add(button_10);
		
		JButton button_11 = new JButton("×");
		button_11.setBounds(266, 213, 46, 35);
		getContentPane().add(button_11);
		
		JButton button_12 = new JButton("÷");
		button_12.setBounds(266, 257, 46, 35);
		getContentPane().add(button_12);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Container contentPane = frame.getContentPane();  
//		contentPane.setBackground(Color.CYAN);
//		contentPane.add(getContentPane(), BorderLayout.SOUTH); 
	}
}
