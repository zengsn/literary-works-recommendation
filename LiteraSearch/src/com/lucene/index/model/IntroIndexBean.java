package com.lucene.index.model;

/**
 * ������Ҫ��ӵ������е���
 * 
 * @author CJP
 * 
 */
public class IntroIndexBean {

	public static final String NAME = "name";
	public static final String NAME_KEY = "name_key";// �����ؼ���
	public static final String AUTHOR = "author";
	public static final String AUTHOR_KEY = "author_key";// ���߹ؼ���
	public static final String DESC = "desc";
	public static final String ID = "id";
	public static final String CHAPTERCOUNT = "chaptercount";

	private String name;// ����������
	private String author; // ��������
	private String desc; // �������
	private String id;// ������ΨһID
	private int chapterCount;// �½ڸ���

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
