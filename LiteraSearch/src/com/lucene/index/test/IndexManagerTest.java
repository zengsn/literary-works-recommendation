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
 * ����IndexManager�Ŀ�����
 * 
 * @author CJP
 * 
 */
public class IndexManagerTest {

	public static void main(String[] args) throws Exception {
		HashSet<ConfigBean> configBeans = new HashSet<ConfigBean>();
		// ����4���������ö���
		for (int i = 0; i < 4; i++) {
			ConfigBean bean = new ConfigBean();
			bean.setIndexName("test" + i);
			bean.setIndexPath("C:/Users/CJP/Desktop/Lucene/index");
			configBeans.add(bean);
		}
		// ����IndexConfigͳһ����
		IndexConfig.setConfigBeans(configBeans);
		// IndexManager indexManager1 = IndexManager.getIndexManager("test0");
		// indexManager1.release(indexManager1.getIndexSearcher());
		String indexName = "test0";
		NRTIndex index = new NRTIndex(indexName);
		Document doc1 = new Document();
		doc1.add(new StringField("id", "123", Store.YES));
		doc1.add(new TextField("content", "����ѧԺ�������ѧϵ", Store.YES));
		index.addDocument(doc1);
		doc1 = new Document();
		doc1.add(new StringField("id", "234", Store.YES));
		doc1.add(new TextField("content", "�������ѧϵ��ʦ2��", Store.YES));
		index.addDocument(doc1);
		index.commit();
		System.out.println("�Ѿ������������������¼");

		NRTSearch search = new NRTSearch(indexName);
		QueryParser parser = new QueryParser(Version.LUCENE_43, "content",
				new StandardAnalyzer(Version.LUCENE_43));
		Query query = parser.parse("����ѧԺ�������ѧϵ��ʦ2��");
		SearchResultBean bean = search.search(query, 0, 10);
		System.out.println("��һ�β�ѯ" + bean.getCount());
		doc1 = new Document();
		doc1.add(new StringField("id", "2", Store.YES));
		doc1.add(new TextField("content", "", Store.YES));
		Term term = new Term("id", "2");
		index.updateDocument(term, doc1);
		index.commit();
		System.out.println("��һ���޸�һ����¼");
		bean = search.search(query, 0, 10);
		System.out.println("�ڶ��β�ѯ" + bean.getCount());
		index.deleteAll();
		index.commit();
	}
}
