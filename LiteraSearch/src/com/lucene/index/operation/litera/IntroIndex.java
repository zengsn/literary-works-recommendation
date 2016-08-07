
package com.lucene.index.operation.litera;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;

import com.lucene.index.constant.Constant;
import com.lucene.index.model.IntroIndexBean;
import com.lucene.index.operation.NRTIndex;
import com.util.ParseBean;

/**
 * 将简介表的信息添加到索引中
 * @author CJP
 *
 */
public class IntroIndex extends NRTIndex {

	public IntroIndex() {
		super(Constant.INDEX_NAME);
	}

	public boolean addIntroIndex(IntroIndexBean bean) {
		Document doc = ParseBean.parseIntroToDoc(bean);
		if (doc != null) {
			return updateDocument(new Term("id", bean.getId()), doc);
		}
		return false;
	}

}
