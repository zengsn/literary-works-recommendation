package com.entity;

/**
 * 目录页
 * 
 * @author Administrator
 * 
 */
public class ContentsPage {

	private String id;// 章节id
	private String url;// 章节url
	private String title;// 标题
	private String state;// 状态
	private String introid;// 名著id

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIntroid() {
		return introid;
	}

	public void setIntroid(String introid) {
		this.introid = introid;
	}

}
