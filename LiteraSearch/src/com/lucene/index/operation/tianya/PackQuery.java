 /**  
 *@Description:     
 */ 
package com.lucene.index.operation.tianya;  

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.index.manager.IndexManager;

  
public class PackQuery {
	
	private Analyzer analyzer;
	
	public PackQuery(String indexName) {
		analyzer = IndexManager.getIndexManager(indexName).getAnalyzer();
	}
	
	/**
	 * 根据关键字查找（分词）
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public Query getOneFieldQuery (String fieldName, String value) {
		if (fieldName == null || value == null) {
			return null;
		}
		QueryParser parse = new QueryParser(Version.LUCENE_43, fieldName, analyzer);
		try {
			return parse.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Query getStringQuery (String fieldName, String value) {
		if (fieldName == null || value == null) {
			return null;
		}
		return new TermQuery(new Term(fieldName, value));
	}
	
	public Query getWildcardQuery (String fieldName, String value) {
		if (fieldName == null || value == null) {
			return null;
		}
		value = "*" + value + "*";
		return new WildcardQuery(new Term(fieldName, value));
		
	}

}
