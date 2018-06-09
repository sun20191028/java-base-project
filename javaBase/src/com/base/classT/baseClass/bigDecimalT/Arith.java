package com.base.classT.baseClass.bigDecimalT;

import java.math.BigDecimal;

/**
 * 工具方法：对double值进行精确的 加、减、乘、除计算。
 * @author liang
 *
 */
public class Arith {
	//默认除法精度
	private static final int DEF_DIV_SCALE = 10;
	//私有构造器，让这个类不能实例化对象。
	private Arith(){
		
	}
	
	public static double add(double v1,double v2){
		return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2)).doubleValue();
	}
	
	public static double subtract(double v1,double v2){
		return BigDecimal.valueOf(v1).subtract(BigDecimal.valueOf(v2)).doubleValue();
	}
	
	public static double multiply(double v1,double v2){
		return BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(v2)).doubleValue();
	}
	
	public static double divide(double v1,double v2,int scale){
		return divide(v1, v2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static double divide(double v1,double v2,int scale,int style){
		BigDecimal big1 = BigDecimal.valueOf(v1);
		BigDecimal big2 = BigDecimal.valueOf(v2);
		return big1.divide(big2,scale,style).doubleValue();
	}
	
	public static void main(String[] args) {
		
	}
	
}
