package com.base.classT.baseClass.bigDecimalT;

interface Er{
	double PI = 3.14;
	void tongdian();
	void dd()	;
}

class Dfqc implements Er{

	@Override
	public void tongdian() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dd() {
		// TODO Auto-generated method stub
		
	}
	public void duan(){
		
	}
}


public class Test {
	public static void main(String[] args) {
//		Er.PI = 3;
		System.out.println(Er.PI);
		Dfqc dfqc = new Dfqc();
//		dfqc.PI = 3;
	}
}
