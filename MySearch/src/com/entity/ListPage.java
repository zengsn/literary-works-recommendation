package com.entity;

/**
 * 列表页
 * 
 * @author Administrator
 * 
 */
public class ListPage {

	private String id;// 名著id
	private String url;// 介绍页url
	private String author;// 作者
	private String name;// 书名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
