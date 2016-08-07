package com.crawl.tianya.model;

/**
 * 存储列表页获取到的信息：下一跳的地址，作者，书名
 * 
 * @author CJP
 * 
 */
public class ListPageBean {

	private String url;
	// 下一跳的url
	private String author;
	// 作者
	private String name;

	// 书名
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
