package com.jar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
		String sql="insert into fund_change_flow (exch_date,aip_no,change_type,change_bal,can_bal,curr_bal,source_order,deal_time,remark) values (?,?,?,?,?,?,?,?,?)";
//		,'aa','',10000.0,10000.0,10000.0,'aaaa',"+new Timestamp(System.currentTimeMillis())+",'aaaa'
		PreparedStatement pstm=conn.prepareStatement(sql);
		pstm.setString(1, "20170330");
		pstm.setString(2, "aa");
		pstm.setString(3, "01");
		pstm.setDouble(4, 10000.0);
		pstm.setDouble(5, 10000.0);
		pstm.setDouble(6, 10000.0);
		pstm.setString(7, "aaaa");
		pstm.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		pstm.setString(9, "aaaa");
		
		int num=pstm.executeUpdate();
		System.out.println(num);
	}
}
