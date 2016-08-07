package com.lucene.index.model;

import java.util.List;

import org.apache.lucene.document.Document;

/**
 * 存放查询到的结果，包括查询到的记录条数，文档的List集合
 * 
 * @author CJP
 * 
 */
public class SearchResultBean {

	// 符合查询条件的总记录条数
	private int count;

	// 查询的结果集
	private List<Document> docs;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Document> getDocs() {
		return docs;
	}

	public void setDocs(List<Document> docs) {
		this.docs = docs;
	}

}
