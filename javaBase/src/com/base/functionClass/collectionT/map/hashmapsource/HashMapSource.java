package com.base.functionClass.collectionT.map.hashmapsource;

import java.util.HashMap;

public class HashMapSource {
	
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>(4);
		map.put("aa", "2");
		map.put("cc", "1");
		map.put("bb", "1");
//		map.put("dd", "1");
//		map.put("ee", "1");
//		map.put("ff", "1");
//		map.put("gg", "1");
//		map.put("hh", "1");
//		map.put("ii", "1");
		System.out.println(map.get("aa"));
		
//		System.out.println(indexFor(hash("aa"), 4));
//		System.out.println(indexFor(hash("cc"), 4));
		
		System.out.println(map.put("aa", "3"));
		System.out.println(map.get("aa"));
		
	}
	
    public static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
    	print(length-1);
        return h & (length-1);
    }
	public static int hash(Object k) {
		int hashSeed = 0;
        int h = hashSeed;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();
        print(k.hashCode());
        print(h);
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        print(h);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	
	public static void print(int t){
		System.out.println(Integer.toBinaryString(t));
	}
	
}
