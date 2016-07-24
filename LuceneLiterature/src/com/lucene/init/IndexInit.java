package com.lucene.init;

import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.constant.LiteraConstant;
import com.lucene.index.model.IndexConfig;
import com.lucene.index.model.IndexConfigBean;

/**
 * 用于初始化一些项目的配置
 * 
 * @author CJP
 * 
 */
public class IndexInit {

	public static void init() {
		HashSet<IndexConfigBean> set = new HashSet<IndexConfigBean>();
		IndexConfigBean bean = new IndexConfigBean();
		bean.setIndexName(LiteraConstant.INDEX_NAME);
		bean.setIndexPath("C:/Users/CJP/Desktop/Lucene/index");

		// HashMap<String, Analyzer> fieldAnalyzers = new HashMap<String,
		// Analyzer>();
		// fieldAnalyzers.put(LiteraIndexBean.INDEX_CONTENT, new IKAnalyzer());
		// Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(
		// Version.LUCENE_43), fieldAnalyzers);
		Analyzer analyzer = new IKAnalyzer();
		bean.setAnalyzer(analyzer);
		set.add(bean);
		IndexConfig.setIndexConfig(set);
	}
}
