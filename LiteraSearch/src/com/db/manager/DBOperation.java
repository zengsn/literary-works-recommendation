package com.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * ����ִ��SQL���
 * 
 * @author CJP
 * 
 */
public class DBOperation {

	private String poolName;// ���ݿ����ӳر���
	private Connection con = null;// ���ݿ�����

	public DBOperation(String poolName) {
		this.poolName = poolName;
	}

	/**
	 * @Description:�ر����ݿ�����
	 */
	public void close() {
		try {
			if (this.con != null) {
				this.con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:�����ݿ�����
	 * @throws SQLException
	 */
	private void open() throws SQLException {
		// �ȹرպ�򿪣���ֹ���ݿ��������
		close();
		this.con = DBManager.getDBManager().getConnection(this.poolName);
	}

	/**
	 * @Description:SQL������ת��
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private PreparedStatement setPres(String sql,
			HashMap<Integer, Object> params) throws SQLException,
			ClassNotFoundException {
		if (null == params || params.size() < 1) {
			return null;
		}
		PreparedStatement pres = this.con.prepareStatement(sql);
		for (int i = 1; i <= params.size(); i++) {
			if (params.get(i) == null) {
				pres.setString(i, "");
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.String")) {
				pres.setString(i, params.get(i).toString());
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.Integer")) {
				pres.setInt(i, (Integer) params.get(i));
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.Long")) {
				pres.setLong(i, (Long) params.get(i));
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.Double")) {
				pres.setDouble(i, (Double) params.get(i));
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.Float")) {
				pres.setFloat(i, (Float) params.get(i));
			} else if (params.get(i).getClass() == Class
					.forName("java.lang.Boolean")) {
				pres.setBoolean(i, (Boolean) params.get(i));
			} else if (params.get(i).getClass() == Class
					.forName("java.sql.Date")) {
				pres.setDate(i, java.sql.Date.valueOf(params.get(i).toString()));
			} else {
				return null;
			}
		}
		return pres;
	}

	/**
	 * @Description:ִ��SQL��䣬����Ӱ�������
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws SQLException {
		this.open();
		Statement state = this.con.createStatement();
		return state.executeUpdate(sql);
	}

	/**
	 * @Description:ִ��SQL��䣬����Ӱ�������
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int executeUpdate(String sql, HashMap<Integer, Object> params)
			throws SQLException, ClassNotFoundException {
		this.open();
		PreparedStatement pres = setPres(sql, params);
		if (null == pres) {
			return 0;
		}
		return pres.executeUpdate();
	}

	/**
	 * @Description:ִ��SQL��䣬���ؽ����
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		this.open();
		Statement state = this.con.createStatement();
		return state.executeQuery(sql);
	}

	/**
	 * @Description:ִ��SQL��䣬���ؽ����
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet executeQuery(String sql, HashMap<Integer, Object> params)
			throws SQLException, ClassNotFoundException {
		this.open();
		PreparedStatement pres = setPres(sql, params);
		if (null == pres) {
			return null;
		}
		return pres.executeQuery();
	}

}
