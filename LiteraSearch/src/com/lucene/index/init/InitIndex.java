package com.lucene.index.init;

import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lucene.index.constant.Constant;
import com.lucene.index.model.ConfigBean;
import com.lucene.index.model.IndexConfig;

public class InitIndex {

	public static void init() {
		HashSet<ConfigBean> set = new HashSet<ConfigBean>();
		ConfigBean bean = new ConfigBean();
		bean.setIndexPath("G:/lucene");
		bean.setIndexName(Constant.INDEX_NAME);
		Analyzer analyzer = new IKAnalyzer(false);
		bean.setAnalyzer(analyzer);
		set.add(bean);
		IndexConfig.setConfigBeans(set);
	}
}
