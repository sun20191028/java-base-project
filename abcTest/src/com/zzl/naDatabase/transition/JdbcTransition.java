package com.zzl.naDatabase.transition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTransition {

	// 定义数据库的用户名
		private static final String USERNAME = "root";
		// 定义数据库的密码
		private static final String PASSWORD = "123456";
		// 定义数据库的驱动信息
		private static final String DRIVER = "com.mysql.jdbc.Driver";
		// 定义访问数据库的地址
		private static final String URL = "jdbc:mysql://localhost:3306/mydb";

		// 定义访问数据库的连接
		private static Connection connection;
		// 定义sql语句的执行对象
		private static PreparedStatement pstmt;
		// 定义查询返回的结果集合
		private static ResultSet resultSet;		
		
		public static void main(String[] args) throws SQLException {
			
			
			try {
				Class.forName(DRIVER);
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//用事务，必须设置setAutoCommit false，表示手动提交
				connection.setAutoCommit(false);
//设置事务的隔离级别。
				connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				
				String sql1 = "insert into userinfo(username,pswd) values(?,?)";
				String sql2 = "update userinfo set pswd=? where username = ?";
				pstmt = connection.prepareStatement(sql1);
				pstmt.setString(1, "CAROL");
				pstmt.setString(2, "123");
				pstmt.executeUpdate();
				
				pstmt = connection.prepareStatement(sql2);
				pstmt.setString(1, "123456");
				pstmt.setString(2, "nicole");				
				pstmt.executeUpdate();
/**
 * 提交事务
 */
				connection.commit();
			} catch (Exception e) {
/**
 * catch中的事物回滚。若事务执行有异常，则事务回滚
 */
				connection.rollback(); 
			}finally{
				
				if (pstmt!=null) {
					pstmt.close();
				}
				if (connection!=null) {
					connection.close();
				}
			}

	}

	
}
