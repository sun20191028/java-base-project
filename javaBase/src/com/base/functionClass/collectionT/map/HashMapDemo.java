package com.base.functionClass.collectionT.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
//		map.put(null, "zhangsan");
//		map.keySet().iterator();
//		System.out.println(map.get(null));
//		map.isEmpty();
//	    System.out.println(hash(new Demo("aa")));
//	    
//	    
//	    System.out.println(Integer.highestOneBit((2 - 1) << 1) );
		map.put(null, "zhangsan");
	
		System.out.println(map);
	}
	
	static int hash(Object k) {
		int hashSeed = 0 ;
        int h = hashSeed ;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	
}
class Demo{
	String name;
	public Demo(String value){
		this.name = value;
	}
}

class HashMapEntry{
	
	public int hashCode(){
		return 1;
	}
	
	 public boolean equals(Object obj) {
	    return true;
	 }

	
}