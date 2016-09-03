package com.crawl.tianya.model;

public class Para {

	public static final String AUTHOR = "author";
	public static final String NAME = "name";
	public static final String CHAPTERNAME = "chaptername";
	public static final String PARA = "para";

	private String author;
	private String name;
	private String chaptername;
	private String para;

	public Para() {
	}

	public Para(String author, String name, String chaptername, String para) {
		this.author = author;
		this.name = name;
		this.chaptername = chaptername;
		this.para = para;
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

	public String getChaptername() {
		return chaptername;
	}

	public void setChaptername(String chaptername) {
		this.chaptername = chaptername;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	@Override
	public String toString() {
		return "Para [author=" + author + ", name=" + name + ", chaptername="
				+ chaptername + ", para=" + para + "]";
	}
}
