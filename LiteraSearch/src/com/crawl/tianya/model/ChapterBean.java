package com.crawl.tianya.model;

/**
 * �洢�½��б��ȡ������Ϣ���½ڱ��⣬�½ڶ�Ӧ���Ķ�ҳ��url���½ڵ���ŵ�
 * 
 * @author CJP
 * 
 */
public class ChapterBean {

	private String id;// �½ڵ�Ψһ��ʶ
	private String url; // �½ڶ�Ӧ���Ķ�ҳ��url
	private String title; // �½ڵı���
	private int chapterId; // �½ڵ����

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
