﻿package com.crawl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.util.CharsetUtils;

/**
 * 网页爬取的基类
 * 
 * @author CJP
 * 
 */
public abstract class BaseCrawl {

	private static Logger log = Logger.getLogger(BaseCrawl.class);
	/**
	 * 网页源代码
	 */
	private String pageSourceCode = "";
	/**
	 * 返回头信息
	 */
	private Header[] reponseHeaders = null;
	/**
	 * 连接超时时间
	 */
	private static int connectTimeOut = 10000;
	/**
	 * 连接读取时间
	 */
	private static int readTimeOut = 10000;
	/**
	 * 默认最大访问次数
	 */
	private static int maxConnectTimes = 3;
	/**
	 * 网页默认编码方式
	 */
	private static String charsetName = "gb2312";
	// 将HttpClient委托给MultiThreadedHttpConnectionManager，支持多线程
	private static MultiThreadedHttpConnectionManager httpConnectManager = new MultiThreadedHttpConnectionManager();
	private static HttpClient httpClient = new HttpClient(httpConnectManager);

	static {
		// 设置通过网络与服务器建立连接的超时时间为10秒
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(connectTimeOut);
		// 设置Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间为10秒
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(readTimeOut);
		// 设置请求的编码格式
		httpClient.getParams().setContentCharset("utf-8");
	}

	/**
	 * 执行请求方法，获取服务器返回的头信息和网页源代码
	 */
	private boolean readPage(HttpMethod method, String defaultCharset,
			String urlStr) {
		int n = maxConnectTimes;
		while (n > 0) {
			try {
				// 判断返回状态是否是200
				if (httpClient.executeMethod(method) != HttpStatus.SC_OK) {
					log.info("can't connect " + urlStr + (maxConnectTimes - n + 1));
					n--;
				} else {
					// 获取头信息
					reponseHeaders = method.getRequestHeaders();
					// 获取服务器的输出流
					InputStream inputStream = method.getResponseBodyAsStream();
					BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
					StringBuffer sb = new StringBuffer("");
					String line = null;
					while ((line = bufferReader.readLine()) != null) {
						sb.append(line).append("\n");
					}
					// 获取网页源代码
					pageSourceCode = sb.toString();
					// 检测流的编码方式
					InputStream in = new ByteArrayInputStream(pageSourceCode.getBytes(charsetName));
					String charset = CharsetUtils.getStreamCharset(in, defaultCharset);
					// 如果编码方式不同，则进行转码操作
					if (!charsetName.toLowerCase().equals(charset.toLowerCase())) {
						pageSourceCode = new String(pageSourceCode.getBytes(charsetName), charset);
					}
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(urlStr + "cant connect " + (maxConnectTimes - n + 1));
				n--;
			}
		}
		return false;
	}

	/**
	 * 添加头信息，创建Get请求
	 */
	private GetMethod createGetMethod(String urlStr,
			HashMap<String, String> params) {
		GetMethod method = new GetMethod(urlStr);
		if (params == null) {
			return method;
		}
		Iterator<Entry<String, String>> itr = params.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) itr.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			method.setRequestHeader(key, val);
		}
		return method;
	}

	/**
	 * 创建POST请求
	 */
	private PostMethod createPostMethod(String urlStr,
			HashMap<String, String> params) {
		PostMethod method = new PostMethod(urlStr);
		if (params == null) {
			return method;
		}
		Iterator<Entry<String, String>> itr = params.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) itr.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			method.setRequestHeader(key, val);
		}
		return method;
	}

	/**
	 * GET方式请求页面
	 */
	public boolean readPageByGet(String urlStr, HashMap<String, String> params,
			String charsetName) {
		GetMethod method = createGetMethod(urlStr, params);
		return readPage(method, charsetName, urlStr);
	}

	/**
	 * POST方式请求页面
	 */
	public boolean readPageByPost(String urlStr,
			HashMap<String, String> params, String charsetName) {
		PostMethod method = createPostMethod(urlStr, params);
		return readPage(method, charsetName, urlStr);
	}

	/**
	 * 获取网页源代码
	 */
	public String getPageSourceCode() {
		return pageSourceCode;
	}

	/**
	 * 获取网页返回头信息
	 */
	public Header[] getHeader() {
		return reponseHeaders;
	}

	/**
	 * 设置连接超时时间
	 */
	public void setConnectTimeOut(int timeOut) {
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(timeOut);
		BaseCrawl.connectTimeOut = timeOut;
	}

	/**
	 * 设置读取超时时间
	 */
	public void setReadTimeOut(int timeOut) {
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		BaseCrawl.readTimeOut = timeOut;
	}

	/**
	 * 设置最大访问次数，链接失败的情况下使用
	 */
	public static void setMaxConnectTimes(int maxConnectTimes) {
		BaseCrawl.maxConnectTimes = maxConnectTimes;
	}

	/**
	 * 设置连接超时时间和读取超时时间
	 */
	public void setTimeout(int connectTimeout, int readTimeout) {
		setConnectTimeOut(connectTimeout);
		setReadTimeOut(readTimeout);
	}

	/**
	 * 设置默认编码方式
	 */
	public static void setCharsetName(String charsetName) {
		BaseCrawl.charsetName = charsetName;
	}

}