package com.db.manager;

import com.util.ClassUtil;

/**
 * 管理数据库连接池的配置文件
 * 
 * @author CJP
 * 
 */
public class DBPool {

	private String poolPath;// 数据库连接池的配置文件路径

	private DBPool() {
	}

	public static DBPool getDBPool() {
		return DBPoolDao.dbPool;
	}

	/**
	 * 静态内部类实现单例模式
	 * @author CJP
	 * 
	 */
	private static class DBPoolDao {
		private static DBPool dbPool = new DBPool();
	}

	public String getPoolPath() {
		if (poolPath == null) {
			poolPath = ClassUtil.getClassPath(DBPool.class) + "proxool.xml";
		}
		return poolPath;
	}
}
