package com.lucene.index.litera.model;

/**
 * 存储从目录中获取到的名著的信息，包括名著名，名著内容，名著存储路径
 * 
 * @author CJP
 * 
 */
public class LiteraBean {

	private String name;
	private String content;
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String conent) {
		this.content = conent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
