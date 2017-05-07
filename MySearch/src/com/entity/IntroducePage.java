package com.entity;
/**
 * 介绍页
 * 
 * @author Administrator
 *
 */
public class IntroducePage {

	private String id;//名著id
	private String url;//介绍页url
	private String author;//作者
	private String name;//书名
	private String description;//简介
	private int chaptercount;//章节数

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getChaptercount() {
		return chaptercount;
	}

	public void setChaptercount(int chaptercount) {
		this.chaptercount = chaptercount;
	}

}
