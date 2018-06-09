package com.jar.dataTableToEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

public class EntityCreateMysql {
	private static final Logger logger = Logger.getLogger(EntityCreateMysql.class);
	private StringBuffer sBuffer = new StringBuffer();
	private StringBuffer sHeadBuffer = new StringBuffer();
	private boolean hasDateType;
	private boolean hasBigDecimal;
	private boolean hasDatetimeType;
	public String createFilePath = "";

	public String projectPackage = "";

	public String creatEntityFile() {
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
			sql = "show tables";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);
				logger.info(tableName);
				if (tableName.toLowerCase().equals("con_test"))
					continue;
				this.sBuffer = new StringBuffer();
				this.sHeadBuffer = new StringBuffer();
				this.hasDateType = false;
				this.hasDatetimeType = false;
				this.hasBigDecimal = false;
				String tableClassName = getTableClassName(tableName);
				gerEntity(tableName);
				gerEntityHead(tableClassName);

				File file = new File(this.createFilePath + "entity\\"
						+ tableClassName + ".java");
				if (!(file.exists()))
					file.createNewFile();
				else {
					file.delete();
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

	private void gerEntity(String tableName) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			sql = "select * from " + tableName + " LIMIT 0,1";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			this.sBuffer.append("\t//Fields\n");
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				logger.info(meta.getColumnName(i + 1) + ":"
						+ meta.getColumnType(i + 1) + " "
						+ meta.getColumnTypeName(i + 1) + " "
						+ meta.getPrecision(i + 1) + " " + meta.getScale(i + 1)
						+ " " + meta.getColumnClassName(i + 1));
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
				this.sBuffer.append("\tprivate " + fieldType + " "
						+ getFieldClassName(fieldName) + ";\n");
			}
			this.sBuffer.append("\n");
			this.sBuffer.append("\t//Property accessors\n");

			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldClassName = getFieldClassName(fieldName);
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
				this.sBuffer.append("\tpublic " + fieldType + " get"
						+ fieldClassName.substring(0, 1).toUpperCase()
						+ fieldClassName.substring(1) + "() {\n");

				String set = "\t\treturn this." + fieldClassName + "; \n";
				Object nvl = getFieldTypeNvlValue(
						meta.getColumnTypeName(i + 1), meta.getScale(i + 1));
				if (nvl != null)
					set = "\t\treturn this." + fieldClassName + " == null ? "
							+ nvl + " : this." + fieldClassName + "; \n";
				this.sBuffer.append(set);
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
		} finally {
			DbHelper.close(rs);
			DbHelper.close(stmt);
			DbHelper.close(pstm);
		}
	}

	private Object getFieldTypeNvlValue(String columnType, int scale) {
		if ((columnType.equals("CHAR")) || (columnType.equals("VARCHAR"))) {
			return "\"\"";
		}
		if (columnType.equals("BIGINT")) {
			return Long.valueOf(0L);
		}
		if (columnType.equals("DATE")) {
			return null;
		}
		if (columnType.equals("DATETIME")) {
			return null;
		}
		if (columnType.equals("INTEGER")) {
			return Integer.valueOf(0);
		}

		if (columnType.equals("TINYINT")) {
			return Integer.valueOf(0);
		}

		if (columnType.equals("DOUBLE")) {
			return Double.valueOf(0.0D);
		}

		if (columnType.equals("BLOB")) {
			return null;
		}
		if (columnType.equals("MEDIUMBLOB")) {
			return null;
		}
		if (columnType.equals("LONGBLOB"))
			return null;
		if (columnType.equals("DECIMAL")) {
			if (scale == 0) {
				return Integer.valueOf(0);
			}

			this.hasBigDecimal = true;
			return null;
		}

		logger.info("getFieldTypeNvlValue未知的columnType类型[" + columnType + "]");

		return null;
	}

	private String getFieldType(String columnType, int scale) {
		if ((columnType.equals("CHAR")) || (columnType.equals("VARCHAR"))) {
			return "String";
		}
		if (columnType.equals("BIGINT")) {
			this.hasDateType = true;
			return "Long";
		}
		if (columnType.equals("DATE")) {
			this.hasDateType = true;
			return "Date";
		}
		if (columnType.equals("DATETIME")) {
			this.hasDatetimeType = true;
			return "Timestamp";
		}
		if (columnType.equals("TIMESTAMP")) {
			this.hasDatetimeType = true;
			return "Timestamp";
		}
		if (columnType.equals("DECIMAL")) {
			if (scale == 0) {
				return "Integer";
			}

			this.hasBigDecimal = true;
			return "BigDecimal";
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
		if (columnType.equals("MEDIUMBLOB"))
			return "byte[]";
		if (columnType.equals("LONGBLOB")) {
			return "byte[]";
		}

		logger.info("getFieldType未知的columnType类型[" + columnType + "]");

		return "";
	}

	private String getTableClassName(String tableName) {
		String tableClassName = "";
		boolean isUnderLine = false;
		for (int i = 0; i < tableName.length(); ++i) {
			if (tableName.charAt(i) == '_') {
				isUnderLine = true;
			} else if (isUnderLine) {
				tableClassName = tableClassName
						+ String.valueOf(tableName.charAt(i)).toUpperCase();
				isUnderLine = false;
			} else {
				tableClassName = tableClassName + tableName.charAt(i);
			}
		}
		tableClassName = tableClassName.substring(0, 1).toUpperCase()
				+ tableClassName.substring(1);
		return tableClassName;
	}

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

	private void gerEntityHead(String tableClassName) {
		this.sHeadBuffer.append("/*\n");
		this.sHeadBuffer
				.append(" * Copyright erong software, Inc. All rights reserved.\n");
		this.sHeadBuffer
				.append(" * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer
				.append("package " + this.projectPackage + ".entity;\n");
		this.sHeadBuffer.append("\n");
		if (this.hasDateType)
			this.sHeadBuffer.append("import java.sql.Date;\n");
		if (this.hasDatetimeType)
			this.sHeadBuffer.append("import java.sql.Timestamp;\n");
		if (this.hasBigDecimal)
			this.sHeadBuffer.append("import java.math.BigDecimal;\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("/**\n");
		this.sHeadBuffer.append(" * " + tableClassName
				+ " entity. @author joshuaxu Persistence Tools\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");

		this.sHeadBuffer.append("public class " + tableClassName + " {\n");
	}

	public String creatDao() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = ConnectionManager.getInstance().getConnection();
			sql = "show tables";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			while (rs.next()) {
				String tableName = rs.getString(1);
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

				File file = new File(this.createFilePath + "dao\\"
						+ tableClassName + "Dao.java");
				if (!(file.exists()))
					file.createNewFile();
				else {
					file.delete();
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

	private void gerDaoHead(String tableClassName) {
		this.sHeadBuffer.append("/*\n");
		this.sHeadBuffer
				.append(" * Copyright erong software, Inc. All rights reserved.\n");
		this.sHeadBuffer
				.append(" * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("package " + this.projectPackage + ".dao;\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("import java.util.List;\n");
		this.sHeadBuffer.append("import java.util.ArrayList;\n");
		this.sHeadBuffer.append("import java.sql.Connection;\n");
		this.sHeadBuffer.append("import java.sql.PreparedStatement;\n");
		this.sHeadBuffer.append("import java.sql.ResultSet;\n");
		this.sHeadBuffer.append("import java.sql.Statement;\n");
		this.sHeadBuffer.append("import java.sql.SQLException;\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer
				.append("import com.erong.base.db.ConnectionManager;\n");
		this.sHeadBuffer.append("import com.erong.common.base.Pager;\n");
		this.sHeadBuffer.append("import com.erong.base.db.DbHelper;\n");
		this.sHeadBuffer.append("import " + this.projectPackage + ".entity."
				+ tableClassName + ";\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("/**\n");
		this.sHeadBuffer.append(" * " + tableClassName
				+ " dao. @author joshuaxu Persistence Tools\n");
		this.sHeadBuffer.append(" */\n");
		this.sHeadBuffer.append("\n");
		this.sHeadBuffer.append("public class " + tableClassName + "Dao {\n");
		this.sHeadBuffer.append("\tprivate  Connection conn;\n");
		this.sHeadBuffer.append("\tprivate  Statement stmt;\n");
		this.sHeadBuffer.append("\tprivate  PreparedStatement pstm;\n");
		this.sHeadBuffer.append("\tprivate  ResultSet rs; \n");
		this.sHeadBuffer.append("\tprivate  String sql; \n");

		this.sHeadBuffer.append("\tpublic " + tableClassName
				+ "Dao() throws Exception {\n");
		this.sHeadBuffer
				.append("\t\tconn=ConnectionManager.getInstance().getConnection();\n");
		this.sHeadBuffer.append("\t}\n");
		this.sHeadBuffer.append("\n");
	}

	private void gerDaoFoot() {
		this.sBuffer.append("}\n");
	}

	private void gerDaoInsert(String tableName) {
		String entityClassName = getTableClassName(tableName);
		String entityName = entityClassName.substring(0, 1).toLowerCase()
				+ entityClassName.substring(1);
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			this.sBuffer.append("\tpublic int insert(" + entityClassName + " "
					+ entityName + ") throws SQLException {\n");
			this.sBuffer.append("\t\ttry{\n");
			this.sBuffer.append("\t\t\tsql=\"INSERT INTO " + tableName
					+ " (\"+\n");
			conn = ConnectionManager.getInstance().getConnection();
			sql = "select * from " + tableName + " LIMIT 0,1";
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();

			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
				if (!(fieldType.equals("byte[]"))) {
					this.sBuffer.append("\t\t\t\t\""
							+ meta.getColumnName(i + 1).toLowerCase());
					this.sBuffer.append(",\"+\n");
				}
			}
			this.sBuffer.delete(this.sBuffer.length() - 4,
					this.sBuffer.length());
			this.sBuffer.append(") \"+\n");
			this.sBuffer.append("\t\t\t\t\"VALUES (");
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
				if (!(fieldType.equals("byte[]"))) {
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
						meta.getScale(i + 1));
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
			sql = "select * from " + tableName + " LIMIT 0,1";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldClassName = getFieldClassName(fieldName);
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
				if (fieldType.equals("Integer"))
					this.sBuffer.append("\t\t" + entityName + ".set"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "(rs.getInt(\""
							+ tableName + "."
							+ meta.getColumnName(i + 1).toLowerCase()
							+ "\"));\n");
				else if (fieldType.equals("byte[]"))
					this.sBuffer.append("\t\t//" + fieldClassName
							+ " is blob\n");
				else {
					this.sBuffer.append("\t\t" + entityName + ".set"
							+ fieldClassName.substring(0, 1).toUpperCase()
							+ fieldClassName.substring(1) + "(rs.get"
							+ fieldType + "(\"" + tableName + "."
							+ meta.getColumnName(i + 1).toLowerCase()
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
			rs = dbmeta.getPrimaryKeys(null, null, tableName);

			Map keyMap = new HashMap();
			while (rs.next()) {
				keyMap.put(rs.getString(4), "");
			}

			sql = "select * from " + tableName + " LIMIT 0,1 ";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 0; i < meta.getColumnCount(); ++i) {
				String colName = meta.getColumnName(i + 1);
				String fieldName = meta.getColumnName(i + 1).toLowerCase();
				String fieldType = getFieldType(meta.getColumnTypeName(i + 1),
						meta.getScale(i + 1));
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

//	public static void main(String[] args) throws IOException {
//		EntityCreateMysqlBak entityCreate = new EntityCreateMysqlBak();
//
//		InputStream inputStream = entityCreate.getClass().getClassLoader()
//				.getResourceAsStream("db.properties");
//		Properties properties = new Properties();
//		try {
//			properties.load(inputStream);
//		} catch (IOException e) {
//			logger.error(e);
//		}
//
//		entityCreate.projectPackage = properties.getProperty("project_package");
//		entityCreate.createFilePath = properties
//				.getProperty("create_file_path");
//		entityCreate.creatEntityFile();
//	}
}
