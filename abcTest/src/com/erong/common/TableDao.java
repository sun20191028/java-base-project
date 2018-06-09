package com.erong.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.erong.common.util.TableUtil;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;
import com.zzl.naDatabase.transition.LoggablePreparedStatement.LoggableStatement;

public class TableDao {
	Logger logger=Logger.getLogger(TableDao.class);
//	private static Connection conn;
//	private static Statement stmt;
//	private static PreparedStatement pstm;
//	private static ResultSet rs; 
//	private String sql; 
	/**
	 * 这种设计是会出问题的，因为没有用ThreadLocal维护 conn。多个线程使用同一个conn，或者单线程多次使用，多次关闭的时候，这个conn就不能再操控了。
	 * 所以使用类方法、类属性的时候注意考虑 要不要用ThreadLocal维护。
	 */
//	static{		
//		try {
//			conn=ConnectionManager.getInstance().getConnection();
//			conn.setAutoCommit(false);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	/**
	 * try catch 不能放到insert中，否则外部无法实现多个插入语句或更新语句回滚。就比如同时插入A表、B表，B表失败，A表也要回滚。
	 * @param ob
	 * @return
	 * @throws Exception
	 */
	public static int insert(Object ob) throws Exception{
		Connection conn=ConnectionManager.getInstance().getConnection();
		PreparedStatement pstm=null;
		conn.setAutoCommit(false);
		try{
	 		String className=TableUtil.getObjectName(ob);							//获取类名：className？
			String tableName=TableUtil.getTableName(className).toUpperCase();		//获取表名：tableName
			Set<String> setClassFieldName=TableUtil.getSetClassFieldName(ob);		//获取非空的属性的set集合
			int num=setClassFieldName.size();										//获取非空属性个数：num
			String tableFieldNameStr=TableUtil.setClassToTableFieldNameStr(setClassFieldName);//获取表属性名的insert形式：tableFieldName
			
			String tableSymbol=TableUtil.getTableSymbol(num);							//获得insert的问号?： tableSymbol
			String sql="insert into "+tableName+" ("+tableFieldNameStr+") values "+tableSymbol;
	//		pstm = new LoggableStatement(conn,sql);  //LoggableStatement是一个继承了preparedStatement的类，可以打印出pstm的sql语句
			pstm = conn.prepareStatement(sql);
			String[] tablefieldNameArray=tableFieldNameStr.split(",");//获得表字段的数组
			for (int i = 0; i < tablefieldNameArray.length; i++) {
				String tableFieldName=tablefieldNameArray[i];
				String classFieldName=TableUtil.fieldNameTableToClass(tableFieldName);  //通过表字段名得到类属性名
				String fieldtype=TableUtil.getFieldNameType(classFieldName,ob);			//根据属性获得其类型
				Object fieldValue=TableUtil.getFieldValue(classFieldName,ob);			//根据属性获得其值。
				switch(fieldtype){
				case "class java.lang.String":
					pstm.setString(i+1, (String) fieldValue);//只有index。并不能直接按字段名称来插入。所以上面通过属性名来插数据的设计失败
					break;
				case "class java.lang.Long":
					pstm.setLong(i+1, (long) fieldValue);
					break;
				case "class java.sql.Timestamp":
					pstm.setTimestamp(i+1, (Timestamp) fieldValue);
					break;
				case "class java.lang.Integer":
					pstm.setInt(i+1, (int) fieldValue);
					break;
				case "class java.lang.Double":
					pstm.setDouble(i+1, (double) fieldValue);
					break;
				default:
					//TODO 要抛出异常。
					break;
				}	
			}
			
	//		System.out.println("Executing SQL:　"+((LoggableStatement)pstm).getQueryString());
			int ret=pstm.executeUpdate();
			if(ret!=1){
				//TODO  抛出异常
				throw new Exception("插入数据失败");
			}
			return ret;
		}finally{
			DbHelper.close(pstm);
//			DbHelper.close(conn);	//进去看，会发现，关闭conn与关闭pstm是不同的。关闭conn之后，还赋值为null，并交给连接池。这里肯定是不能关的，否则外面怎么提交？
			TableUtil.clearThreadLocalMap();//前面出异常，没关闭也没问题，出异常都是直接抛出，然后回滚，线程都挂了，后面的肯定没法做。
		}
	}
}
