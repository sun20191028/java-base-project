package com.base.basic.annotation.demo3.test.service;

import com.base.basic.annotation.demo3.annotation.Autowired;
import com.base.basic.annotation.demo3.annotation.Component;
import com.base.basic.annotation.demo3.test.dao.DAOImpl;

@Component
public class ServiceImpl implements IService{
	@Autowired
	private DAOImpl daoImpl;
	
	public boolean getFlag(){
		return daoImpl.getFlag();
	}

	public DAOImpl getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(DAOImpl daoImpl) {
		this.daoImpl = daoImpl;
	}
	
	
}
