package com.base.xml.simpleAPIForXml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 利用面向接口编程， 此Handler只是把xml中的数据，一个一个解析出来，具体如何使用，由实现的Handler自己决定，
 * 此例子先展现一种原生的 分割读取功能,
 * 	并非真正的一行一行的读取，       而是一段一段的解析 ,利用正则 得到 < >  作为 startElement  然后>  < 之间的 为  characters ，</ > 为endElement
 * 
 * 查看characters方法的char数组会发现，其实已经把整个xml文档读进内存了，
 
 
 
[
, 
, <, b, o, o, k, s, t, o, r, e, >, 
, 
,  ,  ,  ,  , <, b, o, o, k,  , i, d, =, ", 1, ", >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , 啊, 啊, <, n, a, m, e, >, 冰, 与, 火, 之, 歌, <, /, n, a, m, e, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, a, u, t, h, o, r, >, 乔, 治, 马, 丁, <, /, a, u, t, h, o, r, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, y, e, a, r, >, 2, 0, 1, 4, <, /, y, e, a, r, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, p, r, i, c, e, >, 8, 9, <, /, p, r, i, c, e, >, 
, 
,  ,  ,  ,  , <, /, b, o, o, k, >, 
, 
,  ,  ,  ,  , <, b, o, o, k,  , i, d, =, ", 2, ", >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, n, a, m, e, >, 安, 徒, 生, 童, 话, <, /, n, a, m, e, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, y, e, a, r, >, 2, 0, 0, 4, <, /, y, e, a, r, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, p, r, i, c, e, >, 7, 7, <, /, p, r, i, c, e, >, 
, 
,  ,  ,  ,  ,  ,  ,  ,  , <, l, a, n, g, u, a, g, e, >, E, n, g, l, i, s, h, <, /, l, a, n, g, u, a, g, e, >, 
, 
,  ,  ,  ,  , <, /, b, o, o, k, >,  ,  ,  ,  , 
注意并未截取到数组结束符  ]
 * 
 * @author liang
 * 
 */
public class SAXParserHandler extends DefaultHandler {

	String value = null;
	Book book = null;
	private ArrayList<Book> bookList = new ArrayList<Book>();

	public ArrayList<Book> getBookList() {
		return bookList;
	}

	int bookIndex = 0;

	/**
	 * 用来标识解析开始
	 */
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		System.out.println("startDocument");
		
	}

	/**
	 * 用来标识解析结束
	 */
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		System.out.println("endDocument");
	}

	/**
	 * 解析xml元素
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.println("startElement");
		// 调用DefaultHandler类的startElement方法
		super.startElement(uri, localName, qName, attributes);
//		if (qName.equals("book")) {
//			bookIndex++;
//			// 创建一个book对象
//			book = new Book();
//			// 开始解析book元素的属性
//			System.out
//					.println("======================开始遍历某一本书的内容=================");
//			// 不知道book元素下属性的名称以及个数，如何获取属性名以及属性值
//			int num = attributes.getLength();
//			for (int i = 0; i < num; i++) {
//				System.out.print("book元素的第" + (i + 1) + "个属性名是："
//						+ attributes.getQName(i));
//				System.out.println("---属性值是：" + attributes.getValue(i));
//				if (attributes.getQName(i).equals("id")) {
//					book.setId(attributes.getValue(i));
//				}
//			}
//		} else if (!qName.equals("name") && !qName.equals("bookstore")) {
//			System.out.print("节点名是：" + qName + "---");
//		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("endElement");
		// 调用DefaultHandler类的endElement方法
		super.endElement(uri, localName, qName);
		// 判断是否针对一本书已经遍历结束
//		if (qName.equals("book")) {
//			bookList.add(book);
//			book = null;
//			System.out.println("======================结束遍历某一本书的内容=================");
//		} else if (qName.equals("name")) {
//			book.setName(value);
//		} else if (qName.equals("author")) {
//			book.setAuthor(value);
//		} else if (qName.equals("year")) {
//			book.setYear(value);
//		} else if (qName.equals("price")) {
//			book.setPrice(value);
//		} else if (qName.equals("language")) {
//			book.setLanguage(value);
//		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("characters");
		super.characters(ch, start, length);
		value = new String(ch, start, length);
		if (!value.trim().equals("")) {
			System.out.println("节点值是：" + value);
		}
	}

}
