package com.jar.dataTableToEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;
import com.util.getProperties.erong.BaseConfigUtil;

public class EntityCreateOracle {
	private static final Logger logger = Logger.getLogger(EntityCreateOracle.class);
	private StringBuffer sBuffer = new StringBuffer();
	private StringBuffer sHeadBuffer = new StringBuffer();
	private boolean hasDateType;
	private boolean hasDatetimeType;
	public String createFilePath = "";
	public String projectPackage = "";

	public String creatEntityFile() throws Exception {
		ConnectionManager.getInstance().openConnection();
		creatEntity();
		creatDao();
		return null;
	}

	public String creatEntity() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			sql = "SELECT * FROM USER_TABLES";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString("table_name").toLowerCase();
				logger.info(tableName);
				//TODO 为什么要排除这张表？
				if (tableName.toLowerCase().equals("con_test"))
					continue;
				this.sBuffer = new StringBuffer();
				this.sHeadBuffer = new StringBuffer();
				this.hasDateType = false;
				this.hasDatetimeType = false;
				/**
				 * 将表名 改装成 对应表的类名 删除"_" 每个"_"之后大写 ，首字母大写
				 */
				String tableClassName = getTableClassName(tableName);
				gerEntity(tableName);	//拼接类 的文件体
				gerEntityHead(tableClassName);	//拼接 类的 文件头
				/**
				 * 创建文件夹 fold
				 */
				File path = new File(this.createFilePath + "entity");
				if(!path.exists()){
					path.mkdirs();
				}
				/**
				 * 创建文件
				 */
				File file = new File(this.createFilePath + "entity\\"
						+ tableClassName + ".java");
				if (!(file.exists()))
					file.createNewFile();
				else {
					file.delete();
					file.createNewFile();
				}
				//将 其写入文件中。
				FileOutputStream out = new FileOutputStream(file, true);
				out.write(this.sHeadBuffer.toString().getBytes("UTF-8"));
				out.write(this.sBuffer.toString().getBytes("UTF-8"));
				out.close();
			}

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(pstm);
		}

		return null;
	}
	
	/**
	 *  将表名 改装成 对应表的类名 删除"_" 每个"_"之后大写 ，首字母大写
	 * @param tableName
	 * @return
	 */
	private String getTableClassName(String tableName){
		String tableClassName="";
		boolean isUnderLine = false;
		for (int i = 0; i < tableName.length(); ++i) {
			//TODO ???什么意思嘛？
			if (tableName.charAt(i) == '_') {
				isUnderLine = true;
			} else if (isUnderLine) {//牛，这思维，没到一次‘_’下一个字母大写，并跳过这一个字母。
				tableClassName = tableClassName + String.valueOf(tableName.charAt(i)).toUpperCase();
				isUnderLine = false;
			} else {
				tableClassName = tableClassName + tableName.charAt(i);
			}
		}
		tableClassName = tableClassName.substring(0, 1).toUpperCase() + tableClassName.substring(1);
		return tableClassName;
	}
	
	/**
	 * 拼接类的文件体
	 * @param tableName
	 */
	private void gerEntity(String tableName) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn=ConnectionManager.getInstance().getConnection();
//			sql="select * from \""+tableName+"\"";
			sql="select * from "+tableName;
			pstm=conn.prepareStatement(sql);
			rs=pstm.executeQuery();
			ResultSetMetaData meta =rs.getMetaData();
			//首先拼接 声明属性语句
			sBuffer.append("\t//Fields\n");  
			for (int i = 0; i < meta.getColumnCount(); i++) {
				logger.info(meta.getColumnName(i + 1) + ":"+ meta.getColumnType(i + 1) + " "+ meta.getColumnTypeName(i + 1) + " "
						+ meta.getPrecision(i + 1) + " " + meta.getScale(i + 1)+ " " + meta.getColumnClassName(i + 1));
				String fieldName = meta.getColumnName(i + 1).toLowerCase(); //取每列的列名，并转换成小写。
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),meta.getPrecision(i + 1), meta.getScale(i + 1));
				this.sBuffer.append("\tprivate " + fieldType + " "
						+ getFieldClassName(fieldName) + ";\n");
			}
			this.sBuffer.append("\n");
			//拼接 get 和set方法 
			this.sBuffer.append("\t//Property accessors\n");
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldClassName = getFieldClassName(fieldName);
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				this.sBuffer.append("\tpublic " + fieldType + " get"
						+ fieldClassName.substring(0, 1).toUpperCase()
						+ fieldClassName.substring(1) + "() {\n");
				this.sBuffer.append("\t\treturn this." + fieldClassName + ";\n");
				this.sBuffer.append("\t}\n");
				this.sBuffer.append("\n");
				this.sBuffer.append("\tpublic void  set"
						+ fieldClassName.substring(0, 1).toUpperCase()
						+ fieldClassName.substring(1) + "(" + fieldType + " "
						+ fieldClassName + ") {\n");
				this.sBuffer.append("\t\tthis." + fieldClassName + "="
						+ fieldClassName + ";\n");
				this.sBuffer.append("\t}\n");
			}
			this.sBuffer.append("}\n");

			return;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}finally{
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}
	private String getFieldType(String columnType, int precision, int scale) {
		if(("CHAR".equalsIgnoreCase(columnType))||("VARCHAR".equalsIgnoreCase(columnType))||("VARCHAR2".equalsIgnoreCase(columnType))){
			return "String";
		}
		if(("DATE".equalsIgnoreCase(columnType))||("DATETIME".equalsIgnoreCase(columnType))){
			this.hasDatetimeType=true;
			return "Timestamp";
		}
		if("NUMBER".equalsIgnoreCase(columnType)){
			if(scale==0){
				if(precision<10)
					return "Integer";
				return "Long";
			}
			return "Double";
		}
		if (columnType.equals("INTEGER")) {
			return "Integer";
		}
		if (columnType.equals("TINYINT")) {
			return "Integer";
		}
		if (columnType.equals("DOUBLE")) {
			return "Double";
		}
		if (columnType.equals("BLOB")) {
			return "byte[]";
		}
		if (columnType.equals("MEDIUMBLOB")) {
			return "byte[]";
		}
		return "";
	}
	
	/**
	 * 属性名处理，跟类名处理方式它这是一样的。
	 * @param fieldName
	 * @return
	 */
	private String getFieldClassName(String fieldName) {
		String fieldClassName = "";
		boolean isUnderLine = false;
		for (int i = 0; i < fieldName.length(); ++i) {
			if (fieldName.charAt(i) == '_') {
				isUnderLine = true;
			} else if (isUnderLine) {
				fieldClassName = fieldClassName
						+ String.valueOf(fieldName.charAt(i)).toUpperCase();
				isUnderLine = false;
			} else {
				fieldClassName = fieldClassName + fieldName.charAt(i);
			}
		}

		return fieldClassName;
	}
	/**
	 * 拼接类的文件头
	 * @param tableClassName
	 */
	private void gerEntityHead(String tableClassName) {
		/**
		 * 拼接版权
		 */
		this.sHeadBuffer.append("/*\n");
		this.sHeadBuffer.append(" * Copyright erong software, Inc. All rights reserved.\n");
		this.sHeadBuffer.append(" * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		/**
		 * 拼接 package
		 */
		this.sHeadBuffer.append("package " + this.projectPackage + ".entity;\n");
		this.sHeadBuffer.append("\n");
		/**
		 * 拼接 导入
		 */
		if (this.hasDateType)
			this.sHeadBuffer.append("import java.sql.Date;\n");
		if (this.hasDatetimeType)
			this.sHeadBuffer.append("import java.sql.Timestamp;\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("/**\n");
		this.sHeadBuffer.append(" * " + tableClassName
				+ " entity. @author joshuaxu Persistence Tools\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		/**
		 * 拼接 声明 类名
		 */
		this.sHeadBuffer.append("public class " + tableClassName + " {\n");
	}
	
	
	
	public String creatDao() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			sql = "SELECT * FROM USER_TABLES";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1).toLowerCase();
				logger.info(tableName);
				if (tableName.toLowerCase().equals("con_test"))
					continue;
				this.sBuffer = new StringBuffer();
				this.sHeadBuffer = new StringBuffer();
				this.hasDateType = false;

				String tableClassName = getTableClassName(tableName);
				gerDaoHead(tableClassName);
				gerDaoInsert(tableName);
				gerDaoPopulate(tableName);
				gerDaoFindById(tableName);
				gerDaoFindBySql(tableName);
				gerDaoFindCountBySql(tableName);
				gerDaoFindPagerBySql(tableName);
				gerDaoFoot();
				
				File path=new File(this.createFilePath + "dao");
				if(!(path.exists())){
					path.mkdirs();
				}
				File file = new File(this.createFilePath + "dao\\"
						+ tableClassName + "Dao.java");
				if (!(file.exists()))
					file.createNewFile();
				else {
					file.delete();
					file.createNewFile();
				}

				FileOutputStream out = new FileOutputStream(file, true);
				out.write(this.sHeadBuffer.toString().getBytes("UTF-8"));
				out.write(this.sBuffer.toString().getBytes("UTF-8"));
				out.close();
			}

			return "";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(pstm);
		}

		return null;
	}
	/**
	 * 拼接 copy、 package、 import、 类名 、属性声明、conn
	 * @param tableClassName
	 */
	private void gerDaoHead(String tableClassName) {
		this.sHeadBuffer.append("/*\n");
		this.sHeadBuffer.append(" * Copyright erong software, Inc. All rights reserved.\n");
		this.sHeadBuffer.append(" * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("package " + this.projectPackage + ".dao;\n");
		this.sHeadBuffer.append("\n");
		/*
		 * 导入要用到的类
		 */
		this.sHeadBuffer.append("import java.util.List;\n");
		this.sHeadBuffer.append("import java.util.ArrayList;\n");
		this.sHeadBuffer.append("import java.sql.Connection;\n");
		this.sHeadBuffer.append("import java.sql.PreparedStatement;\n");
		this.sHeadBuffer.append("import java.sql.ResultSet;\n");
		this.sHeadBuffer.append("import java.sql.Statement;\n");
		this.sHeadBuffer.append("import java.sql.SQLException;\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("import com.erong.base.db.ConnectionManager;\n");
		this.sHeadBuffer.append("import com.erong.common.base.Pager;\n");
		this.sHeadBuffer.append("import com.erong.base.db.DbHelper;\n");
		this.sHeadBuffer.append("import " + this.projectPackage + ".entity."+ tableClassName + ";\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("/**\n");
		this.sHeadBuffer.append(" * " + tableClassName + " dao. @author joshuaxu Persistence Tools\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("public class " + tableClassName + "Dao {\n");
		this.sHeadBuffer.append("\tprivate  Connection conn;\n");
		this.sHeadBuffer.append("\tprivate  Statement stmt;\n");
		this.sHeadBuffer.append("\tprivate  PreparedStatement pstm;\n");
		this.sHeadBuffer.append("\tprivate  ResultSet rs; \n");
		this.sHeadBuffer.append("\tprivate  String sql; \n");

		this.sHeadBuffer.append("\tpublic " + tableClassName + "Dao() throws Exception {\n");
		this.sHeadBuffer.append("\t\tconn=ConnectionManager.getInstance().getConnection();\n");
		this.sHeadBuffer.append("\t}\n");
		this.sHeadBuffer.append("\n");
	}
	
	private void gerDaoInsert(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			this.sBuffer.append("\tpublic int insert(" + entityClassName + " " + entityName + ") throws SQLException {\n");
			this.sBuffer.append("\t\ttry{\n");
			this.sBuffer.append("\t\t\tsql=\"INSERT INTO " + tableName + " (\"+\n");
			conn = ConnectionManager.getInstance().getConnection();
			sql = "select * from " + tableName;
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();

			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				if (!(fieldType.equals("byte[]"))) {
					this.sBuffer.append("\t\t\t\t\""+ meta.getColumnName(i + 1));
					this.sBuffer.append(",\"+\n");
				}
			}
			this.sBuffer.delete(this.sBuffer.length() - 4,this.sBuffer.length());//删除 多拼接的“,”之后的东西。
			this.sBuffer.append(") \"+\n");
			this.sBuffer.append("\t\t\t\t\"VALUES (");
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				if (!(fieldType.equals("byte[]"))) {  //如果数据类型为byte数组，则不能用“?”代替。
					this.sBuffer.append("?,");
				}
			}
			this.sBuffer.deleteCharAt(this.sBuffer.length() - 1);
			this.sBuffer.append(")\";\n");
			this.sBuffer.append("\t\t\tpstm = conn.prepareStatement(sql);\n");
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldClassName = getFieldClassName(fieldName);
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				if (fieldType.equals("Integer"))
					this.sBuffer.append("\t\t\tpstm.setInt("
							+ String.valueOf(i + 1) + "," + entityName + ".get"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "());\n");
				else if (fieldType.equals("byte[]"))
					this.sBuffer.append("\t\t\t//" + fieldClassName
							+ "fieldName is blob\n");
				else {
					this.sBuffer.append("\t\t\tpstm.set" + fieldType + "("
							+ String.valueOf(i + 1) + "," + entityName + ".get"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "());\n");
				}
			}
			this.sBuffer.append("\t\t\tint ret=pstm.executeUpdate();\n");
			this.sBuffer.append("\t\t\tpstm.close();\n");
			this.sBuffer.append("\t\t\treturn ret;\n");
			this.sBuffer.append("\t\t} finally {\n");
			this.sBuffer.append("\t\t\tDbHelper.close(rs);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(stmt);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(pstm);\n");
			this.sBuffer.append("\t\t}\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");

			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}
	
	private void gerDaoPopulate(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase()
				+ entityClassName.substring(1);
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			this.sBuffer.append("\tpublic static " + entityClassName
					+ " populate(ResultSet rs) throws SQLException {\n");
			this.sBuffer.append("\t\t" + entityClassName + " " + entityName
					+ "=new " + entityClassName + "();\n");
			conn = ConnectionManager.getInstance().getConnection();
			sql = "select * from " + tableName;
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldClassName = getFieldClassName(fieldName);
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				if (fieldType.equals("Integer"))
					this.sBuffer.append("\t\t" + entityName + ".set"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "(rs.getInt(\""
							+ meta.getColumnName(i + 1) + "\"));\n");
				else if (fieldType.equals("byte[]"))
					this.sBuffer.append("\t\t//" + fieldClassName
							+ " is blob\n");
				else {
					this.sBuffer.append("\t\t" + entityName + ".set"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "(rs.get"
							+ fieldType + "(\"" + meta.getColumnName(i + 1)
							+ "\"));\n");
				}
			}
			this.sBuffer.append("\t\treturn " + entityName + ";\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");

			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	private void gerDaoFindById(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase()
				+ entityClassName.substring(1);
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			this.sBuffer.append("\tpublic " + entityClassName + " findById(");
			conn = ConnectionManager.getInstance().getConnection();
			DatabaseMetaData dbmeta = conn.getMetaData();
			rs = dbmeta.getPrimaryKeys(null, null, tableName.toUpperCase());

			Map keyMap = new HashMap();
			while (rs.next()) {
				keyMap.put(rs.getString(4), "");
			}

			sql = "select * from " + tableName;
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String colName = meta.getColumnName(i + 1);
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getPrecision(i + 1), meta.getScale(i + 1));
				if (keyMap.containsKey(colName)) {
					keyMap.remove(colName);
					keyMap.put(fieldName, fieldType);
				}

			}

			for (Iterator iterator = keyMap.keySet().iterator(); iterator
					.hasNext();) {
				String keyName = (String) iterator.next();
				String keyType = (String) keyMap.get(keyName);
				this.sBuffer.append(keyType + " " + keyName + ",");
			}
			this.sBuffer.deleteCharAt(this.sBuffer.length() - 1);
			this.sBuffer.append(") throws SQLException {\n");

			this.sBuffer.append("\t\ttry{\n");
			this.sBuffer.append("\t\t\tsql=\"SELECT * FROM " + tableName
					+ " WHERE \"+\n");
			for (Iterator iterator = keyMap.keySet().iterator(); iterator
					.hasNext();) {
				String keyName = (String) iterator.next();
				this.sBuffer.append("\t\t\t\t\"" + keyName + "=? AND \"+\n");
			}
			this.sBuffer.delete(this.sBuffer.length() - 7,
					this.sBuffer.length());
			this.sBuffer.append("\";\n");
			this.sBuffer.append("\t\t\tpstm = conn.prepareStatement(sql);\n");
			int index = 0;
			for (Iterator iterator = keyMap.keySet().iterator(); iterator
					.hasNext(); ++index) {
				String keyName = (String) iterator.next();
				String keyType = (String) keyMap.get(keyName);
				if (keyType.equals("Integer"))
					this.sBuffer.append("\t\t\tpstm.setInt(" + (index + 1)
							+ "," + keyName + ");\n");
				else
					this.sBuffer.append("\t\t\tpstm.set" + keyType + "("
							+ (index + 1) + "," + keyName + ");\n");
			}
			this.sBuffer.append("\t\t\trs = pstm.executeQuery();\n");
			this.sBuffer.append("\t\t\t" + entityClassName + " " + entityName
					+ "=null;\n");
			this.sBuffer.append("\t\t\twhile(rs.next()){\n");
			this.sBuffer.append("\t\t\t\t" + entityName + "=populate(rs);\n");
			this.sBuffer.append("\t\t\t}\n");
			this.sBuffer.append("\t\t\treturn " + entityName + ";\n");
			this.sBuffer.append("\t\t} finally {\n");
			this.sBuffer.append("\t\t\tDbHelper.close(rs);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(stmt);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(pstm);\n");
			this.sBuffer.append("\t\t}\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	private void gerDaoFindBySql(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase()
				+ entityClassName.substring(1);

		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			this.sBuffer.append("\tpublic List<" + entityClassName
					+ "> findBySql(String sqlString)throws SQLException {\n");
			this.sBuffer.append("\t\ttry{\n");

			this.sBuffer.append("\t\t\tstmt = conn.createStatement();\n");
			this.sBuffer.append("\t\t\trs = stmt.executeQuery(sqlString);\n");
			this.sBuffer.append("\t\t\tList<" + entityClassName
					+ ">list =new ArrayList<" + entityClassName + ">();\n");
			this.sBuffer.append("\t\t\twhile(rs.next()){\n");
			this.sBuffer.append("\t\t\t\t" + entityClassName + " " + entityName
					+ "=null;\n");
			this.sBuffer.append("\t\t\t\t" + entityName + "=populate(rs);\n");
			this.sBuffer.append("\t\t\t\tlist.add(" + entityName + ");\n");
			this.sBuffer.append("\t\t\t}\n");
			this.sBuffer.append("\t\t\treturn list;\n");
			this.sBuffer.append("\t\t} finally {\n");
			this.sBuffer.append("\t\t\tDbHelper.close(rs);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(stmt);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(pstm);\n");
			this.sBuffer.append("\t\t}\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	private void gerDaoFindCountBySql(String tableName) {
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			this.sBuffer
					.append("\tpublic int findCountBySql(String sqlString)throws SQLException {\n");
			this.sBuffer.append("\t\ttry{\n");

			this.sBuffer.append("\t\t\tstmt = conn.createStatement();\n");
			this.sBuffer.append("\t\t\trs = stmt.executeQuery(sqlString);\n");
			this.sBuffer.append("\t\t\trs.next();\n");
			this.sBuffer.append("\t\t\treturn rs.getInt(1);\n");
			this.sBuffer.append("\t\t} finally {\n");
			this.sBuffer.append("\t\t\tDbHelper.close(rs);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(stmt);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(pstm);\n");
			this.sBuffer.append("\t\t}\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	private void gerDaoFindPagerBySql(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase()
				+ entityClassName.substring(1);

		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			this.sBuffer
					.append("\tpublic Pager findPagerBySql(String sqlString ,String sqlCountString, int pageNo, int pageSize)throws SQLException {\n");
			this.sBuffer.append("\t\ttry{\n");

			this.sBuffer.append("\t\t\tstmt = conn.createStatement();\n");
			this.sBuffer
					.append("\t\t\trs = stmt.executeQuery(sqlCountString);\n");
			this.sBuffer.append("\t\t\tInteger count=null;\n");
			this.sBuffer.append("\t\t\twhile(rs.next()){\n");
			this.sBuffer.append("\t\t\t\tcount=rs.getInt(1);\n");
			this.sBuffer.append("\t\t\t}\n");
			this.sBuffer
					.append("\t\t\tsqlString+=\" limit \"+(pageNo-1)*pageSize+\",\"+pageSize;\n");
			this.sBuffer.append("\t\t\trs = stmt.executeQuery(sqlString);\n");
			this.sBuffer.append("\t\t\tList<" + entityClassName
					+ ">list =new ArrayList<" + entityClassName + ">();\n");
			this.sBuffer.append("\t\t\twhile(rs.next()){\n");
			this.sBuffer.append("\t\t\t\t" + entityClassName + " " + entityName
					+ "=null;\n");
			this.sBuffer.append("\t\t\t\t" + entityName + "=populate(rs);\n");
			this.sBuffer.append("\t\t\t\tlist.add(" + entityName + ");\n");
			this.sBuffer.append("\t\t\t}\n");
			this.sBuffer
					.append("\t\t\treturn new Pager(pageSize,pageNo,count,list);\n");
			this.sBuffer.append("\t\t} finally {\n");
			this.sBuffer.append("\t\t\tDbHelper.close(rs);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(stmt);\n");
			this.sBuffer.append("\t\t\tDbHelper.close(pstm);\n");
			this.sBuffer.append("\t\t}\n");
			this.sBuffer.append("\t}\n");
			this.sBuffer.append("\n");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	public static void main(String[] args) throws Exception {
		EntityCreateOracle entityCreate = new EntityCreateOracle();

		InputStream inputStream = entityCreate.getClass().getClassLoader()
				.getResourceAsStream("db.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}

		entityCreate.projectPackage = properties.getProperty("project_package");
		entityCreate.createFilePath = properties
				.getProperty("create_file_path");
		entityCreate.creatEntityFile();
	}
	private void gerDaoFoot() {
		this.sBuffer.append("}\n");
	}
}
