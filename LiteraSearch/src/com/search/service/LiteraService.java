package com.search.service;

import com.crawl.tianya.model.Para;
import com.lucene.index.model.Page;
import com.search.dao.LiteraDao;

/**
 * 业务层
 * 
 * @author CJP
 * 
 */
public class LiteraService {

	private LiteraDao dao = new LiteraDao();

	/*
	 * 根据关键字获取当前页显示的内容
	 */
	public Page show(String keyword, int currPageNO) throws Exception {
		Page page = new Page();
		page.setCurrPageNO(currPageNO);
		page.setTotalPageNO(dao.getAllRecords(keyword));
		int totalPageNO = (dao.getAllRecords(keyword) % page.getPerPageSize() == 0) ? (dao
				.getAllRecords(keyword) / page.getPerPageSize()) : (dao
				.getAllRecords(keyword) / page.getPerPageSize() + 1);// 关键
		page.setTotalPageNO(totalPageNO);
		page.setTotalRecords(dao.getAllRecords(keyword));
		int start = (currPageNO - 1) * page.getPerPageSize();// 关键
		page.setParaList(dao.findAll(keyword, start, page.getPerPageSize()));
		return page;
	}

	public static void main(String[] args) throws Exception {
		LiteraService service = new LiteraService();
		Page page = service.show("弦乐器", 2);
		System.out.println(page.getCurrPageNO());
		System.out.println(page.getPerPageSize());
		System.out.println(page.getTotalPageNO());
		System.out.println(page.getTotalRecords());
		for (Para a : page.getParaList()) {
			System.out.println(a);
		}
	}

}
