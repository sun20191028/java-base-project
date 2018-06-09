/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;


/**
 * Dept entity. @author joshuaxu Persistence Tools
 */

public class Dept {
	//Fields
	private Integer deptno;
	private String dname;
	private String loc;

	//Property accessors
	public Integer getDeptno() {
		return this.deptno;
	}

	public void  setDeptno(Integer deptno) {
		this.deptno=deptno;
	}
	public String getDname() {
		return this.dname;
	}

	public void  setDname(String dname) {
		this.dname=dname;
	}
	public String getLoc() {
		return this.loc;
	}

	public void  setLoc(String loc) {
		this.loc=loc;
	}
}
