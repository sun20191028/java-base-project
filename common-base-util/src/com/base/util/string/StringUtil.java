package com.base.util.string;

import com.base.common.Constant;

/**
 * 
 * @author liang
 *
 */
public class StringUtil {
	
	
	/**
	 * null/""/"  "/ ¶¼ÊÇ¿Õ
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str){
		if(str!=null){
			if( ! "".equals(str.trim())){
				return false;
			}
		}
		return true;
	}
	
	public static byte[] StringToBytes(String str) {
		try {
			if (isEmptyString(str)) {
				return new byte[0];
			}
			return str.getBytes(Constant.CHARSET);
		} catch (Exception e) {
//			CommUtil.WriteLog(6, e);
		}
		return null;
	}
	

	public static String getBlank(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; ++i)
			sb.append(' ');
		return sb.toString();
	}
}
