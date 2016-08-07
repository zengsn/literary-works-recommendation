package com.util;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;

import com.crawl.tianya.model.IntroSearchResultBean;
import com.lucene.index.model.IntroIndexBean;

/**
 * ת��Bean��Document
 * 
 * @author CJP
 * 
 */
public class ParseBean {

	/**
	 * �������л�ȡ����������ӵ�Document��
	 */
	public static Document parseIntroToDoc(IntroIndexBean bean) {
		if (bean == null) {
			return null;
		}
		Document doc = new Document();
		if (bean.getId() != null) {
			doc.add(new StringField(IntroIndexBean.ID, bean.getId(), Store.YES));
		} else {
			return null;
		}
		if (bean.getName() != null) {
			// ����Ϊһ������
			doc.add(new StringField(IntroIndexBean.NAME, bean.getName(),
					Store.YES));
			// �����з�
			doc.add(new TextField(IntroIndexBean.NAME_KEY, bean.getName(),
					Store.YES));
		}
		if (bean.getAuthor() != null) {
			// ����Ϊһ������
			doc.add(new StringField(IntroIndexBean.AUTHOR, bean.getAuthor(),
					Store.YES));
			// �����з�
			doc.add(new TextField(IntroIndexBean.AUTHOR_KEY, bean.getAuthor(),
					Store.YES));
		}
		if (bean.getDesc() != null) {
			doc.add(new StringField(IntroIndexBean.DESC, bean.getDesc(),
					Store.YES));
		}
		if (bean.getChapterCount() > 0) {
			doc.add(new IntField(IntroIndexBean.CHAPTERCOUNT, bean
					.getChapterCount(), Store.YES));
		}
		return doc;
	}

	/**
	 * ����ȡ����Documentת��ΪBean
	 */
	public static IntroSearchResultBean parseDocToBean(Document doc) {
		if (doc == null) {
			return null;
		}
		IntroSearchResultBean bean = new IntroSearchResultBean();
		bean.setId(doc.get(IntroIndexBean.ID));
		bean.setName(doc.get(IntroIndexBean.NAME));
		bean.setAuthor(doc.get(IntroIndexBean.AUTHOR));
		bean.setChaptercount(Integer.parseInt(doc
				.get(IntroIndexBean.CHAPTERCOUNT)));
		bean.setDesc(doc.get(IntroIndexBean.DESC));
		return bean;
	}
}
