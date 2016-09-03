package com.search.dao;

import java.util.List;

import com.crawl.tianya.model.Para;
import com.lucene.index.init.InitIndex;
import com.lucene.index.model.LiteraSearchResultBean;
import com.lucene.index.operation.tianya.TianYaSearch;
import com.util.JsonUtil;

/**
 * 持久层
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
	 * 根据关键字，获得总记录条数
	 */
	public int getAllRecords(String keyword) {
		LiteraSearchResultBean bean = search
				.searchLiteraPara(keyword, 1, 10000);

		return bean.getCount();
	}

	/*
	 * 根据关键字，批量查询记录
	 */
	public List<Para> findAll(String keyword, int start, int size) {
		LiteraSearchResultBean bean = search.searchLiteraPara(keyword, start,
				start + size);
		return bean.getDatas();
	}

	public static void main(String[] args) {
		LiteraDao dao = new LiteraDao();
		System.out.println(JsonUtil.parseJson(dao.findAll("花朵", 1, 10)));
	}
}
