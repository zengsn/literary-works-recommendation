package com.crawl.tianya.model;

/**
 * 存储简介页获取到的信息：名著的作者，名著简介，名著章节的信息
 * 
 * @author CJP
 * 
 */
public class IntroPageBean {

	private String name;// 名著的书名
	private String author; // 名著作者
	private String desc; // 名著简介
	private String id;// 名著的唯一ID
	private String url;// 名著简介页的url
	private int chapterCount;// 章节个数

	public int getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

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

}
