package com.util;

public class StringUtils {

	/**
	 * 判断字符串为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		boolean b = false;
		if ("".equals(str) || null == str) {
			b = true;
		}
		return b;
	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		boolean b = false;
		if (!"".equals(str) && null != str) {
			b = true;
		}
		return b;
	}

	public static String arr2Str(String[] arr) {
		String str = "";
		for (String a : arr) {
			str += a;
		}
		return str;
	}

}
