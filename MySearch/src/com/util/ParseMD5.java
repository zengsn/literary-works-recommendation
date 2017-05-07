package com.util;

public class ParseMD5 extends Encrypt {

	/**
	 * @Description:32位小写MD5
	 * @param str
	 * @return
	 */
	public static String parseStrToMD5(String str) {
		return encrypt(str, MD5);
	}

	/**
	 * @Description:32位大写MD5
	 * @param str
	 * @return
	 */
	public static String parseStrToUpperMD5(String str) {
		return parseStrToMD5(str).toUpperCase();
	}

}
