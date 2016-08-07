package com.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * 数据库连接池的管理
 * 
 * @author CJP
 * 
 */
public class DBManager {

	private DBManager() {
		try {
			// 加载数据库连接池配置文件
			JAXPConfigurator.configure(DBPool.getDBPool().getPoolPath(), false);
			// 加载数据库驱动类
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param poolName
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String poolName) throws SQLException {
		return DriverManager.getConnection(poolName);
	}

	/**
	 * 内部静态类实现单例模式
	 * 
	 * @author CJP
	 * 
	 */
	private static class DBManagerDao {
		private static DBManager dbManager = new DBManager();
	}

	/**
	 * 返回数据库连接池管理类
	 * 
	 * @return
	 */
	public static DBManager getDBManager() {
		return DBManagerDao.dbManager;
	}

}
