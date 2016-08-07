package com.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

/**
 * ���ݿ����ӳصĹ���
 * 
 * @author CJP
 * 
 */
public class DBManager {

	private DBManager() {
		try {
			// �������ݿ����ӳ������ļ�
			JAXPConfigurator.configure(DBPool.getDBPool().getPoolPath(), false);
			// �������ݿ�������
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @param poolName
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String poolName) throws SQLException {
		return DriverManager.getConnection(poolName);
	}

	/**
	 * �ڲ���̬��ʵ�ֵ���ģʽ
	 * 
	 * @author CJP
	 * 
	 */
	private static class DBManagerDao {
		private static DBManager dbManager = new DBManager();
	}

	/**
	 * �������ݿ����ӳع�����
	 * 
	 * @return
	 */
	public static DBManager getDBManager() {
		return DBManagerDao.dbManager;
	}

}
