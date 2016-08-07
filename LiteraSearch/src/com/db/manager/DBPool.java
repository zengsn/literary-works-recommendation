package com.db.manager;

import com.util.ClassUtil;

/**
 * �������ݿ����ӳص������ļ�
 * 
 * @author CJP
 * 
 */
public class DBPool {

	private String poolPath;// ���ݿ����ӳص������ļ�·��

	private DBPool() {
	}

	public static DBPool getDBPool() {
		return DBPoolDao.dbPool;
	}

	/**
	 * ��̬�ڲ���ʵ�ֵ���ģʽ
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
