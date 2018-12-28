package com.base.functionClass.collectionT.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * entrySet 更好。
 * @author liangpro
 *
 */
public class ForeachMap {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "zhangsan");
		map.put("age", "25");
		map.put("love", "羽毛球");
		
		for (Entry<String, String> string : map.entrySet()) {
			System.out.println(string.getKey() + "," + string.getValue());
			
			
		}
		
		
	}
	

}
