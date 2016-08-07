package com.crawl.tianya.model;

/**
 * 存储阅读页获取到的信息：章节的标题和章节的正文
 * 
 * @author CJP
 * 
 */
public class ReadPageBean extends ChapterBean {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
