package com.util.getProperties.erong;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BaseConfigUtil {
//	private static final Logger logger = Logger.getLogger(BaseConfigUtil.class);
	private static String SERVICE_CODE_FILE = "erong.properties";
	private static String DB_CODE_FILE="db.properties";
	private static Properties pro = null;
	public static Logger logger = Logger.getLogger(BaseConfigUtil.class);
	
	/**
	 * 这个是根据path获取 properties文件对象。
	 * @param c
	 * @param configpath
	 * @return
	 */
	public static Properties getProperties(){
		return getProperties(BaseConfigUtil.class,DB_CODE_FILE);
	}
	public static Properties getProperties(Class c, String configpath) {
		InputStream inputStream = c.getClassLoader().getResourceAsStream(configpath);
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e) {
			logger.error(e);
		}
		return p;
	}
	/**
	 * 这个根本不行，整个进程中只能用一次，因为是类变量，整个线程中只有一个Properties，如果数据库用了此Properties，其他配置就不能用了。
	 * 但是上面的返回属性文件的设计就不一样了，它并未使用类变量。
	 * @param key
	 * @return
	 */
//	public static String getDbId(String key) {
//		
//		return getServiceId(DB_CODE_FILE,key);
//	}
	/**
	 *	这个是通过键得到值，他是通过类属性，来避免多次加载，通过synchronized同步锁，避免加载多个
	 * @param key
	 * @return
	 */
	public static String getServiceId(String key) {
		return getServiceId(SERVICE_CODE_FILE,key);
	}
	public static String getServiceId(String configFile, String key) {
		String value = null;
		InputStream io = null;
		try {
			synchronized (pro) {
				if (pro == null) {
					pro = new Properties();
//					synchronized (pro) { //其实个人认为放在这里，根本没用，关键是不能让多线程中的 pro通过判断，如果通过了判断，那么便有多个pro堵在此处，便会加载多次属性文件。
					io = BaseConfigUtil.class.getClassLoader().getResourceAsStream(configFile);
					pro.load(io);
					io.close();
					io = null;
				}
			}
			value = pro.getProperty(key);
			logger.info("aaaaaaaaaaaaaa");
		} catch (IOException e) {
			logger.error("load properties error...", e);
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
	 * 通过log配置文件，初始化log4j  ,
	 * @param profilename
	 */
	public static void initLog(String profilename) {
		URL resource = BaseConfigUtil.class.getClassLoader().getResource(profilename);
		if (resource != null) {
			PropertyConfigurator.configure(resource);
		}
		logger.info("-================-" + profilename);
	}

	public static void main(String[] args) {
//		initLog("log4j.properties");//既然注释了依然可以记录日志，那这个初始化有什么意思。
		
		System.out.println(getServiceId("DB_USER"));
	}
}
