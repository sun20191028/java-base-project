package com.erong.common;
//package com.erong.common.dao;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//
//import com.erong.common.dao.util.MsgUtil;
//import com.erong.common.dao.util.TableUtil;
//import com.jar.connectPool.erong.ConnectionManager;
//import com.jar.connectPool.erong.DbHelper;
//import com.zzl.naDatabase.transition.LoggablePreparedStatement.LoggableStatement;
//
//public class TableDao {
//	Logger logger=Logger.getLogger(TableDao.class);
//	private  Connection conn;
//	private  Statement stmt;
//	private  PreparedStatement pstm;
//	private  ResultSet rs; 
//	private  String sql; 
//	
//	public TableDao() throws Exception {
//		conn=ConnectionManager.getInstance().getConnection();
//		conn.setAutoCommit(false);
//	}
//	
//	public int insert(Object ob) throws Exception{
//		//TODO注意抛出异常。
////		if(ob==null){
////		}
// 		String className=TableUtil.getObjectName(ob);//类名：className？
//		String tableName=TableUtil.getTableName(className).toUpperCase();//表名：tableName
//		
//		/**
//		 * 下面是采用从表中取字段的方法。
//		 * 由于对象可能有些属性的值为null，null没法插入数据库中。所以不能通过数据库去获取属性名
//		 */
////		Set tableFieldNameSet=TableUtil.getField(tableName);//表属性名set集合：tableFieldNameSet
////		int num=tableFieldNameSet.size();
////		String tableFieldNameStr=MsgUtil.setToString(tableFieldNameSet);
////		String tableSymbol=MsgUtil.getTableSymbol(num);//： tableSymbol
////		String sql="insert into "+tableName+" "+tableFieldNameStr+" values "+tableSymbol;
////		String[] tablefieldNameArray=tableFieldNameStr.substring(1, tableFieldNameStr.length()-1).split(",");//表
////		try {
////			pstm = new LoggableStatement(conn,sql);
//////			pstm = conn.prepareStatement(sql);
////			for (int j = 0; j < tablefieldNameArray.length; j++) {
////				String methodName=MsgUtil.getGetMethodName(tablefieldNameArray[j]);
////				Method mm=null;
////				Class cl=ob.getClass();
////				mm=ob.getClass().getMethod(methodName, null);
////				String fieldtype=TableUtil.getFieldNameType(tablefieldNameArray[j], tableName);
////				switch(fieldtype){
////					case "String":
////						pstm.setString(j+1, (String) mm.invoke(ob, null));
////						break;
////					case "Long":
////						pstm.setLong(j+1, (long) mm.invoke(ob, null));
////						break;
////					case "Timestamp":
////						pstm.setTimestamp(j+1, (Timestamp) mm.invoke(ob, null));
////						break;
////					case "Integer":
////						pstm.setInt(j+1, (int) mm.invoke(ob, null));
////						break;
////					case "Double":
////						pstm.setDouble(j+1, (double) mm.invoke(ob, null));
////						break;
////					case "byte[]":
////						pstm.setBlob(j+1,  (Blob) mm.invoke(ob, null));
////						break;
////					default:
////						//TODO 要抛出异常。
////						break;
////				}	
////			}
//		
//		
//		Set<String> setClassFieldName=TableUtil.getSetClassFieldName(ob);
//		int num=setClassFieldName.size();//属性个数：num
//		String tableFieldNameStr=TableUtil.setClassToTableFieldNameStr(setClassFieldName);//表属性名：tableFieldName
//		
//		String tableSymbol=TableUtil.getTableSymbol(num);//： tableSymbol
//		String sql="insert into "+tableName+" "+tableFieldNameStr+" values "+tableSymbol;
////		try {
//			pstm = new LoggableStatement(conn,sql);
////			pstm = conn.prepareStatement(sql);
//			
////			Iterator<String> ite=setClassFieldName.iterator();
////			while(ite.hasNext()){
////				String classFieldName=ite.next();
////				String tableFieldName=TableUtil.classToTableFieldName(classFieldName);
//				
//			String[] tablefieldNameArray=tableFieldNameStr.substring(1, tableFieldNameStr.length()-1).split(",");//表
//			for (int i = 0; i < tablefieldNameArray.length; i++) {
//				String tableFieldName=tablefieldNameArray[i];
//				String classFieldName=TableUtil.fieldNameTableToClass(tableFieldName);
//				String fieldtype=TableUtil.getFieldNameType(classFieldName,ob);
//				Object fieldValue=TableUtil.getFieldValue(classFieldName,ob);
//				switch(fieldtype){
//				case "class java.lang.String":
//					pstm.setString(i+1, (String) fieldValue);//只有index。并不能直接按字段名称来插入。所以上面通过属性名来插数据的设计失败
//					break;
//				case "class java.lang.Long":
//					pstm.setLong(i+1, (long) fieldValue);
//					break;
//				case "class java.sql.Timestamp":
//					pstm.setTimestamp(i+1, (Timestamp) fieldValue);
//					break;
//				case "class java.lang.Integer":
//					pstm.setInt(i+1, (int) fieldValue);
//					break;
//				case "class java.lang.Double":
//					pstm.setDouble(i+1, (double) fieldValue);
//					break;
//				default:
//					//TODO 要抛出异常。
//					break;
//				}	
//			}
//			System.out.println("Executing SQL:　"+((LoggableStatement)pstm).getQueryString());
//			int ret=pstm.executeUpdate();
//			if(ret!=1){
//				//TODO  抛出异常
//				throw new Exception("插入数据失败");
//			}
//			pstm.close();
//			TableUtil.clearThreadLocalMap();
//			return ret;
////		} catch (SQLException e) {
////			// TODO AUTO-GENERATED CATCH BLOCK
////			E.PRINTSTACKTRACE();
////		}FINALLY {
////			DBHELPER.CLOSE(RS);
////			DBHELPER.CLOSE(STMT);
////			DBHELPER.CLOSE(PSTM);
////			TABLEUTIL.CLEANTHREADLOCALMAP();
////		}
////		
////		return 0;
//	}
//}
