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
import com.erong.common.entity.Emp;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * Emp dao. @author joshuaxu Persistence Tools
 */

public class EmpDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public EmpDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(Emp emp) throws SQLException {
		try{
			sql="INSERT INTO emp ("+
				"EMPNO,"+
				"ENAME,"+
				"JOB,"+
				"MGR,"+
				"HIREDATE,"+
				"SAL,"+
				"COMM,"+
				"DEPTNO) "+
				"VALUES (?,?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,emp.getEmpno());
			pstm.setString(2,emp.getEname());
			pstm.setString(3,emp.getJob());
			pstm.setInt(4,emp.getMgr());
			pstm.setTimestamp(5,emp.getHiredate());
			pstm.setDouble(6,emp.getSal());
			pstm.setDouble(7,emp.getComm());
			pstm.setInt(8,emp.getDeptno());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static Emp populate(ResultSet rs) throws SQLException {
		Emp emp=new Emp();
		emp.setEmpno(rs.getInt("EMPNO"));
		emp.setEname(rs.getString("ENAME"));
		emp.setJob(rs.getString("JOB"));
		emp.setMgr(rs.getInt("MGR"));
		emp.setHiredate(rs.getTimestamp("HIREDATE"));
		emp.setSal(rs.getDouble("SAL"));
		emp.setComm(rs.getDouble("COMM"));
		emp.setDeptno(rs.getInt("DEPTNO"));
		return emp;
	}

	public Emp findById(Integer empno) throws SQLException {
		try{
			sql="SELECT * FROM emp WHERE "+
				"empno=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1,empno);
			rs = pstm.executeQuery();
			Emp emp=null;
			while(rs.next()){
				emp=populate(rs);
			}
			return emp;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<Emp> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<Emp>list =new ArrayList<Emp>();
			while(rs.next()){
				Emp emp=null;
				emp=populate(rs);
				list.add(emp);
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
			List<Emp>list =new ArrayList<Emp>();
			while(rs.next()){
				Emp emp=null;
				emp=populate(rs);
				list.add(emp);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
