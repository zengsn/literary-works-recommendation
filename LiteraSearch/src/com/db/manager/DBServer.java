package com.db.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 数据库的操作，新增，删除，查询，修改
 * 
 * @author CJP
 * 
 */
public class DBServer {

	private DBOperation dbOperation;

	/**
	 * 
	 * @param poolName	数据库连接池的别名
	 */
	public DBServer(String poolName) {
		dbOperation = new DBOperation(poolName);
	}

	/**
	 * @Description:数据库的关闭
	 */
	public void close() {
		dbOperation.close();
	}

	/**
	 * @Description:数据库的新增操作
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int insert(String sql) throws SQLException {
		return dbOperation.executeUpdate(sql);
	}

	/**
	 * @param tableName	数据库的表名
	 * @param columns	插入的字段
	 * @param params	插入的值
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Description: 数据库新增操作
	 */
	public int insert(String tableName, String columns,
			HashMap<Integer, Object> params) throws SQLException,
			ClassNotFoundException {
		String sql = insertSql(tableName, columns);
		return dbOperation.executeUpdate(sql, params);
	}

	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @Description: 数据库删除操作
	 */
	public int delete(String sql) throws SQLException {
		return dbOperation.executeUpdate(sql);
	}

	/**
	 * @param tableName	数据库的表名
	 * @param condition	查询的条件
	 * @return
	 * @throws SQLException
	 * @Description: 数据库删除操作
	 */
	public int delete(String tableName, String condition) throws SQLException {
		if (null == tableName) {
			return 0;
		}
		String sql = "delete from " + tableName + " " + condition;
		return dbOperation.executeUpdate(sql);
	}

	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @Description: 数据库更新操作
	 */
	public int update(String sql) throws SQLException {
		return dbOperation.executeUpdate(sql);
	}

	/**
	 * @param tableName	数据库的表名
	 * @param columns	更新的字段
	 * @param condition	更新的条件
	 * @param params	更新的值
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @Description: 数据库更新操作
	 */
	public int update(String tableName, String columns, String condition,
			HashMap<Integer, Object> params) throws SQLException,
			ClassNotFoundException {
		String sql = updateSql(tableName, columns, condition);
		return dbOperation.executeUpdate(sql, params);
	}

	/**
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @Description: 数据库查询操作
	 */
	public ResultSet select(String sql) throws SQLException {
		return dbOperation.executeQuery(sql);
	}

	/**
	 * @param tableName	数据库的表名
	 * @param columns	查询的字段
	 * @param condition	查询的条件
	 * @return
	 * @throws SQLException
	 * @Description: 数据库查询操作
	 */
	public ResultSet select(String tableName, String columns, String condition)
			throws SQLException {
		String sql = "select " + columns + " from " + tableName + " "
				+ condition;
		return dbOperation.executeQuery(sql);
	}

	/**
	 * @param tableName	数据库的表名
	 * @param columns	查询的字段
	 * @param condition	查询的条件
	 * @return
	 * @Description: 组装 update sql eg: update tableName set column1=?,column2=?
	 *               condition
	 */
	private String updateSql(String tableName, String columns, String condition) {
		if (tableName == null || columns == null) {
			return "";
		}
		String[] column = columns.split(",");
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		sb.append(column[0]);
		sb.append("=?");
		for (int i = 1; i < column.length; i++) {
			sb.append(", ");
			sb.append(column[i]);
			sb.append("=?");
		}
		sb.append(" ");
		sb.append(condition);
		return sb.toString();
	}

	/**
	 * @param tableName
	 * @param columns
	 * @return
	 * @Description: 组装 insert sql eg: insert into tableName (column1, column2)
	 *               values (?,?)
	 */
	private String insertSql(String tableName, String columns) {
		if (tableName == null || columns == null) {
			return "";
		}
		int n = columns.split(",").length;
		StringBuilder sb = new StringBuilder("");
		sb.append("insert into ");
		sb.append(tableName);
		sb.append(" (");
		sb.append(columns);
		sb.append(") values (?");
		for (int i = 1; i < n; i++) {
			sb.append(",?");
		}
		sb.append(")");
		return sb.toString();
	}

}
