package com.lucene.index.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

import com.lucene.index.manager.IndexManager;
import com.lucene.index.model.SearchResultBean;

/**
 * 索引的查询操作，使用默认、指定的排序方式
 * 
 * @author CJP
 * 
 */
public class NRTSearch {

	private IndexManager indexManager;

	public NRTSearch(String indexName) {
		this.indexManager = IndexManager.getIndexManager(indexName);
	}

	/**
	 * 分页查询，排序采用指定的排序方式
	 * 
	 * @param query
	 *            查询条件
	 * @param start
	 *            从结果集的第几条开始获取，包括start
	 * @param end
	 *            获取到结果集的第几条，不包括end
	 * @param sort
	 *            指定的排序
	 * @return
	 */
	public SearchResultBean search(Query query, int start, int end, Sort sort) {
		start = start > 0 ? start : 0;
		end = end > 0 ? end : 0;
		if (query == null || start > end || indexManager == null) {
			return null;
		}
		List<Document> docs = new ArrayList<Document>();
		SearchResultBean bean = new SearchResultBean();
		bean.setDocs(docs);
		IndexSearcher searcher = indexManager.getIndexSearcher();
		try {
			TopDocs topDocs = searcher.search(query, end, sort);
			bean.setCount(topDocs.totalHits);
			end = end > topDocs.totalHits ? topDocs.totalHits : end;
			for (int i = start; i < end; i++) {
				docs.add(searcher.doc(topDocs.scoreDocs[i].doc));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			indexManager.release(searcher);
		}
		return bean;
	}

	/**
	 * 分页查询，排序采用默认的排序方式
	 * 
	 * @param query
	 *            查询条件
	 * @param start
	 *            从结果集的第几条开始获取，包括start
	 * @param end
	 *            获取到结果集的第几条，不包括end
	 * @return
	 */
	public SearchResultBean search(Query query, int start, int end) {
		start = start > 0 ? start : 0;
		end = end > 0 ? end : 0;
		if (query == null || start > end || indexManager == null) {
			return null;
		}
		SearchResultBean bean = new SearchResultBean();
		List<Document> docs = new ArrayList<Document>();
		bean.setDocs(docs);
		IndexSearcher searcher = indexManager.getIndexSearcher();
		try {
			TopDocs topDocs = searcher.search(query, end);
			bean.setCount(topDocs.totalHits);
			end = end > topDocs.totalHits ? topDocs.totalHits : end; // 选取两者中的较小值
			for (int i = start; i < end; i++) {
				docs.add(searcher.doc(topDocs.scoreDocs[i].doc));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			indexManager.release(searcher);
		}
		return bean;
	}
}
