package com.crawl.tianya.model;

/**
 * �洢���ҳ��ȡ������Ϣ�����������ߣ�������飬�����½ڵ���Ϣ
 * 
 * @author CJP
 * 
 */
public class IntroPageBean {

	private String name;// ����������
	private String author; // ��������
	private String desc; // �������
	private String id;// ������ΨһID
	private String url;// �������ҳ��url
	private int chapterCount;// �½ڸ���

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
