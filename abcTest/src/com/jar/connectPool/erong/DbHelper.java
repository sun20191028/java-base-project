package com.jar.connectPool.erong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DbHelper {
	static Logger logger = Logger.getLogger(DbHelper.class);
	
	public static void close(Connection conn){
		if(conn!=null){
			try {
				logger.debug("close(Connection conn)");
				conn.close();	//立即释放此 Connection 对象的数据库和 JDBC 资源，而不是等待它们被自动释放。并不是把它关了，相当于是清理一下。
				conn =null;
				ConnectionManager.connectionThreadLocal.set(conn);
			} catch (SQLException e) {
				logger.error("Close Connection Fail", e);
			}
		}
	}
	public static void close(ResultSet rs){
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error("Close ResultSet Fail", e);
		}
	}
	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			logger.error("Close Statement Fail", e);
		}
	}

	public static void close(PreparedStatement pstm) {
		try {
			if (pstm != null) {
				pstm.close();
				pstm = null;
			}
		} catch (SQLException e) {
			logger.error("Close PreparedStatement Fail", e);
		}
	}
}
