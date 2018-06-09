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
import com.erong.common.entity.TableName;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * TableName dao. @author joshuaxu Persistence Tools
 */

public class TableNameDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public TableNameDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(TableName tableName) throws SQLException {
		try{
			sql="INSERT INTO table_name ("+
				"T_ID,"+
				"T_NAME_CN_GF,"+
				"T_AGE,"+
				"T_BIRTHDAY,"+
				"T_TELE,"+
				"T_MONEY,"+
				"T_DISCOUNT) "+
				"VALUES (?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1,tableName.getTId());
			pstm.setString(2,tableName.getTNameCnGf());
			pstm.setInt(3,tableName.getTAge());
			pstm.setTimestamp(4,tableName.getTBirthday());
			pstm.setString(5,tableName.getTTele());
			pstm.setLong(6,tableName.getTMoney());
			pstm.setDouble(7,tableName.getTDiscount());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static TableName populate(ResultSet rs) throws SQLException {
		TableName tableName=new TableName();
		tableName.setTId(rs.getLong("T_ID"));
		tableName.setTNameCnGf(rs.getString("T_NAME_CN_GF"));
		tableName.setTAge(rs.getInt("T_AGE"));
		tableName.setTBirthday(rs.getTimestamp("T_BIRTHDAY"));
		tableName.setTTele(rs.getString("T_TELE"));
		tableName.setTMoney(rs.getLong("T_MONEY"));
		tableName.setTDiscount(rs.getDouble("T_DISCOUNT"));
		return tableName;
	}

	public TableName findById(Long t_id) throws SQLException {
		try{
			sql="SELECT * FROM table_name WHERE "+
				"t_id=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setLong(1,t_id);
			rs = pstm.executeQuery();
			TableName tableName=null;
			while(rs.next()){
				tableName=populate(rs);
			}
			return tableName;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<TableName> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<TableName>list =new ArrayList<TableName>();
			while(rs.next()){
				TableName tableName=null;
				tableName=populate(rs);
				list.add(tableName);
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
			List<TableName>list =new ArrayList<TableName>();
			while(rs.next()){
				TableName tableName=null;
				tableName=populate(rs);
				list.add(tableName);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
