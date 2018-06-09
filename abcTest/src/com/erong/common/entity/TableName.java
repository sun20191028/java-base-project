/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.entity;

import java.sql.Timestamp;

/**
 * TableName entity. @author joshuaxu Persistence Tools
 */

public class TableName {
	//Fields
	private Long tId;
	private String tNameCnGf;
	private Integer tAge;
	private Timestamp tBirthday;
	private String tTele;
	private Long tMoney;
	private Double tDiscount;

	//Property accessors
	public Long getTId() {
		return this.tId;
	}

	public void  setTId(Long tId) {
		this.tId=tId;
	}
	public String getTNameCnGf() {
		return this.tNameCnGf;
	}

	public void  setTNameCnGf(String tNameCnGf) {
		this.tNameCnGf=tNameCnGf;
	}
	public Integer getTAge() {
		return this.tAge;
	}

	public void  setTAge(Integer tAge) {
		this.tAge=tAge;
	}
	public Timestamp getTBirthday() {
		return this.tBirthday;
	}

	public void  setTBirthday(Timestamp tBirthday) {
		this.tBirthday=tBirthday;
	}
	public String getTTele() {
		return this.tTele;
	}

	public void  setTTele(String tTele) {
		this.tTele=tTele;
	}
	public Long getTMoney() {
		return this.tMoney;
	}

	public void  setTMoney(Long tMoney) {
		this.tMoney=tMoney;
	}
	public Double getTDiscount() {
		return this.tDiscount;
	}

	public void  setTDiscount(Double tDiscount) {
		this.tDiscount=tDiscount;
	}
}
