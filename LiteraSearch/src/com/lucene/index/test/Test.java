package com.lucene.index.test;

import com.crawl.tianya.db.TianYaDB;
import com.lucene.index.init.InitIndex;
import com.lucene.index.operation.tianya.TianYaIndex;
import com.lucene.index.operation.tianya.TianYaSearch;
import com.util.JsonUtil;

public class Test {

	public static void main(String[] args) {
		InitIndex.init();
		//new TianYaDB().dataImport2Index();
		//new TianYaIndex().commit();
		TianYaSearch search = new TianYaSearch();
		System.out.println(JsonUtil.parseJson(search.searchLiteraPara("º£Ñó", 1, 10)));;
	}
}
