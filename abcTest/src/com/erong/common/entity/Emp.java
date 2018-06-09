/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;

import java.sql.Timestamp;

/**
 * Emp entity. @author joshuaxu Persistence Tools
 */

public class Emp {
	//Fields
	private Integer empno;
	private String ename;
	private String job;
	private Integer mgr;
	private Timestamp hiredate;
	private Double sal;
	private Double comm;
	private Integer deptno;

	//Property accessors
	public Integer getEmpno() {
		return this.empno;
	}

	public void  setEmpno(Integer empno) {
		this.empno=empno;
	}
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
	public Integer getMgr() {
		return this.mgr;
	}

	public void  setMgr(Integer mgr) {
		this.mgr=mgr;
	}
	public Timestamp getHiredate() {
		return this.hiredate;
	}

	public void  setHiredate(Timestamp hiredate) {
		this.hiredate=hiredate;
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
	public Integer getDeptno() {
		return this.deptno;
	}

	public void  setDeptno(Integer deptno) {
		this.deptno=deptno;
	}
}
