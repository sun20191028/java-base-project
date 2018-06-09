/*
 * Copyright erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.erong.common.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import com.erong.common.base.Pager;
import com.erong.common.entity.Salgrade;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * Salgrade dao. @author joshuaxu Persistence Tools
 */

public class SalgradeDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public SalgradeDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(Salgrade salgrade) throws SQLException {
		try{
			sql="INSERT INTO salgrade ("+
				"GRADE,"+
				"LOSAL,"+
				"HISAL) "+
				"VALUES (?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setDouble(1,salgrade.getGrade());
			pstm.setDouble(2,salgrade.getLosal());
			pstm.setDouble(3,salgrade.getHisal());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static Salgrade populate(ResultSet rs) throws SQLException {
		Salgrade salgrade=new Salgrade();
		salgrade.setGrade(rs.getDouble("GRADE"));
		salgrade.setLosal(rs.getDouble("LOSAL"));
		salgrade.setHisal(rs.getDouble("HISAL"));
		return salgrade;
	}

	public Salgrade findById() throws SQLException {
		try{
			sql="SELECT * FROM salgrade WH";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			Salgrade salgrade=null;
			while(rs.next()){
				salgrade=populate(rs);
			}
			return salgrade;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<Salgrade> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<Salgrade>list =new ArrayList<Salgrade>();
			while(rs.next()){
				Salgrade salgrade=null;
				salgrade=populate(rs);
				list.add(salgrade);
			}
			return list;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public int findCountBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			rs.next();
			return rs.getInt(1);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public Pager findPagerBySql(String sqlString ,String sqlCountString, int pageNo, int pageSize)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlCountString);
			Integer count=null;
			while(rs.next()){
				count=rs.getInt(1);
			}
			sqlString+=" limit "+(pageNo-1)*pageSize+","+pageSize;
			rs = stmt.executeQuery(sqlString);
			List<Salgrade>list =new ArrayList<Salgrade>();
			while(rs.next()){
				Salgrade salgrade=null;
				salgrade=populate(rs);
				list.add(salgrade);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
