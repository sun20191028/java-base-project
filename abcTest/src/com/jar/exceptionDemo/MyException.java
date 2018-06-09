package com.jar.exceptionDemo;

public class MyException extends Exception {
	private static final long serialVersionUID = 1L;
	private String sErrCode = "";
	public MyException(String sErrCode,String sExpMsg){
		super(sExpMsg);
		this.sErrCode=sErrCode;
	}
	public String getsErrCode() {
		return sErrCode;
	}
		
}
