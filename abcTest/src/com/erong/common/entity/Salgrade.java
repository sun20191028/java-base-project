/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;


/**
 * Salgrade entity. @author joshuaxu Persistence Tools
 */

public class Salgrade {
	//Fields
	private Double grade;
	private Double losal;
	private Double hisal;

	//Property accessors
	public Double getGrade() {
		return this.grade;
	}

	public void  setGrade(Double grade) {
		this.grade=grade;
	}
	public Double getLosal() {
		return this.losal;
	}

	public void  setLosal(Double losal) {
		this.losal=losal;
	}
	public Double getHisal() {
		return this.hisal;
	}

	public void  setHisal(Double hisal) {
		this.hisal=hisal;
	}
}
