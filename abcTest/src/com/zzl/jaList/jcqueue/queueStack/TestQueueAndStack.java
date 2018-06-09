package com.zzl.jaList.jcqueue.queueStack;

import java.net.*;
import java.util.*;
public class TestQueueAndStack {
	 /**
     * 测试队列
     * <pre>
     * 队列特点，先进先出，后进后出，火车过山洞例子
     * </pre>
     */
    static void testQueue(){
        Queue<String> queue=new LinkedList<String>();
        //添加几个元素
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");
        queue.add("5");
        System.out.println("队列中的元素�?:"+queue);
        //弹出元素、移除元�?
        queue.poll();
        System.out.println("队列中的元素�?:"+queue);
        //查看队列中首个元素，并不移除
        String peek=queue.peek();
        System.out.println("查看队列中首个元素，并不移除:"+peek);
        System.out.println("队列中的元素�?:"+queue);
    }
    /**
     * 测试�?
     * <pre>
     * 先进后出，后进先出，水桶倒水
     * </pre>
     */
    static void testStack(){
        Stack<String> stack=new Stack<String>();
        //添加几个元素
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        stack.push("e");
        stack.add("1");
        stack.add("2");
        stack.add("3");
        stack.add("4");
        stack.add("5");
        System.out.println("栈中的元素是:"+stack);
        //弹出元素
        stack.pop();
        System.out.println("栈中的元素是:"+stack);
        //查看栈中首个元素，并不移�?
        String peek=stack.peek();
        System.out.println("查看栈中首个元素，并不移�?:"+peek);
        System.out.println("栈中的元素是:"+stack);
    }
    
    public static void main(String[] args) {
    	testQueue();
    	testStack();
	}
}
