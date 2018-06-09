package com.zzl.eaencode;

import java.io.UnsupportedEncodingException;

public class EncodeTest {
	public static void main(String[] args) {
		
	}
    /** 
     * 字符串转化为UTF-8 
     * @param str 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    public static String toUTF8(String str) throws UnsupportedEncodingException{  
        String result = str;  
        
        if (str != null) {  
            // 用默认字符编码解码字符串。  
            byte[] bs = str.getBytes();  
            // 用新的字符编码生成字符串  
            result=new String(bs, "utf-8");  
        }  
        return result;  
    }  
      
    /** 
     * 字节数组转化为UTF-8 
     * @param bty 
     * @return 
     * @throws UnsupportedEncodingException 
     */  
    public static String toUTF8(byte[] bty) throws UnsupportedEncodingException{  
         
        if (bty.length > 0) {  
            return new String(bty, "UTF-8");  
        }  
    
        return new String(bty);  
    }
}
