package com.base.basicDataTypes.volatileDemo;

public class VolatiteService {
	private boolean asleep = true;
	private int num ;
	
	public void countSomeSleep(){
		System.out.println(num ++);
	}
	
	
	public void doJob(){
		while(asleep){
			countSomeSleep();
		}
	}


	public boolean isAsleep() {
		return asleep;
	}


	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}
	
	
}
