package com.service;

import com.dao.LuceneDao;
import com.entity.Page;
import com.entity.SearchResult;

public class WebService {

	private LuceneDao dao = new LuceneDao();

	/**
	 * 根据关键字获取当前页显示的记录
	 * 
	 * @param keywords
	 * @param currPageNO
	 * @return
	 */
	public Page show(String keywords, int currPageNO) {
		Page page = new Page();
		if (currPageNO < 1) {
			page.setCurrPageNO(1);
		}
		page.setCurrPageNO(currPageNO);
		int start = (currPageNO - 1) * page.getPerPageSize();
		int end = start + page.getPerPageSize();
		SearchResult sr = dao.searchIndex(keywords, start, end, true);
		page.setTotalRecords(sr.getCount());
		int totalPageNO = (sr.getCount() % page.getPerPageSize() == 0) ? (sr
				.getCount() / page.getPerPageSize()) : (sr.getCount()
				/ page.getPerPageSize() + 1);// 关键
		page.setTotalPageNO(totalPageNO);
		page.setParaList(sr.getParaList());
		return page;
	}
}
