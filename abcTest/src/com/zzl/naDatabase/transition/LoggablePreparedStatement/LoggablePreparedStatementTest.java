package com.zzl.naDatabase.transition.LoggablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoggablePreparedStatementTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott","tiger");
        PreparedStatement ps = null;  
        ResultSet rs = null;  
        try{  
            String sql = "INSERT INTO TABLE_NAME (T_ID,T_AGE) VALUES (?,?)";
//          ps = conn.prepareStatement(sql);  
            ps = new LoggableStatement(conn,sql);    
            ps.setInt(1, 11);
            ps.setInt(2, 32);
            System.out.println("Executing SQL:　"+((LoggableStatement)ps).getQueryString());   
            
            ps.executeUpdate();
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}
