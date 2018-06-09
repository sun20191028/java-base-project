/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;


/**
 * Bonus entity. @author joshuaxu Persistence Tools
 */

public class Bonus {
	//Fields
	private String ename;
	private String job;
	private Double sal;
	private Double comm;

	//Property accessors
	public String getEname() {
		return this.ename;
	}

	public void  setEname(String ename) {
		this.ename=ename;
	}
	public String getJob() {
		return this.job;
	}

	public void  setJob(String job) {
		this.job=job;
	}
	public Double getSal() {
		return this.sal;
	}

	public void  setSal(Double sal) {
		this.sal=sal;
	}
	public Double getComm() {
		return this.comm;
	}

	public void  setComm(Double comm) {
		this.comm=comm;
	}
}
