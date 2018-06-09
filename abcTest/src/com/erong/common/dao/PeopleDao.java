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
import com.erong.common.entity.People;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

/**
 * People dao. @author joshuaxu Persistence Tools
 */

public class PeopleDao {
	private  Connection conn;
	private  Statement stmt;
	private  PreparedStatement pstm;
	private  ResultSet rs; 
	private  String sql; 
	public PeopleDao() throws Exception {
		conn=ConnectionManager.getInstance().getConnection();
	}

	public int insert(People people) throws SQLException {
		try{
			sql="INSERT INTO people ("+
				"P_NAME,"+
				"P_AGE) "+
				"VALUES (?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,people.getPName());
			pstm.setInt(2,people.getPAge());
			int ret=pstm.executeUpdate();
			pstm.close();
			return ret;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static People populate(ResultSet rs) throws SQLException {
		People people=new People();
		people.setPName(rs.getString("P_NAME"));
		people.setPAge(rs.getInt("P_AGE"));
		return people;
	}

	public People findById(String p_name) throws SQLException {
		try{
			sql="SELECT * FROM people WHERE "+
				"p_name=? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1,p_name);
			rs = pstm.executeQuery();
			People people=null;
			while(rs.next()){
				people=populate(rs);
			}
			return people;
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public List<People> findBySql(String sqlString)throws SQLException {
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlString);
			List<People>list =new ArrayList<People>();
			while(rs.next()){
				People people=null;
				people=populate(rs);
				list.add(people);
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
			List<People>list =new ArrayList<People>();
			while(rs.next()){
				People people=null;
				people=populate(rs);
				list.add(people);
			}
			return new Pager(pageSize,pageNo,count,list);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

}
