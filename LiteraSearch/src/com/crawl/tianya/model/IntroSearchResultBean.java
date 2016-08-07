package com.crawl.tianya.model;
/**
 * 保存数据库返回的名著的信息
 * @author CJP
 *
 */
public class IntroSearchResultBean {

	private String name;// 书名
	private String author;// 作者
	private String desc;// 简介
	private int chaptercount;// 章节个数
	private String id;// 简介页标识

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

	public int getChaptercount() {
		return chaptercount;
	}

	public void setChaptercount(int chaptercount) {
		this.chaptercount = chaptercount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
