package com.base.util.properties;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.LogManager;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.base.util.string.StringUtil;

/**
 * 
 * @author liang
 *
 */
public class PropUtil {
	private static HashMap<String, Object[]> propsMap = new HashMap();
	
	public static void main(String[] args) {
		System.out.println("##"+getConfig("test")+"##");
//		System.out.println("  ".length());
	}
	
	/**
	 * 基础方法，
	 * 规则是  :
	 * 		若 属性文件 不存在 ，则 返回   "" ;
	 * 		 若 keyName不在属性文件中，返回    "" ;
	 * @param fileName
	 * @param keyName
	 * @return            null/""/"  "/等
	 */
	public static String getConfig(String fileName, String keyName) {
		Properties props = null;
		boolean bIsNeedLoadCfg = false;
		
		//1、文件不存在，直接返回   ""
		File cfgFile = new File(fileName);
		if (!(cfgFile.exists())) {
			return "";
		}
		Object[] arrs = (Object[]) propsMap.get(fileName);
		
		//2、类变量中 没有 ，则 需要 重新加载
		if (arrs == null) {
			bIsNeedLoadCfg = true;
		} else {
			Long lastModify = (Long) arrs[0];// 最后修改时间 不等于存储时间，需要加载。
			if (lastModify.longValue() != cfgFile.lastModified())
				bIsNeedLoadCfg = true;
			else {
				props = (Properties) arrs[1];
			}
		}
		//3、 加载属性文件。
		if (bIsNeedLoadCfg) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(cfgFile);
				props = new Properties();
				props.load(fis); // 加载属性文件。
				// 并把 props put入类变量
				propsMap.put(fileName, new Object[]{Long.valueOf(cfgFile.lastModified()),props});
			} catch (Exception localException) {
				return "";
			} finally {
				try {
					if (fis != null)
						fis.close();
				} catch (Exception localException2) {
				}
			}
		}
		return props.getProperty(keyName, "");//不可能返回null 只能是"" 或"  " 等;
	}

	public static String getConfig(String keyName) {
		return getConfig("config.properties", keyName);
	}
	
	/**
	 * 
	 * @param keyName
	 * @param defaultValue
	 * @return    defaultValue/(非空，非"",非"  ")的value;
	 */
	public static String getConfigByString(String keyName, String defaultValue) {
		String value = getConfig(keyName);
		if (StringUtil.isEmptyString(value)) {
			return defaultValue;
		}
		return value;
	}
	/**
	 * @param keyName
	 * @return             null/""/"  "/等
	 */
	public static String getConfigByString(String keyName) {
		return getConfig(keyName);
	}

	public static int getConfigByInt(String keyName, int defaultValue) {
		String value = getConfig(keyName);
		if (StringUtil.isEmptyString(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value.trim());
	}
	public static int getConfigByInt(String keyName) {
		return getConfigByInt(keyName, 0);
	}

	public static boolean getConfigByBoolean(String keyName,boolean defaultValue) {
		String value = getConfig(keyName);
		if (StringUtil.isEmptyString(value)) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}
	public static boolean getConfigByBoolean(String keyName) {
		return getConfigByBoolean(keyName, false);
	}

	public static long getConfigByLong(String keyName, long defaultValue) {
		String value = getConfig(keyName);
		if (StringUtil.isEmptyString(value)) {
			return defaultValue;
		}
		return Long.parseLong(value.trim());
	}
	public static long getConfigByLong(String keyName) {
		return getConfigByLong(keyName, 0L);
	}

	public static float getConfigByFloat(String keyName, float defaultValue) {
		String value = getConfig(keyName);
		if (StringUtil.isEmptyString(value)) {
			return defaultValue;
		}
		return Float.parseFloat(value.trim());
	}
	public static float getConfigByFloat(String keyName) {
		return getConfigByFloat(keyName, 0.0f);
	}
	
	
	/**
	 * 没别的意思，就是比较这两个 获取io的方式，哪个更好
	 * @param configFile
	 * @param key
	 * @return
	 */
	public static String getConfigKeyValue(String configFile, String key) {
		String value = null;
		InputStream io = null;
		try {
			Properties properties = new Properties();
			io = PropUtil.class.getClassLoader().getResourceAsStream(configFile);
			properties.load(io);
			value = properties.getProperty(key);
			io.close();
			io = null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (io != null) {
				try {
					io.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				io = null;
			}
		}
		return value;
	}
	
	/**
	 * 我的resourceBundle
	 */
	public void getProperties1(){
		ResourceBundle pro=ResourceBundle.getBundle("com.myServer");//默认在src文件下
		System.out.println(pro.getString("DB_URL_HIS"));
		
//		ResourceBundle pro=ResourceBundle.getBundle("E:/my.properties");//暂时不能读取D盘路径
//		System.out.println(pro.getString("DB_URL_HIS"));
	}
	
	
	/**
	 * 我的resourceBundle
	 */
	public static void loadProperties(){
//		ResourceBundle pro=ResourceBundle.getBundle("mysrc");//默认在src文件下
//		System.out.println(pro.getString("DB_TEST_SQL"));
		ResourceBundle rsBundle=ResourceBundle.getBundle("com.myServer");
		System.out.println(rsBundle.getString("DB_URL_HIS"));
		
//		ResourceBundle pro=ResourceBundle.getBundle("E:/my.properties");//暂时不能读取D盘路径
//		System.out.println(pro.getString("DB_URL_HIS"));
	}
}
