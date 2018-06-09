package com.util.getProperties.gold;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class CommUtil {
	private static HashMap<String, Object[]> propsMap = new HashMap();
	/**
	 * by gold
	 * 其主旨思想 还是Properties类的对象有一个load（FileInputStream fis）的方法，可以从FileInputStream加载属性文件。
	 * 只通过键获取 值有一个问题，就是每一次访问时，都会加载一次这个文件，所以通过一个HashMap类属性储存其加载的值，避免重复加载。
	 * 但是又有一个问题，就是如果后期只是更改了属性文件，并未停止服务器，已加载的属性文件得不得更新。所以干脆给他储存一个修改时间。若修改时间不相等，则重新加载。
	 */
	public static String getConfig(String keyName){
		return getConfig("gess.properties",keyName);
	}
	public static String getConfig(String fileName,String keyName){
		Properties props=null;
		boolean bIsNeedLoadCfg=false;
		File cfgFile=new File(fileName);
		//文件不存在的时候返回""
		if(!(cfgFile.exists()))
			return "";
		Object[] arrs=propsMap.get(fileName);
		//如果这个map中没有装载这个文件，则需要加载。
		if(arrs==null){
			bIsNeedLoadCfg=true;
		}else{
			//map中加载的值是一个数组，一号位是文件修改时间，2号位才是属性文件。
			Long lastModify=(Long) arrs[0];
			if(lastModify.longValue()!=cfgFile.lastModified()){    //File类的lastModified方法返回的是文件对象最后一次修改的时间
				/**
				 * 加上这个更好,如果不移除，在不断修改属性文件后，便会导致map中的值越来越大。
				 * 会自动替换啊！！
				 */
				propsMap.remove(keyName);
				bIsNeedLoadCfg=true;
			}else{
				props=(Properties) arrs[1];
			}
		}
		if(bIsNeedLoadCfg){
			FileInputStream fis=null;
			try {
				fis=new FileInputStream(cfgFile);
				props=new Properties();
				props.load(fis);
				propsMap.put(fileName, new Object[]{ Long.valueOf(cfgFile.lastModified()),props });
			} catch (Exception lastModified) {
				// TODO Auto-generated catch block
				return "";
			}finally{
				try {
					if(fis!=null)
						fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return props.getProperty(keyName, "");
	}
	/**
	 * 从属性文件中获取String型值
	 */
	public static String getConfigByString(String keyName, String defaultValue){
		String value=getConfig(keyName);
		if((value!=null)&&(value.length()>0)){
			return value.trim();
		}
		return defaultValue;
	}
	public static String getConfigByString(String keyName){
		return getConfig(keyName);
	}
	/**
	 * 从属性文件中获取int型值
	 * @param keyName
	 * @param defaultValue
	 * @return
	 */
	public static int getConfigByInt(String keyName, int defaultValue){
		String value=getConfig(keyName);
		if((value!=null)&&(value.length()>0)){
			return Integer.parseInt(value.trim());
		}
		return defaultValue;
	}
	public static int getConfigByInt(String keyName){
		return Integer.parseInt(getConfig(keyName));
	}
	/**
	 * 从属性文件中获取boolean型值
	 * @param keyName
	 * @param defaultValue
	 * @return
	 */
	public static boolean getConfigByBoolean(String keyName,
			boolean defaultValue) {
		String value = getConfig(keyName);
		if ((value != null) && (value.length() > 0)) {
			return Boolean.parseBoolean(value);
		}
		return defaultValue;
	}
	public static boolean getConfigByBoolean(String keyName) {
		return Boolean.parseBoolean(getConfig(keyName).trim());
	}
	/**
	 * 从属性文件中获取long型值
	 * @param keyName
	 * @param defaultValue
	 * @return
	 */
	public static long getConfigByLong(String keyName, long defaultValue) {
		String value = getConfig(keyName);
		if ((value != null) && (value.length() > 0)) {
			return Long.parseLong(value.trim());
		}
		return defaultValue;
	}
	public static long getConfigByLong(String keyName) {
		return Long.parseLong(getConfig(keyName));
	}
	/**
	 * 从属性文件中获取float的值
	 * @param keyName
	 * @param defaultValue
	 * @return
	 */
	public static float getConfigByFloat(String keyName, float defaultValue) {
		String value = getConfig(keyName);
		if ((value != null) && (value.length() > 0)) {
			return Float.parseFloat(value.trim());
		}
		return defaultValue;
	}
	public static float getConfigByFloat(String keyName) {
		return Float.parseFloat(getConfig(keyName));
	}
}
