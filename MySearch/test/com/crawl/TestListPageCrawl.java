package com.crawl;

import java.util.List;

import org.junit.Test;

import com.entity.ListPage;

public class TestListPageCrawl {

	/**
	 * 测试列表页爬虫
	 */
	@Test
	public void test01() {
		String url = "http://www.ty2016.net/world/1.html";
		ListPageCrawl crawl = new ListPageCrawl(url);
		List<ListPage> lpList = crawl.getLists();
		for (ListPage lp : lpList) {
			System.out.println(lp.getAuthor());
		}
	}
}
