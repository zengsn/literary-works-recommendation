package com.lucene.test;

import java.util.HashSet;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import com.lucene.index.model.IndexConfig;
import com.lucene.index.model.IndexConfigBean;
import com.lucene.index.model.SearchResultBean;
import com.lucene.index.operation.NRTIndex;
import com.lucene.index.operation.NRTSearch;

public class IndexManagerTest {

	public static void main(String[] args) {

		HashSet<IndexConfigBean> set = new HashSet<IndexConfigBean>();
		for (int i = 0; i < 4; i++) {
			IndexConfigBean bean = new IndexConfigBean();
			bean.setIndexPath("C:/Users/CJP/Desktop/Lucene/index");
			bean.setIndexName("test" + i);
			set.add(bean);
		}
		IndexConfig.setIndexConfig(set);
		String indexName = "test0";
		NRTIndex index = new NRTIndex(indexName);
		Document doc1 = new Document();
		doc1.add(new StringField("id", "1", Store.YES));
		doc1.add(new TextField("content", "惠州学院计算机科学系", Store.YES));
		index.addDocument(doc1);
		doc1 = new Document();
		doc1.add(new StringField("id", "2", Store.YES));
		doc1.add(new TextField("content", "计算机科学系计师2班", Store.YES));
		index.addDocument(doc1);
		index.commit();

		NRTSearch search = new NRTSearch(indexName);
		QueryParser parse = new QueryParser(Version.LUCENE_43, "content",
				new StandardAnalyzer(Version.LUCENE_43));
		Query query;

		try {
			query = parse.parse("惠州学院计算机科学系计师2班");
			SearchResultBean bean = search.search(query, 0, 10);
			System.out.println("第一次查询" + bean.getCount());
			doc1 = new Document();
			doc1.add(new StringField("id", "2", Store.YES));
			doc1.add(new TextField("content", "", Store.YES));
			Term term = new Term("id", "2");
			index.updateDocument(term, doc1);
			index.commit();
			System.out.println("第一次修改一条记录");
			bean = search.search(query, 0, 10);
			System.out.println("第二次查询" + bean.getCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// IndexManager manager = IndexManager.getIndexManager("test0");

	}
}
