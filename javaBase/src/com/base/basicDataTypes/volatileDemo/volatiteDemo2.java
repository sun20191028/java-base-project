package com.base.basicDataTypes.volatileDemo;

//public class volatiteDemo2 {
//	private static int puto = 0;
//	private volatile static int vol = 0 ;
//	
//	public static void main(String[] args) {
//		new Thread(){
//			public void run(){
//				System.out.println("开始循环");
//				while(vol == 0 && puto ==0){
//					System.out.println(1);
//				}
//				System.out.println("结束循环");
//				System.exit(0);
//				
//			}
//			
//		}.start();
//		new Thread(){
//			public void run(){
//				try {
//					Thread.sleep(1500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("开始赋值");
//				puto = 1;
//			}
//		}.start();
//		
//		
//		
//	}
//}
import java.lang.*;
import java.io.*;
public class volatiteDemo2 {
	private static int a=0;
//	private volatile static int a=0;
	private static int c=0;
	private volatile static int b=0;
  
	public static void main(String args[]) throws InterruptedException,IOException{
         System.out.println(Thread.currentThread());
         new Thread(){ //第一个线程
        	 public void run(){
        		 System.out.println(this.getName() + " 开始循环。优先级: " + this.getPriority());
        		 int n=0;
        		 while( a==0 ){	//结束程序办法3：添加volatile 的控制变量 b
        			 //	 c++;//结束程序办法1:循环体内不为空
//        			 System.out.println("循环体内不为空：打印输出。"); //结束程序办法1: 循环体内不为空
        			 c++;
        			 a = a;
//        			 System.out.println(1);
//        			try {
//						Thread.sleep(200);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
        		 }
        		 System.out.println("结束循环");
        		 System.out.println(this.getName() + "结束");// 如果打印出Thread-0结束,说明程序退出。
        		 System.exit(0);
     		}
     	}.start();
     	
     	System.out.println(Thread.currentThread());
     	new Thread(){ //第二个线程
     		public void run(){
//     			a = 0;
     	//		this.setPriority(6); // 结束程序办法1:提高第二个线程的优先级
     			System.out.println(this.getName() + "开始循环。优先级: " + this.getPriority());
     			try{
     				Thread.sleep(1500);
     			}catch(InterruptedException e){e.printStackTrace();
     			}
 				System.out.println(this.getName() + " 开始赋值");
            	a=1;
            	System.out.println(this.getName() + " 赋值结束。run()结束。");
            	System.out.println(this.getName() + "结束");
            	try{
     				Thread.sleep(1500);
     			}catch(InterruptedException e){e.printStackTrace();
     				
     			}
     		}  		

     	}.start();
     	System.out.println(Thread.currentThread());
   }
} 