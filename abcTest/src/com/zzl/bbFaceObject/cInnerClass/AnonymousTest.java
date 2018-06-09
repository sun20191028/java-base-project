package com.zzl.bbFaceObject.cInnerClass;

interface Product{
	public double getPrice();
	public String getName();
}

public class AnonymousTest {
	public void test(Product p){
		System.out.println("购买了一个"+p.getName()+",花掉了"+p.getPrice());
	}
	public static void main(String[] args) {
		AnonymousTest ano=new AnonymousTest();
		ano.test(new Product(){

			@Override
			public double getPrice() {
				// TODO Auto-generated method stub
				return 567.8;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "AGP显卡";
			}
			
		});
	}
}
