package com.jar.connectPool.erong;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.util.getProperties.erong.BaseConfigUtil;

public class ConnectionManager {
	private static final Logger logger=Logger.getLogger("ConnectionManager");
	public static ThreadLocal<Connection> connectionThreadLocal=new ThreadLocal<>();
	public static ThreadLocal<Connection> connectionThreadLocal1=new ThreadLocal<>();
	private ComboPooledDataSource ds;
	private static ConnectionManager instance;
	
	public ConnectionManager() throws PropertyVetoException{
		dataSourceInit();
	}
	private void dataSourceInit() throws PropertyVetoException{
		Properties p=BaseConfigUtil.getProperties();//这里并没有声明成类对象，也外层 加锁，
		this.ds = new ComboPooledDataSource();	//对于对象默认初始化，依然不能直接用，还是要显示初始化一下。
		this.ds.setUser(p.getProperty("user"));
		this.ds.setPassword(p.getProperty("password"));
		this.ds.setJdbcUrl(p.getProperty("url"));
		this.ds.setDriverClass(p.getProperty("driver"));
		this.ds.setInitialPoolSize(Integer.parseInt(p.getProperty("initialPoolSize")));
		this.ds.setMinPoolSize(Integer.parseInt(p.getProperty("minPoolSize")));
		this.ds.setMaxPoolSize(Integer.parseInt(p.getProperty("maxPoolSize")));
		this.ds.setMaxStatements(Integer.parseInt(p.getProperty("maxStatements")));
		this.ds.setMaxIdleTime(Integer.parseInt(p.getProperty("maxIdleTime")));
	}
	public static ConnectionManager getInstance(){
			try {
					if(instance==null){
//						synchronized (instance) {  //虽然不加，影响也不大，只是多个线程过if时，会创建多个对象，并且后面的对象覆盖前面创建的对象。
						instance=new ConnectionManager();
//					}
				}
			} catch (PropertyVetoException e) {
				e.printStackTrace();
		}
		return instance;
	}
	public void closeConnection(){
		DbHelper.close((Connection)connectionThreadLocal.get());
	}
	public void closeConnection1(){
		DbHelper.close((Connection)connectionThreadLocal1.get());
	}
	/**
	 * 对于为什么要先openConnection(),我们知道，c3p0连接池是第一次使用的时候才启动连接池，open可以将连接池提前启动，
	 * 并且，可以再openConnection出对事务进行管理，提交，或回滚。可以从最表层进行回滚，由于提前提交，会导致提交之后若出现异常，没办法回滚。
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Connection openConnection() throws SQLException, Exception {
		if (connectionThreadLocal.get() != null) {
			closeConnection();
		}
		return getConnection();
	}
	
	public Connection getConnection() throws Exception{
		if(connectionThreadLocal.get()!=null){
			return (Connection)connectionThreadLocal.get();
		}
		logger.debug("connect");
		Connection conn=this.ds.getConnection();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(2);
		connectionThreadLocal.set(conn);
		return (Connection)connectionThreadLocal.get();
	}
	
	protected void finalize() throws Throwable {
		DataSources.destroy(this.ds);   //ComboPooledDataSource 实现了DataSource，实现了destroy方法。
		super.finalize();
	}
	
	public Connection getConnection1() throws SQLException, Exception {
		if (connectionThreadLocal1.get() != null)
			return ((Connection) connectionThreadLocal1.get());
		logger.debug("connect1");
		Connection conn1 = this.ds.getConnection();
		conn1.setAutoCommit(false);
		conn1.setTransactionIsolation(2);
		connectionThreadLocal1.set(conn1);
		return ((Connection) connectionThreadLocal1.get());
	}
}
