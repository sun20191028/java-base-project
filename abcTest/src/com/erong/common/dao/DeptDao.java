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
import com.erong.common.entity.Dept;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * Dept dao. @author joshuaxu Persistence Tools
 */

public class DeptDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public DeptDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(Dept dept) throws SQLException {
		try{
			sql="INSERT INTO dept ("+
				"DEPTNO,"+
				"DNAME,"+
				"LOC) "+
				"VALUES (?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,dept.getDeptno());
			pstm.setString(2,dept.getDname());
			pstm.setString(3,dept.getLoc());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static Dept populate(ResultSet rs) throws SQLException {
		Dept dept=new Dept();
		dept.setDeptno(rs.getInt("DEPTNO"));
		dept.setDname(rs.getString("DNAME"));
		dept.setLoc(rs.getString("LOC"));
		return dept;
	}

	public Dept findById(Integer deptno) throws SQLException {
		try{
			sql="SELECT * FROM dept WHERE "+
				"deptno=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,deptno);
			rs = pstm.executeQuery();
			Dept dept=null;
			while(rs.next()){
				dept=populate(rs);
			}
			return dept;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<Dept> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<Dept>list =new ArrayList<Dept>();
			while(rs.next()){
				Dept dept=null;
				dept=populate(rs);
				list.add(dept);
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
			List<Dept>list =new ArrayList<Dept>();
			while(rs.next()){
				Dept dept=null;
				dept=populate(rs);
				list.add(dept);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
