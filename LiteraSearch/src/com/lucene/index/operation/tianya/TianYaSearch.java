/**  
 *@Description:     
 */
package com.lucene.index.operation.tianya;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import com.crawl.tianya.model.Para;
import com.lucene.index.constant.Constant;
import com.lucene.index.model.LiteraSearchResultBean;
import com.lucene.index.model.SearchResultBean;
import com.lucene.index.operation.NRTSearch;
import com.util.ParseBean;

public class TianYaSearch extends NRTSearch {
	private PackQuery packQuery = new PackQuery(Constant.INDEX_NAME);

	public TianYaSearch() {
		super(Constant.INDEX_NAME);
	}

	/**
	 * 根据关键字查找
	 */
	public LiteraSearchResultBean searchLiteraPara(String key, int start,
			int end) {
		if (key == null) {
			return null;
		}
		Query query = packQuery.getOneFieldQuery(Para.PARA, key);
		SearchResultBean bean = search(query, start, end);
		return parse(bean);
	}

	private LiteraSearchResultBean parse(SearchResultBean bean) {
		if (bean == null) {
			return null;
		}
		LiteraSearchResultBean result = new LiteraSearchResultBean();
		result.setCount(bean.getCount());
		if (bean.getDocs() != null) {
			List<Para> data = new ArrayList<Para>();
			result.setDatas(data);
			for (Document doc : bean.getDocs()) {
				data.add(ParseBean.doc2bean(doc));
			}
		}
		return result;
	}

}
