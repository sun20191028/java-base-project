package com.erong.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.erong.common.dao.TableNameDao;
import com.erong.common.entity.People;
import com.erong.common.entity.TableName;
import com.jar.connectPool.erong.ConnectionManager;
import com.jar.connectPool.erong.DbHelper;

public class TestEntity {
	public static void main(String[] args) {
		/**
		 * 由于声明的都是包装类，所以默认初始化都为null。
		 */
		/**
		 * 这是一种情况，单线程内部多次访问。
		 * 分开提交耗时6s，一起提交耗时4s    
		 * 所以效率 一起提交>分开提交>多次关闭打开conn>>>>
		 */
//		Connection conn = null;
//		try {
//			conn = ConnectionManager.getInstance().openConnection();
//			TableName tn = new TableName();
//			for (int i = 0; i < 10000; i++) {
////				System.out.println(i);   //输出居然辣么占时间，输出10000条数据，占时4，5s。  
//				
//				// tn.setTAge(22);
//				tn.setTBirthday(new Timestamp(System.currentTimeMillis()));
//				tn.setTDiscount(1.2);
//				tn.setTId(new Long(i));
////				tn.setTId(1L);
//				
////				if(i==500){
////					tn.setTId(new Long(i-1));
////				}
//				// tn.setTMoney(13333l);
//				tn.setTNameCnGf("map清空测试");
//				tn.setTTele("13266506395");
//	
//				People p = new People();
//				p.setPName("peo"+i);
//				p.setPAge(23);
//				
//
//				TableDao.insert(tn);
//				TableDao.insert(p);
//
//				// TableNameDao tnd=new TableNameDao();
//				// tnd.insert(tn);
////				conn.commit();//对于已提交的 conn是不会回滚的。
//			}
//			conn.commit();//若在这一层，则只要其中有一个异常，都会回滚。
//			/**
//			 * 这个还是有影响的，回滚是回滚conn上的东西，commit之前，所有的statement、preparedStatement.都是在conn上的。commit之后，你会发现即便代码只走完这一步，就可以在
//			 * 数据库中查到数据，提交之后，此时的conn上并没有数据，所以回滚，也只能回滚此时的。
//			 * 如果在这里又为conn上弄一些数据，。。。。
//			 */
//			tn.setTId(19565L);
//			TableDao.insert(tn);
//			if(true){
//				throw new Exception("测试提交之后的异常，会不会回滚数据库");
//			}
//			/**
//			 * 。。。在这里再来一次提交，上面的异常则会回滚这里的conn上的数据。
//			 */
//			conn.commit();
//		} catch (Exception e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
		/*
		 * 这是第二种情况，单线程外部，多次访问。 只有一条数据成功，后面的全都失败了。
		 * 上面测了分开单次提交与合起来提交并不会影响速度。
		 * 但是这里速度特别慢。11s，说明打开和关闭连接都是比较耗时的。
		 * 既然都是一个连接，
		 */
		/**
		 * 这种肯定不行，A指向  堆V  B指向堆V B做更改，A提交没问题。此时A关了，B怎么能打开A呢？A关了是A中存储的地址变了，并不是改变了A引用的那一块堆区。
		 * （暂时）其实我们并没有改变当前变量所引用的上一个堆区中的内容，而是重新创建一块或者找到已有的那一块，然后把这个地址交给它，
		 * 因此哪里关闭，重新使用时必须还从这里打开。
		 */
//		Connection conn = null;
//		try {
//			conn = ConnectionManager.getInstance().openConnection();	//又犯错了，它的实例只有一个，单线程只有一个连接，多线程才能使用多个连接。
//																		//所以不管这里的conn还是，该线程的其他地方的conn，都是同一个conn，这层openConnection得到conn只是为了在这一层rollback和commit，这种设计，可以实现提交、回滚与使用的位置不一样，提交和回滚范围更大。
//																		//虽然多线程拿到的ConnectionManager的instance一样，但是conn=connectionThreadLocal.get()不一样。这样一个实例在多个线程中拥有多个连接。
//		} catch (SQLException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		} catch (Exception e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		
//		Connection conn = null;
//		for (int i = 0; i < 10000; i++) {
//			try {
//				conn = ConnectionManager.getInstance().openConnection();
//				TableName tn = new TableName();
//				// tn.setTAge(22);
//				tn.setTBirthday(new Timestamp(System.currentTimeMillis()));
//				tn.setTDiscount(1.2);
//				tn.setTId(new Long(i));
//				tn.setTNameCnGf("map清空测试");
//				tn.setTTele("13266506395");
//	
//				People p = new People();
//				p.setPName("peo"+i);
//				p.setPAge(23);
//				
//
//				TableDao.insert(tn);
//				TableDao.insert(p);
//				conn.commit();//若在这一层，则只要其中有一个异常，都会回滚。
//			} catch (Exception e) {
//				try {
//					conn.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				e.printStackTrace();
//			} finally {
//				if (conn != null) {
//					DbHelper.close(conn);
//				}
//			}
//		}
		
		/**
		 * 为什么10000条数据，通过多线程会更慢。
		 */
		for (int i = 0; i < 10000; i++) {
			TableName tn = new TableName();
			tn.setTBirthday(new Timestamp(System.currentTimeMillis()));
			tn.setTDiscount(1.2);
			tn.setTNameCnGf("map清空测试");
			tn.setTTele("13266506395");
			
			People p = new People();
			p.setPAge(23);
			tn.setTId(new Long(i));
			p.setPName("peo"+i);
			TableDaoThreadTest ttt=new TableDaoThreadTest(tn);
			TableDaoThreadTest tt2=new TableDaoThreadTest(p);
			ttt.run();
			tt2.run();
			System.out.println(i);
		}
		System.out.println(new Timestamp(System.currentTimeMillis()));
	}
}
