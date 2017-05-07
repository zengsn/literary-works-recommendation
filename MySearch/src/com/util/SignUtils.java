package com.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 用于校验请求是否来自微信服务器
 * 
 * @author Administrator
 *
 */
public class SignUtils {

	private static final String TOKEN = "weixintoken"; // 与接口配置信息中填写的Token要一致

	/**
	 * 检验signature
	 * 
	 * @param signautre
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signautre, String timestamp,
			String nonce) {
		boolean b = false;

		String[] arr = new String[] { TOKEN, timestamp, nonce };
		// 将token、timestamp、nonce三个参数按照字典序排序
		Arrays.sort(arr);
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteArrToHexStr(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		content = null;
		// 将加密后的字符串与signature对比，为true则标识该请求来源于微信
		b = tmpStr != null ? tmpStr.equals(signautre.toUpperCase()) : false;
		return b;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteArrToHexStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
