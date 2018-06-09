package com.erong.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.erong.common.entity.TableName;
import com.jar.connectPool.erong.ConnectionManager;

public class TableDaoThreadTest extends Thread{
	Connection conn;
	Object obj;
	
	public TableDaoThreadTest(Object obje){
		this.obj=obje;
	}
	
	public void run(){
		try {
			conn = ConnectionManager.getInstance().openConnection();
			TableDao.insert(obj);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
