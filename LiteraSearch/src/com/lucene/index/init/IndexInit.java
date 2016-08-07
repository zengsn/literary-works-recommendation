
package com.lucene.index.init;

import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;
import com.lucene.index.constant.Constant;
import com.lucene.index.model.ConfigBean;
import com.lucene.index.model.IndexConfig;

public class IndexInit {

	public static void initIndex() {
		HashSet<ConfigBean> set = new HashSet<ConfigBean>();
		ConfigBean bean = new ConfigBean();
		bean.setIndexPath("C:/Users/CJP/Desktop/Lucene");
		bean.setIndexName(Constant.INDEX_NAME);
		Analyzer analyzer = new IKAnalyzer();
		bean.setAnalyzer(analyzer);
		set.add(bean);
		IndexConfig.setConfigBeans(set);
	}
}
