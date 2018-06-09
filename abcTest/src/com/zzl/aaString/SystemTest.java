package com.zzl.aaString;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SystemTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
//		Map<String, String> env=System.getenv();
////		for (String name : env.keySet()) {
////			System.out.println(name+"-->"+env.get(name));
////		}
//		Iterator<String> ite=env.keySet().iterator();
//		while (ite.hasNext()) {
//			System.out.println(ite.next()+"-->"+env.get(ite.next()));
//		}
		
		Properties props=System.getProperties();
		//后面那个参数是描述语句 comment String
		props.store(new FileOutputStream("props.txt"), "System Properties");
		System.out.println(System.getProperty("os.name"));
		
		
	}
}
