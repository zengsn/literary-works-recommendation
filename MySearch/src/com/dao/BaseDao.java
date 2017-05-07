package com.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseDao {

	private String driverClass = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8";
	private String user = "root";
	private String password = "123456";

	/**
	 * 获取连接对象
	 * 
	 * @return
	 */
	public Connection getConn() {

		try {
			// 加载数据库驱动
			Class.forName(driverClass);
			// 获取数据库连接
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUrlByRand(String table, String state) {

		Connection conn = getConn();

		StringBuffer sb = new StringBuffer("select url from ");
		sb.append(table).append(" where ").append("state='").append(state)
				.append("' order by rand() limit 1");
		// System.out.println(sb);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("url");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return null;

	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateById(String table, String field, Object value, String id) {

		Connection conn = getConn();

		StringBuffer sb = new StringBuffer("update ");
		sb.append(table).append(" set ").append(field).append("=?")
				.append(" where id=?");

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setObject(1, value);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}

	}

	/**
	 * 根据Id删除数据
	 * 
	 * @param table
	 * @param id
	 */
	public void deleteById(String table, String id) {

		// 1.获取数据库连接
		Connection conn = getConn();

		// 2.组装SQL
		StringBuffer sb = new StringBuffer("delete from ");
		sb.append(table);
		sb.append(" where id=?");

		// 预处理
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, id);

			// 执行SQL，返回影响的行数
			pstmt.executeUpdate();
			System.out.println("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null);
		}

	}

	/**
	 * 往数据库中插入数据
	 * 
	 * @param obj
	 */
	public boolean add(Object obj) {

		boolean b = false;

		// 1.获取连接
		Connection conn = getConn();

		// 2.组装SQL语句:insert into listpage(id,url,author,name)
		// value('fd44ad4bdd7716f725959f4342f11ba4','http://www.ty2016.net/horror/DarkTower_7/','斯蒂芬・金','黑暗之塔(黑暗塔7)')
		StringBuffer sb = new StringBuffer("insert into ");

		// 获取对象的Class对象
		Class<?> clazz = obj.getClass();
		String tableName = clazz.getSimpleName().toLowerCase();
		sb.append(tableName).append("(");
		// 获取对象所有的属性
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (field.equals(fields[fields.length - 1])) {
				sb.append(fieldName).append(")");
			} else {
				sb.append(fieldName).append(",");
			}
		}
		int fieldLength = fields.length;
		sb.append(" value(");
		for(int i=0; i<fieldLength;i++){
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");

		// 预处理
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			
			Pattern pattern = Pattern.compile(".*\\(([a-zA-z_,]*)\\).*");
			Matcher matcher = pattern.matcher(sb);
			String temp = "";
			if (matcher.find()) {
				temp = matcher.group(1);
			}
			String[] temps = temp.split(",");
			int index = 1;
			for (String t : temps) {
				// 组装getXXX()方法
				String getXXX = "get"
						+ t.substring(0, 1).toUpperCase()
								.concat(t.substring(1));
				// 获取对象的getXXX()方法
				Method method = clazz.getMethod(getXXX, null);
				// 调用对象的getXXX()方法
				Object o = method.invoke(obj, null);
				// 获取对象的返回值类型
				Type returnType = method.getGenericReturnType();
				// 当返回值类型是String时，在值得两边加上''
				if (returnType.toString().equals("class java.lang.String")) {
					pstmt.setString(index++, o+"");
					//sb.append("\"").append(o).append("\"").append(",");
				} else if(returnType.toString().equals("class java.lang.Int")){
					//sb.append(o).append(",");
					pstmt.setInt(index++, (Integer)o);
				}
			}
			
			// 执行SQL，返回影响的行数
			pstmt.executeUpdate();
			System.out.println("插入成功");
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		} finally {
			closeAll(conn, pstmt, null);
		}
		return b;
	}

	/**
	 * 获取表数据
	 * 
	 * @param table
	 *            表名
	 * @param paramMap
	 *            参数
	 * @return
	 */
	public List<Object> findByCondition(String table,
			Map<String, Object> paramMap) {

		// 1.获取连接
		Connection conn = getConn();

		// 2.组装SQL语句
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(table).append(" where 1=1");
		if (paramMap != null && paramMap.size() > 0) {
			for(Map.Entry<String, Object> paramEntry:paramMap.entrySet()){
				sb.append(" and ").append(paramEntry.getKey()).append("=?");
			}
		}

		List<Object> list = new ArrayList<Object>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 预处理
			pstmt = conn.prepareStatement(sb.toString());
			if (paramMap != null && paramMap.size() > 0) {
				int index = 1;
				for(Map.Entry<String, Object> paramEntry:paramMap.entrySet()){
					pstmt.setObject(index++, paramEntry.getValue());
				}
			}

			// 加载数据库表对应实体类
			table = table.substring(0, 1).toUpperCase()
					.concat(table.substring(1, table.length()));
			String classPath = "com.entity." + table;
			Class<?> clazz = Class.forName(classPath);

			// 执行SQL，返回结果集
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				// 获取实体类的实例对象
				Object obj = clazz.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					// 拼接setXxx()方法
					String columnLabel = rsmd.getColumnLabel(i).toLowerCase();
					//System.out.println(columnLabel);
					String columnType = rsmd.getColumnTypeName(i);
					String setXxx = "set"
							+ columnLabel
									.substring(0, 1)
									.toUpperCase()
									.concat(columnLabel.substring(1));
					// 调用实例对象的setXxx()方法
					if (columnType.equals("INT")) {
						Method setMethod = clazz.getMethod(setXxx, int.class);
						setMethod.invoke(obj, rs.getDouble(columnLabel));
					} else if (columnType.equals("VARCHAR")
							|| columnType.equals("TEXT")||columnType.equals("LONGTEXT")) {
						Method setMethod = clazz
								.getMethod(setXxx, String.class);
						setMethod.invoke(obj, rs.getString(columnLabel));
					} else if (columnType.equals("DATE")) {
						Method setMethod = clazz.getMethod(setXxx, Date.class);
						setMethod.invoke(obj, rs.getDate(columnLabel));
					}
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return list;
	}
}
