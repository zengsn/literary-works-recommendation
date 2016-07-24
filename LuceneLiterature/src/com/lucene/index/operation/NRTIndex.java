package com.lucene.index.operation;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.search.Query;

import com.lucene.index.manager.IndexManager;

/**
 * 实现索引的基础操作，包括添加记录、删除指定条件的记录，删除全部、更新指定条件的记录，提交记录
 * 
 * @author CJP
 * 
 */
public class NRTIndex {

	private TrackingIndexWriter trackingIndexWriter;
	private String indexName;

	public NRTIndex(String indexName) {
		this.indexName = indexName;
		this.trackingIndexWriter = IndexManager.getIndexManager(indexName)
				.getTrackingIndexWriter();
	}

	/**
	 * 将内存中的内容提交到磁盘
	 */
	public void commit() {
		IndexManager.getIndexManager(indexName).commit();
	}

	/**
	 * 更新索引中的记录
	 */
	public boolean updateDocument(Term term, Document doc) {
		try {
			trackingIndexWriter.updateDocument(term, doc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 清空索引中的记录
	 */
	public boolean deleteAll() {
		try {
			trackingIndexWriter.deleteAll();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除符合条件的记录
	 */
	public boolean deleteDocument(Query query) {
		try {
			trackingIndexWriter.deleteDocuments(query);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 向索引中添加一条记录
	 */
	public boolean addDocument(Document doc) {
		try {
			trackingIndexWriter.addDocument(doc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
