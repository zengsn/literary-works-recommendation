package com.util;

/**
 * ���������ڵ�Ŀ¼
 * 
 * @author CJP
 * 
 */
public class ClassUtil {

	/**
	 * ����Class�ļ����ڵ�Ŀ¼
	 * 
	 * @param c
	 * @return
	 */
	public static String getClassPath(Class<?> c) {
		return c.getResource("").getPath().replaceAll("%20", " ");
	}

	/**
	 * ����Class�ļ����ڵĸ�Ŀ¼
	 * 
	 * @param c
	 * @return
	 */
	public static String getClassRootPath(Class<?> c) {
		return c.getResource("/").getPath().replaceAll("%20", " ");
	}
}
