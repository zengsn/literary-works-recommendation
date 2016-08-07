package com.lucene.index.test;

import java.util.HashSet;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import com.lucene.index.manager.IndexManager;
import com.lucene.index.model.ConfigBean;
import com.lucene.index.model.IndexConfig;
import com.lucene.index.model.SearchResultBean;
import com.lucene.index.operation.NRTIndex;
import com.lucene.index.operation.NRTSearch;

/**
 * 测试IndexManager的可用性
 * 
 * @author CJP
 * 
 */
public class IndexManagerTest {

	public static void main(String[] args) throws Exception {
		HashSet<ConfigBean> configBeans = new HashSet<ConfigBean>();
		// 创建4个索引配置对象
		for (int i = 0; i < 4; i++) {
			ConfigBean bean = new ConfigBean();
			bean.setIndexName("test" + i);
			bean.setIndexPath("C:/Users/CJP/Desktop/Lucene/index");
			configBeans.add(bean);
		}
		// 交由IndexConfig统一管理
		IndexConfig.setConfigBeans(configBeans);
		// IndexManager indexManager1 = IndexManager.getIndexManager("test0");
		// indexManager1.release(indexManager1.getIndexSearcher());
		String indexName = "test0";
		NRTIndex index = new NRTIndex(indexName);
		Document doc1 = new Document();
		doc1.add(new StringField("id", "123", Store.YES));
		doc1.add(new TextField("content", "惠州学院计算机科学系", Store.YES));
		index.addDocument(doc1);
		doc1 = new Document();
		doc1.add(new StringField("id", "234", Store.YES));
		doc1.add(new TextField("content", "计算机科学系计师2班", Store.YES));
		index.addDocument(doc1);
		index.commit();
		System.out.println("已经向索引中添加两条记录");

		NRTSearch search = new NRTSearch(indexName);
		QueryParser parser = new QueryParser(Version.LUCENE_43, "content",
				new StandardAnalyzer(Version.LUCENE_43));
		Query query = parser.parse("惠州学院计算机科学系计师2班");
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
		index.deleteAll();
		index.commit();
	}
}
