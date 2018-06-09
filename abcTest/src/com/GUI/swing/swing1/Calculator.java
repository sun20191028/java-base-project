package com.GUI.swing.swing1;
import java.awt.BorderLayout;import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Calculator extends javax.swing.JFrame {
	
	private JButton one;
	private JButton two;
	private JButton jButton1;
	private JButton add;
	private JButton equal;
	private JButton dot;
	private JButton zero;
	private JButton sqrtt;
	private JTextField jTextField1;
	private JButton back;
	private JButton divide;
	private JButton multiply;
	private JButton substract;
	private JButton jButton2;
	private JButton nine;
	private JButton eight;
	private JButton seven;
	private JButton six;
	private JButton five;
	private JButton clear;
	private JButton four;
	private JButton three;
	static char ch;
//	private int a;
//	private int b;
//	private int c;
//	private int d;
//	private int e;
//	private int f;
//	private int g;
//	private int h;
//	private int i;
//	private int j;
//	private String k;	
//	BufferedWriter bw;
//	BufferedReader br;
	StringBuffer sf = new StringBuffer ();
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Calculator inst = new Calculator();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
		
	}
	
	public Calculator() {
		super();
		initGUI();
	}
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setModalExclusionType(getModalExclusionType());
			{
				one = new JButton();
				getContentPane().add(one);
				one.setText("1");
				one.setBounds(57, 416, 50, 50);
				one.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							oneMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				two = new JButton();
				getContentPane().add(two);
				two.setText("2");
				two.setBounds(124, 416, 50, 50);
				two.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							twoMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				three = new JButton();
				getContentPane().add(three);
				three.setText("3");
				three.setBounds(185, 416, 50, 50);
				three.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							threeMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				four = new JButton();
				getContentPane().add(four);
				four.setText("4");
				four.setBounds(57, 347, 50, 50);
				four.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							fourMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				five = new JButton();
				getContentPane().add(five);
				five.setText("5");
				five.setBounds(124, 347, 50, 50);
				five.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							fiveMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				six = new JButton();
				getContentPane().add(six);
				six.setText("6");
				six.setBounds(185, 347, 50, 50);
				six.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							sixMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				seven = new JButton();
				getContentPane().add(seven);
				seven.setText("7");
				seven.setBounds(57, 277, 50, 50);
				seven.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							sevenMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				eight = new JButton();
				getContentPane().add(eight);
				eight.setText("8");
				eight.setBounds(124, 277, 50, 50);
				eight.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							eightMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				nine = new JButton();
				getContentPane().add(nine);
				nine.setText("9");
				nine.setBounds(185, 277, 50, 50);
				nine.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							nineMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				zero = new JButton();
				getContentPane().add(zero);
				zero.setText("0");
				zero.setBounds(124, 484, 21, 24);
				zero.setSize(50, 50);
				zero.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							zeroMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				dot = new JButton();
				getContentPane().add(dot);
				dot.setText("·");
				dot.setBounds(185, 484, 17, 24);
				dot.setSize(50, 50);
				dot.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						try {
							dotMouseClicked(evt);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			{
				sqrtt = new JButton();
				getContentPane().add(sqrtt);
				sqrtt.setText("\u221a\uffe3");
				sqrtt.setBounds(57, 484, 62, 24);
				sqrtt.setSize(50, 50);
				sqrtt.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						sqrttMouseClicked(evt);
					}
				});
			}
			{
				equal = new JButton();
				getContentPane().add(equal);
				equal.setText("=");
				equal.setBounds(251, 416, 50, 118);
				equal.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						equalMouseClicked(evt);
					}
				});
			}
			{
				add = new JButton();
				getContentPane().add(add);
				add.setText("+");
				add.setBounds(251, 347, 50, 50);
				add.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						addMouseClicked(evt);
					}
				});
			}
			{
				substract = new JButton();
				getContentPane().add(substract);
				substract.setText("-");
				substract.setBounds(251, 277, 50, 50);
				substract.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						substractMouseClicked(evt);
					}
				});
			}
			{
				multiply = new JButton();
				getContentPane().add(multiply);
				multiply.setText("*");
				multiply.setBounds(251, 211, 50, 50);
				multiply.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						multiplyMouseClicked(evt);
					}
				});
			}
			{
				divide = new JButton();
				getContentPane().add(divide);
				divide.setText("/");
				divide.setBounds(187, 211, 50, 50);
				divide.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						divideMouseClicked(evt);
					}
				});
			}
			{
				back = new JButton();
				getContentPane().add(back);
				back.setText("<--");
				back.setBounds(124, 211, 50, 50);
				back.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						backMouseClicked(evt);
					}
				});
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1);
				jTextField1.setText("");
				jTextField1.setBounds(57, 134, 244, 50);
			}
			{
				clear = new JButton();
				getContentPane().add(clear);
				clear.setText("C");
				clear.setBounds(57, 211, 50, 50);
				clear.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						     clearMouseClicked(evt);
					}
				});
			}
			pack();
			this.setSize(400, 600);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void oneMouseClicked(MouseEvent evt) throws IOException {
		sf.append("1");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for one.mouseClicked
	}
	
	private void twoMouseClicked(MouseEvent evt) throws IOException {
		sf.append("2");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for two.mouseClicked
	}
	
	private void threeMouseClicked(MouseEvent evt) throws IOException {
		sf.append("3");
		this.jTextField1.setText(sf.toString());
	}
	
	private void fourMouseClicked(MouseEvent evt) throws IOException {
		sf.append("4");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for four.mouseClicked
	}
	
	private void fiveMouseClicked(MouseEvent evt) throws IOException {
		sf.append("5");;
		this.jTextField1.setText(sf.toString());
		//TODO add your code for five.mouseClicked
	}
	
	private void sixMouseClicked(MouseEvent evt) throws IOException {
		sf.append("6");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for six.mouseClicked
	}
	

	private void sevenMouseClicked(MouseEvent evt) throws IOException {
		sf.append("7");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for seven.mouseClicked
	}
	
	private void eightMouseClicked(MouseEvent evt) throws IOException {
		sf.append("8");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for eight.mouseClicked
	}
	
	private void nineMouseClicked(MouseEvent evt) throws IOException {
		sf.append("9");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for nine.mouseClicked
	}
	
	private void zeroMouseClicked(MouseEvent evt) throws IOException {
		sf.append("0");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for zero.mouseClicked
	}
	
	private void dotMouseClicked(MouseEvent evt) throws IOException {
		sf.append(".");
		this.jTextField1.setText(sf.toString());
		//TODO add your code for dot.mouseClicked
	}
	
	
	private void addMouseClicked(MouseEvent evt) {
		sf.append("+");
		this.jTextField1.setText(sf.toString());
	}
		
	private void substractMouseClicked(MouseEvent evt) {
		sf.append("-");
		this.jTextField1.setText(sf.toString());
	}
	
	private void multiplyMouseClicked(MouseEvent evt) {
		sf.append("*");
		this.jTextField1.setText(sf.toString());
	}
	
	private void divideMouseClicked(MouseEvent evt) {
		sf.append("/");
		this.jTextField1.setText(sf.toString());
	}
	
	private void sqrttMouseClicked(MouseEvent evt) {
	
		//TODO add your code for sqrtt.mouseClicked
	}	
	
	private void equalMouseClicked(MouseEvent evt) {
		String text1=null;
		int num1=0;
		int num2=0;
		String text = this.jTextField1.getText();
		for (int i = 0; i < text.length(); i++) {
			if(text.charAt(i)=='+'||text.charAt(i)=='-'||text.charAt(i)=='*'||text.charAt(i)=='/'){
				String st1=text.substring(0, i);
				num1=Integer.parseInt(st1);
				text1=text.substring(i+1);
				ch=text.charAt(i);
				break;
			}
		}
		for (int j = 0; j <text1.length(); j++) {
			if(text1.charAt(j)=='+'||text1.charAt(j)=='-'||text1.charAt(j)=='*'||text1.charAt(j)=='/'){
				String st2=text1.substring(0, j);
				num2=Integer.parseInt(st2);
					if(ch=='+'){
						num1=num1+num2;					
					}else if(ch=='-'){
						num1=num1-num2;	
					}else if(ch=='*'){
						num1=num1*num2;	
					}else if(ch=='/'){
						num1=num1/num2;	
					}
					ch=text1.charAt(j);
				text1=text1.substring(j+1);
				continue;
			}
		}
		int in=Integer.parseInt(text1);
		if(ch=='+'){
			num1=num1+in;					
		}else if(ch=='-'){
			num1=num1-in;	
		}else if(ch=='*'){
			num1=num1*in;	
		}else if(ch=='/'){
			num1=num1/in;	
		}
		String stt=num1+"";
		sf.delete(0, sf.length());
		sf.append(stt);
		this.jTextField1.setText(stt);	
	}

	private void backMouseClicked(MouseEvent evt) {
		sf.deleteCharAt(sf.length()-1);
		this.jTextField1.setText(sf.toString());
		//TODO add your code for back.mouseClicked
	}
	
	private void clearMouseClicked(MouseEvent evt) {
		this.jTextField1.setText("");
		sf.delete(0, sf.length());
		//TODO add your code for clear.mouseClicked
	}	
}
