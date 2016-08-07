package com.lucene.index.model;

/**
 * 简介表中要添加到索引中的域
 * 
 * @author CJP
 * 
 */
public class IntroIndexBean {

	public static final String NAME = "name";
	public static final String NAME_KEY = "name_key";// 书名关键字
	public static final String AUTHOR = "author";
	public static final String AUTHOR_KEY = "author_key";// 作者关键字
	public static final String DESC = "desc";
	public static final String ID = "id";
	public static final String CHAPTERCOUNT = "chaptercount";

	private String name;// 名著的书名
	private String author; // 名著作者
	private String desc; // 名著简介
	private String id;// 名著的唯一ID
	private int chapterCount;// 章节个数

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

	public int getChapterCount() {
		return chapterCount;
	}

	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}

}
