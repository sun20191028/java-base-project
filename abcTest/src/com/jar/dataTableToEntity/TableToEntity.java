package com.jar.dataTableToEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TableToEntity {
	private static final Logger logger = Logger.getLogger(TableToEntity.class);
	public void generate() throws Exception {
		Properties properties = new Properties();//为什么要再写一遍加载，那前面写的那些工具不是白写了。
		InputStream inputStream = super.getClass().getClassLoader().getResourceAsStream("db.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		String driver = properties.getProperty("driver");
		if (driver.indexOf("mysql") > -1) {
			EntityCreateMysql entityCreate = new EntityCreateMysql();
			entityCreate.projectPackage = properties.getProperty("project_package");
			entityCreate.createFilePath = properties.getProperty("create_file_path");
			entityCreate.creatEntityFile();
		}
		if (driver.indexOf("oracle") > -1) {
			EntityCreateOracle entityCreate = new EntityCreateOracle();
			entityCreate.projectPackage = properties
					.getProperty("project_package");
			entityCreate.createFilePath = properties
					.getProperty("create_file_path");
			entityCreate.creatEntityFile();
		}
	}
}
