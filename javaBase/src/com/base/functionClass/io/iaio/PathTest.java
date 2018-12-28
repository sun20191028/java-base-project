package com.base.functionClass.io.iaio;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
	
	public static void main(String[] args) {
		Path path = Paths.get(".");
		System.out.println("path包含的路径数量：" + path.getNameCount());
		System.out.println("path的根路径：" + path.getRoot());
		
		Path absolutePath = path.toAbsolutePath();
		System.out.println(absolutePath);
		
		System.out.println("absolutePath的根路径：" + absolutePath.getRoot());
		System.out.println("absolutePath包含的路径数量：" + absolutePath.getNameCount());
		
		Path path2 = Paths.get("g:", "publish","codes");
		System.out.println(path2);
		
	}
	
	
	
}
