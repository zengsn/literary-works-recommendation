package com.lucene.index.operation;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;

import com.lucene.constant.LiteraConstant;
import com.lucene.index.litera.model.LiteraBean;
import com.lucene.index.litera.model.LiteraIndexBean;
import com.lucene.util.ParseBean;

public class LiteraIndex extends NRTIndex {

	public LiteraIndex() {
		super(LiteraConstant.INDEX_NAME);
	}

	/**
	 * 创建索引
	 */
	public boolean addLiteraIndex(LiteraIndexBean bean) {
		Document doc = ParseBean.parseLiteraIndexToDocument(bean);
		if (doc != null) {
			return updateDocument(new Term("Name", bean.getName()), doc);
		}
		return false;
	}

	/**
	 * 将指定路径下的所有名著资料导入到索引中
	 * 
	 *       
	 */
	public void putLiteraToIndex(String path) {
		List<LiteraBean> beans = new ParseBean().getDatas(path);
		for (LiteraBean bean : beans) {
			LiteraIndexBean ibean = new LiteraIndexBean();
			ibean.setName(bean.getName());
			ibean.setPath(bean.getPath());
			ibean.setContent(bean.getContent());
			new LiteraIndex().addLiteraIndex(ibean);
		}
	}
}
