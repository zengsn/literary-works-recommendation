package com.crawl.tianya.model;
/**
 * �������ݿⷵ�ص���������Ϣ
 * @author CJP
 *
 */
public class IntroSearchResultBean {

	private String name;// ����
	private String author;// ����
	private String desc;// ���
	private int chaptercount;// �½ڸ���
	private String id;// ���ҳ��ʶ

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
