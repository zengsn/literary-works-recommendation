package com.lucene.index.operation.tianya;

import org.apache.lucene.document.Document;

import com.crawl.tianya.model.Para;
import com.lucene.index.constant.Constant;
import com.lucene.index.operation.NRTIndex;
import com.util.ParseBean;

public class TianYaIndex extends NRTIndex {

	public TianYaIndex() {
		super(Constant.INDEX_NAME);
	}

	public boolean addLiteraIndex(Para para) {
		Document doc = ParseBean.bean2doc(para);
		if (doc != null) {
			return addDocument(doc);
		}
		return false;
	}

}
