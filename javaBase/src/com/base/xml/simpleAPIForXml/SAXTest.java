package com.base.xml.simpleAPIForXml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SAXTest {
	
	   /**
     * @param args
     */
    public static void main(String[] args) {
        //创建一个SAXParserFactory的对象
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //通过factory获取SAXParser实例
        try {
            SAXParser parser = factory.newSAXParser();
            //创建对象SAXParserHandler的实例
            SAXParserHandler handler = new SAXParserHandler();
            parser.parse("src/com/base/xml/simpleAPIForXml/book.xml", handler);
            System.out.println("~！~！~！共有" + handler.getBookList().size() + "本书");
            for (Book book : handler.getBookList()) {
                System.out.println(book.getId());
                System.out.println(book.getName());
                System.out.println(book.getAuthor());
                System.out.println(book.getYear());
                System.out.println(book.getPrice());
                System.out.println(book.getLanguage());  
                System.out.println("----finish----");
            }
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
