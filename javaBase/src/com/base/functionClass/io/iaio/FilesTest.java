package com.base.functionClass.io.iaio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 强大的Files功能
 * 补全了File 不能访问文件内容的缺陷
 * @author liangpro
 *
 */
public class FilesTest {

	public static void main(String[] args) throws IOException {
		OutputStream out = new FileOutputStream("src/com/zzl/iaio/destinationFile.txt");
		Path path = Paths.get("src/com/zzl/iaio/charByte.txt");
		Files.copy(path, out);//拷贝文件
		
		System.out.println(path);
		
		List<String> lines = Files.readAllLines(path, Charset.forName("utf-8"));//读取文件
		System.out.println(lines);
		
		System.out.println("文件大小" + Files.size(path));//文件大小
		
		List<String> poem = new ArrayList<String>();
		poem.add("愿你出走半生。");
		poem.add("归来依然是那个少年。");//会自动换行
		Files.write(path, poem, Charset.forName("utf-8"),StandardOpenOption.APPEND);//将数据添加在文件的末尾，而不是覆盖
		
		
		
		File file = new File("src/gess.properties");
		
		System.out.println(file.exists());
		
		File file2 = new File("../a.properties");
		
		System.out.println(file2.exists());
		
		ResourceBundle res =  ResourceBundle.getBundle("gess");
		
		System.out.println(res.getString("hello"));
		
		
		
	}
	
	
}
