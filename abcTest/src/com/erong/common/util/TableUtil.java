package com.erong.common.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

public class TableUtil {
	/**
	 * map中储存的字段类型是java类型
	 * 单线程 单次进入 一个类只有一个对象被装载。
	 * 多个对象不能同时装载，需要清空 nameTypeValue。
	 */
	private static ThreadLocal<Map<String, Object[]>>  nameTypeValue=new ThreadLocal();
	/**
	 * 利用map去装对应对象的属性名称、类型、和值  。nameTypeValue
	 * @param ob
	 * @throws Exception 
	 */
	public static void loadClassField(Object ob) throws Exception{
		Map<String,Object[]> mapClassFieldName=new HashMap<>();
		Field[] classFieldName=ob.getClass().getDeclaredFields();
		for ( int i = 0 ; i < classFieldName.length ; i++){
			Field f = classFieldName[i];
			f.setAccessible( true ); // 设置些属性是可以访问的
			Object val = f.get(ob); // 得到此属性的值
			if(val!=null&&val!=""){  //此处不需要 (int)val!=0 val都是包装类。
				String classFieldNameType=f.getType().toString();
				Object[] obj={classFieldNameType,val};
				mapClassFieldName.put(f.getName(), obj);
				nameTypeValue.set(mapClassFieldName);
			}
		}
		/*
		 * 判断顺序别反了，null是初始化的时候指向null，并未分配空间，isEmpty是分配了空间，但空间里面没有存值。
		 * 当线程首次进来的时候若对象中没有值，则map中未null。之后clear后，若对象属性没有值，则map是isEmpty()
		 */
		if (nameTypeValue.get()==null||nameTypeValue.get().isEmpty()) {
			throw (new Exception("对象为空，无法插入数据库"));
		}
	}
	/**
	 * 清空map中的所有键值对。
	 */
	public static void clearThreadLocalMap(){
		if(nameTypeValue.get()!=null||!(nameTypeValue.get().isEmpty())){
			nameTypeValue.get().clear();
		}
	}
	
	/**
	 * 从对象中获取 已经 赋值的map<String,String>型的属性 和类型
	 * @param ob
	 * @return
	 * @throws Exception 
	 */
	public static Map<String, String> getMapClassField(Object ob) throws Exception{
		Map<String, String> mapClassFieldName=new HashMap<>();
		if(nameTypeValue.get()==null||nameTypeValue.get().isEmpty()){
			loadClassField(ob);
		}
		Set<String> classFieldNameSet=nameTypeValue.get().keySet();
		for (String classFieldName : classFieldNameSet) {
			Object[] obj=nameTypeValue.get().get(classFieldName);
			String type=(String) obj[0];
			mapClassFieldName.put(classFieldName, type);
		}
		return mapClassFieldName;
	}
	/**
	 * 从对象中获取set 型的属性名称集合
	 * @throws Exception 
	 */
	public static Set<String> getSetClassFieldName(Object obj) throws Exception{
		if(nameTypeValue.get()==null||nameTypeValue.get().isEmpty()){
			loadClassField(obj);
		}
		Set<String> setClassFieldName= nameTypeValue.get().keySet();
		return setClassFieldName;
	}
	/**
	 * 根据属性名，得到属性类型
	 * @param ob
	 * @return
	 * @throws Exception 
	 */
	public static String getFieldNameType(String classFieldName,Object obj) throws Exception{
		if(nameTypeValue.get()==null||nameTypeValue.get().isEmpty()){
			loadClassField(obj);
		}
		Object[] objectArray=nameTypeValue.get().get(classFieldName);
		String classFieldNameType=(String) objectArray[0];
		return classFieldNameType;
	}
	
	/**
	 * 根据属性名，获取属性值
	 * @throws Exception 
	 */
	public static Object getFieldValue(String classFieldName,Object obj) throws Exception{
		if(nameTypeValue.get()==null||nameTypeValue.get().isEmpty()){
			loadClassField(obj);
		}
		return nameTypeValue.get().get(classFieldName)[1];
	}

/**--------------我是华丽的分割线-----接下来主要是字符串的处理----------------------**/	
	/**
	 * 通过对象，获取其类名
	 * @param ob
	 * @return
	 */
	public static String getObjectName(Object ob){
		Class obClass=ob.getClass();
		String obName=obClass.getSimpleName();
		return obName;
	}
	/**
	 * 通过类名，获取其对应的表格名称。
	 * @param str
	 * @return
	 */
	public static String getTableName(String str){
		StringBuffer stb=new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c=str.charAt(i);
			if(Character.isUpperCase(c)){
				stb.append("_"+c);
			}else{
				stb.append(c);
			}
		}
		String st=stb.toString();
		st=st.substring(1);
		return st.toLowerCase();
	}
	
	/**
	 * 类属性名的set集合 转换成 表字段名的String。
	 * @param setClassFieldName
	 * @return
	 */
	public static String setClassToTableFieldNameStr(Set<String> setClassFieldName){
		String tableFieldNameStrMult ="";
		Iterator<String> itt=setClassFieldName.iterator();
		while(itt.hasNext()){
			String classFieldName=itt.next();
			tableFieldNameStrMult=tableFieldNameStrMult.concat(classToTableFieldName(classFieldName));
		}
		String str=tableFieldNameStrMult.substring(0,tableFieldNameStrMult.length()-1);
		return str.toUpperCase();
	}
	
	/**
	 * 类属性名 转换成 表字段名。
	 * @param str
	 * @return
	 */
	public static String classToTableFieldName(String str){
		StringBuffer stb=new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if(Character.isUpperCase(str.charAt(i))){
				stb.append("_"+(str.charAt(i)));
			}else{
				stb.append(str.charAt(i));
			}
		}
		return stb.toString().concat(",");
	}
	
	/**
	 * 获得形如 指定个数的(?,?,?,?)字符串
	 * @param i
	 * @return
	 */
	public static String getTableSymbol(int i){
		StringBuffer stb=new StringBuffer();
		for (int j = 0; j < i; j++) {
			stb.append("?,");
		}
		String st=stb.toString();
		st="("+st.substring(0,st.length()-1)+")";
		return st;
	}
	/**
	 * 通过类属性名获得对应的get方法
	 * @param fieldName
	 * @return
	 */
	public static String getGetMethodName(String fieldName){
		String methodName="get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
		return methodName;
	}
	/**
	 * 表字段名转类 属性名的方法
	 */
	public static String fieldNameTableToClass(String tableFieldName){
		String classFieldName = "";
		boolean isUnderLine = false;
		for (int i = 0; i < tableFieldName.length(); ++i) {
			if (tableFieldName.charAt(i) == '_') {
				isUnderLine = true;
			} else if (isUnderLine) {
				classFieldName = classFieldName+ String.valueOf(tableFieldName.charAt(i)).toUpperCase();
				isUnderLine = false;
			} else {
				classFieldName = classFieldName + String.valueOf(tableFieldName.charAt(i)).toLowerCase();
			}
		}

		return classFieldName;
		
	}
}
