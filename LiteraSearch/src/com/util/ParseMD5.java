package com.util;

public class ParseMD5 extends Encrypt {

	/**
	 * @Description:32λСдMD5
	 * @param str
	 * @return
	 */
	public static String parseStrToMD5(String str) {
		return encrypt(str, MD5);
	}

	/**
	 * @Description:32λ��дMD5
	 * @param str
	 * @return
	 */
	public static String parseStrToUpperMD5(String str) {
		return parseStrToMD5(str).toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(ParseMD5
				.parseStrToMD5("http://www.ty2016.net/world/Calvino3/"));
	}
}
