package com.base.basic.annotation.demo3.engine;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.base.basic.annotation.demo3.annotation.Autowired;
import com.base.basic.annotation.demo3.annotation.Component;
import com.base.basic.annotation.demo3.annotation.ComponentScan;

public class AnnotationConfigApplicationContext {
	Class clazz;
	List<File> javaFiles = new ArrayList<File>();
	Map<String, Object> beans = new ConcurrentHashMap<String, Object>();
	
	private volatile boolean isInit;
	
	public AnnotationConfigApplicationContext(Class clazz){
		this.clazz = clazz;
	}
	
	public void init() throws Exception{
		ComponentScan cs = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
		String[] strs = cs.value();
		// 扫描文件
		for (int i = 0; i < strs.length; i++) {
			String cPath = strs[i];
//			File file = new File("D:/project/git/java-base-project/javaBase/src/com/base/basic/annotation/demo3");
			File file = new File("./" + cPath);
			if(!file.exists()){
				throw new Exception("ComponentScan path is not exist");
			}
			loadPath(file);
		}
		// 生成bean
		for (File file : javaFiles) {
//			URL url = new URL(file.getAbsolutePath());
//			URL[] urls = {url};
//			URLClassLoader loader = new URLClassLoader(urls);
//			Object ob = loader.loadClass(file.getAbsolutePath());
//			
//			Class cla = ob.getClass();
			String fileN = file.getAbsolutePath();
			fileN = fileN.substring(fileN.indexOf("src")+4, fileN.lastIndexOf("."));
			fileN = fileN.replace("/", ".").replace("\\", ".");
			Class cla = Class.forName(fileN);
			
			Component comp = (Component) cla.getAnnotation(Component.class);
			if(comp!=null){
				beans.put(file.getName().substring(0,file.getName().indexOf(".")), cla.newInstance());
			}
		}
		// 装配bean
		for (Entry<String, Object> entry : beans.entrySet()) {// 暂时只处理属性装配
			Class ca = entry.getValue().getClass();
			Field[] fields = ca.getDeclaredFields();
			for (Field field : fields) {
				Autowired auto = field.getAnnotation(Autowired.class);
				if(null != auto){
					String className = field.getType().getName();
					Object ob = beans.get(className.substring(className.lastIndexOf(".")+1));
					if(ob == null){
						Class childClass = Class.forName(className);
						className = childClass.getSuperclass().getName();
						ob = beans.get(className);
					}
					if(ob !=null){
						String  fieldName = field.getName();
						String methodStr = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
						Method method = ca.getDeclaredMethod(methodStr, ob.getClass());
						method.invoke(entry.getValue(), ob);
					}
				}
			}
		}
		isInit = true;
	}
	
	public <T> T getBean(Class<T> requiredType){//没有加入并发
		if(!isInit){
			try {
				init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return (T) beans.get(requiredType.getName().substring(requiredType.getName().lastIndexOf(".")+1));
		
	}
	
	private void loadPath(File file){
		
		File[] files = file.listFiles();
		if(files.length<=0)
			return;
		for (int i = 0; i < files.length; i++) {
			File innerFile = files[i];
			if(innerFile.isDirectory()){
				loadPath(innerFile);
			}else if(innerFile.getName().endsWith(".java")){
				javaFiles.add(innerFile);
			}
		}
	}
	
}
