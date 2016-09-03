package com.search.dao;

import java.util.List;

import com.crawl.tianya.model.Para;
import com.lucene.index.init.InitIndex;
import com.lucene.index.model.LiteraSearchResultBean;
import com.lucene.index.operation.tianya.TianYaSearch;
import com.util.JsonUtil;

/**
 * �־ò�
 * 
 * @author CJP
 * 
 */
public class LiteraDao {

	static {
		InitIndex.init();
	}
	private static TianYaSearch search = new TianYaSearch();

	/*
	 * ���ݹؼ��֣�����ܼ�¼����
	 */
	public int getAllRecords(String keyword) {
		LiteraSearchResultBean bean = search
				.searchLiteraPara(keyword, 1, 10000);

		return bean.getCount();
	}

	/*
	 * ���ݹؼ��֣�������ѯ��¼
	 */
	public List<Para> findAll(String keyword, int start, int size) {
		LiteraSearchResultBean bean = search.searchLiteraPara(keyword, start,
				start + size);
		return bean.getDatas();
	}

	public static void main(String[] args) {
		LiteraDao dao = new LiteraDao();
		System.out.println(JsonUtil.parseJson(dao.findAll("����", 1, 10)));
	}
}
