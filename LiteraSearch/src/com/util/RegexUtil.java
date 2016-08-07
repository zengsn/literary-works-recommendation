package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * 
 * @author CJP
 * 
 */
public class RegexUtil {

	private static String rootUrlRegex = "(http://.*?/)";// 获取主页的url
	private static String currentUrlRegex = "(http://.*/)";// 获取当前网页url的上一级
	private static String ChRegex = "([\u4e00-\u9fa5]+)"; // 匹配一个或多个汉字

	/**
	 * 通过正则获取想要的字符串数据，用splitStr分隔后存储在String中
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param splitStr
	 *            分割符
	 * @param n
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static String getString(String dealStr, String regexStr,
			String splitStr, int n) {
		String reStr = "";
		if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()) {
			return reStr;
		}
		splitStr = (splitStr == null) ? "" : splitStr;
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		StringBuffer stringBuffer = new StringBuffer();
		while (matcher.find()) {
			stringBuffer.append(matcher.group(n).trim());
			// 匹配的数据之间通过splitStr分离
			stringBuffer.append(splitStr);
		}
		reStr = stringBuffer.toString();
		if (splitStr != "" && reStr.endsWith(splitStr)) {
			// 去掉末尾的splitStr
			reStr = reStr.substring(0, reStr.length() - splitStr.length());
		}
		return reStr;
	}

	/**
	 * 将所有匹配结果组装成字符串
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param n
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static String getString(String dealStr, String regexStr, int n) {
		return getString(dealStr, regexStr, null, n);
	}

	/**
	 * 获取正则匹配的第一个结果
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param n
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static String getFirstString(String dealStr, String regexStr, int n) {
		if (dealStr == null || regexStr == null || n < 1) {
			return "";
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			return matcher.group(n).trim();
		}
		return "";
	}

	/**
	 * 正则匹配字符串并将所有结果保存在List中
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param n
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static List<String> getList(String dealStr, String regexStr, int n) {
		List<String> list = new ArrayList<String>();
		if (dealStr == null || regexStr == null || n < 1) {
			return list;
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			list.add(matcher.group(n).trim());
		}
		return list;
	}

	/**
	 * 获取全部
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param array
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static List<String[]> getList(String dealStr, String regexStr,
			int[] array) {
		List<String[]> list = new ArrayList<String[]>();
		if (dealStr == null || regexStr == null || array == null) {
			return list;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] < 1) {
				return list;
			}
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			String[] ss = new String[array.length];
			for (int i = 0; i < array.length; i++) {
				ss[i] = matcher.group(array[i]).trim();
			}
			list.add(ss);
		}
		return list;
	}

	/**
	 * 获取全部
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param array
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static List<String> getStringArray(String dealStr, String regexStr,
			int[] array) {
		List<String> reStringList = new ArrayList<String>();
		if (dealStr == null || regexStr == null || array == null) {
			return reStringList;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] < 1) {
				return reStringList;
			}
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(matcher.group(array[i]).trim());
			}
			reStringList.add(sb.toString());
		}
		return reStringList;
	}

	/**
	 * 获取第一次匹配到的结果
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param array
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static String[] getFirstArray(String dealStr, String regexStr,
			int[] array) {
		if (dealStr == null || regexStr == null || array == null) {
			return null;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] < 1) {
				return null;
			}
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			String[] ss = new String[array.length];
			for (int i = 0; i < array.length; i++) {
				ss[i] = matcher.group(array[i]).trim();
			}
			return ss;
		}
		return null;
	}

	/**
	 * 将链接url中的中文进行编码处理
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeUrlCh(String url)
			throws UnsupportedEncodingException {
		while (true) {
			String s = getFirstString(url, ChRegex, 1);
			if ("".equals(s)) {
				return url;
			}
			url = url.replaceAll(s, URLEncoder.encode(s, "utf-8"));
		}
	}

	/**
	 * 将url转化为绝对的链接地址
	 * 
	 * @param url
	 *            正则匹配出来的内容
	 * @param currentUrl
	 *            当前的url
	 * @return
	 */
	public static String getHttpUrl(String url, String currentUrl) {
		try {
			url = encodeUrlCh(url).replaceAll("\\\\/", "/");// 将"\\/"换为"/"
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (url.indexOf("http") == 0) {
			// 如果url以"http"开始（本身为绝对地址）则直接返回此url
			return url;
		}
		if (url.indexOf("/") == 0) {
			// 如果url以"/"开始（主机下的根地址）则获取url的根地址加上url
			return getFirstString(currentUrl, rootUrlRegex, 1)
					+ url.substring(1);
		}
		if (url.indexOf("\\/") == 0) {
			// 如果url以"\/"开始（当前页的地址）则获取url的根地址加上url
			return getFirstString(currentUrl, rootUrlRegex, 1)
					+ url.substring(2);
		}
		return getFirstString(currentUrl, currentUrlRegex, 1) + url;
	}

	/**
	 * 获取和正则匹配的网页、网站的绝对的链接地址，放入List中
	 * 
	 * @param dealStr
	 *            待处理的字符串
	 * @param regexStr
	 *            正则字符串
	 * @param currentUrl
	 *            当前的url地址
	 * @param n
	 *            提取的内容在正则中的位置
	 * @return
	 */
	public static List<String> getArrayList(String dealStr, String regexStr,
			String currentUrl, int n) {
		List<String> reArrayList = new ArrayList<String>();
		if (dealStr == null || regexStr == null || n < 1 || dealStr.isEmpty()) {
			return reArrayList;
		}
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher matcher = pattern.matcher(dealStr);
		while (matcher.find()) {
			reArrayList.add(getHttpUrl(matcher.group(n).trim(), currentUrl));
		}
		return reArrayList;
	}

	public static void main(String[] args) {
		String dealStr = "ab1234asdv";
		String regexStr = "a(.*?)a";
		System.out.println(RegexUtil.getFirstString(dealStr, regexStr, 1));
		String s1 = "http://www.mzhu8.com/book/1444/index.html";
		System.out.println(RegexUtil.getFirstString(s1, rootUrlRegex, 1));
	}

}
