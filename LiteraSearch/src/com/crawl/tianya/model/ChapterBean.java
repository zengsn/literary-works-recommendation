package com.crawl.tianya.model;

/**
 * 存储章节列表获取到的信息：章节标题，章节对应的阅读页的url，章节的序号等
 * 
 * @author CJP
 * 
 */
public class ChapterBean {

	private String id;// 章节的唯一标识
	private String url; // 章节对应的阅读页的url
	private String title; // 章节的标题
	private int chapterId; // 章节的序号

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

}
