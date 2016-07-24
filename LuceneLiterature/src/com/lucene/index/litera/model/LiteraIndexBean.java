package com.lucene.index.litera.model;

/**
 * 定义索引使用到的相关域名
 * 
 * @author CJP
 * 
 */
public class LiteraIndexBean {

	public static final String INDEX_NAME = "name";
	public static final String INDEX_PATH = "path";
	public static final String INDEX_CONTENT = "content";

	/**
	 * 书名
	 */
	private String name;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 存储路径
	 */
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
