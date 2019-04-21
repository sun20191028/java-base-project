package com.base.basic.collection.extention.concurrentLinkedHashMap;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * 还是不行，
 * 虽然可以支持并发，迭代器和 put、remove可以并发进行
 * 但是 remove的东西，依然在迭代器上面。先迭代，再remove，迭代器依然会输出
 * 
 * @author liangpro
 *
 */
public class ConcurrentLinkedHashMapTest {
	static ConcurrentLinkedHashMap<String, String> map = new ConcurrentLinkedHashMap.Builder()
	.maximumWeightedCapacity(10000).weigher(Weighers.singleton()).build();
	static ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<String, String>();	
			
	public static void main(String[] args) {
		map.put("aa", "aa");
		map.put("bb", "bb");
		map.put("cc", "cc");
		map.put("dd", "dd");
		map.put("ee", "ee");
		
		hashMap.put("aa", "aa");
		hashMap.put("bb", "bb");
		hashMap.put("cc", "cc");
		hashMap.put("dd", "dd");
		hashMap.put("ee", "ee");
		
		System.out.println(Arrays.toString(map.keySet().toArray()));// 
		System.out.println(map.ascendingMapWithLimit(1));
		System.out.println(map.descendingMapWithLimit(1));
		System.out.println(Arrays.toString(hashMap.keySet().toArray()));
		
//		new Thread(){
//			public void run(){
//				while (true) {
//					for (Entry<String, String> entry : map.entrySet()) {
//						System.out.println(entry.getKey() + "," + entry.getValue());
//						try {
//							sleep(1000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}.start();
//		
//		new Thread(){
//			public void run(){
//				for (int i = 0; i < 10000; i++) {
//					map.put(i+"", "test");
//					System.out.println("put : " + i);
//					try {
//						sleep(300);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}.start();
//		
//		new Thread(){
//			public void run(){
//				for (int i = 0; i < 10000; i++) {
//					map.remove(i+"");
//					System.out.println("remove : " + i);
//					try {
//						sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}.start();
		
	}
	
	
	
}
