/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;


/**
 * People entity. @author joshuaxu Persistence Tools
 */

public class People {
	//Fields
	private String pName;
	private Integer pAge;

	//Property accessors
	public String getPName() {
		return this.pName;
	}

	public void  setPName(String pName) {
		this.pName=pName;
	}
	public Integer getPAge() {
		return this.pAge;
	}

	public void  setPAge(Integer pAge) {
		this.pAge=pAge;
	}
}
