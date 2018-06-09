package com.zzl.bbFaceObject.athis;

public class TestThis {
	String name;
	public static void main(String[] args) {
		TestThis tt=new TestThis();
		tt.name="zhangsan";
		tt.show();
		
	}
	public void show(){
		TestThis te=new TestThis();
		te.jump();
		this.jump();
	}
	public void jump(){
		System.out.println(this+name);
	}
}
