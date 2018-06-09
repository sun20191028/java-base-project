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
import com.erong.common.entity.Bonus;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * Bonus dao. @author joshuaxu Persistence Tools
 */

public class BonusDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public BonusDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(Bonus bonus) throws SQLException {
		try{
			sql="INSERT INTO bonus ("+
				"ENAME,"+
				"JOB,"+
				"SAL,"+
				"COMM) "+
				"VALUES (?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,bonus.getEname());
			pstm.setString(2,bonus.getJob());
			pstm.setDouble(3,bonus.getSal());
			pstm.setDouble(4,bonus.getComm());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static Bonus populate(ResultSet rs) throws SQLException {
		Bonus bonus=new Bonus();
		bonus.setEname(rs.getString("ENAME"));
		bonus.setJob(rs.getString("JOB"));
		bonus.setSal(rs.getDouble("SAL"));
		bonus.setComm(rs.getDouble("COMM"));
		return bonus;
	}

	public Bonus findById() throws SQLException {
		try{
			sql="SELECT * FROM bonus WH";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			Bonus bonus=null;
			while(rs.next()){
				bonus=populate(rs);
			}
			return bonus;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<Bonus> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<Bonus>list =new ArrayList<Bonus>();
			while(rs.next()){
				Bonus bonus=null;
				bonus=populate(rs);
				list.add(bonus);
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
			List<Bonus>list =new ArrayList<Bonus>();
			while(rs.next()){
				Bonus bonus=null;
				bonus=populate(rs);
				list.add(bonus);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
