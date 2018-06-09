package com.base.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class GetLocalHost {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String localUri = "";
		while(true){
			localUri = br.readLine();
			String before = localUri.substring(0,localUri.indexOf("\\WebContent"));
			int index = before.lastIndexOf("\\");
			localUri = localUri.substring(index,localUri.length());
			String url = localUri.replaceAll("\\\\", "/");
			url = url.replace("/WebContent", "");
			url = "http://localhost:8080"+url;
			System.out.println(url);
		}
		
//		D:\studyspace\servletSpace\httpHead\WebContent\servletAndJsp\jspfold\11\Announce.jsp
//		http://localhost:8080/httpHead/servletAndJsp/jspfold/11/Announce.jsp
	}
}
