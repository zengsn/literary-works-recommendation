package com.lucene.index.test;

import java.util.List;

import com.crawl.tianya.db.TianYaDB;
import com.crawl.tianya.model.IntroSearchResultBean;
import com.lucene.index.init.IndexInit;
import com.lucene.index.model.LiteraSearchResultBean;
import com.lucene.index.operation.litera.IntroIndex;
import com.lucene.index.operation.litera.IntroSearch;
import com.util.JsonUtil;

public class Test {

	public static void main(String[] args) {
		IndexInit.initIndex();

		new TianYaDB().dataImportToIndex();
		new IntroIndex().commit();

		IntroSearch search = new IntroSearch();
		System.out.println(JsonUtil.parseJson(search
				.searchLiteraName("《冬天的故事》")));
		LiteraSearchResultBean bean = search.searchLiteraName("《冬天的故事》", 0, 10);
		List<IntroSearchResultBean> datas = bean.getDatas();
		for (IntroSearchResultBean b : datas) {
			System.out.println(b.getName() + "|" + b.getAuthor());
		}
	}
}
