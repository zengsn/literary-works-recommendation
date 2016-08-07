/**  
 *@Description:     
 */
package com.lucene.index.operation.litera;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

import com.crawl.tianya.model.IntroSearchResultBean;
import com.lucene.index.constant.Constant;
import com.lucene.index.model.IntroIndexBean;
import com.lucene.index.model.LiteraSearchResultBean;
import com.lucene.index.model.SearchResultBean;
import com.lucene.index.operation.NRTSearch;
import com.util.ParseBean;

public class IntroSearch extends NRTSearch {
	private PackQuery packQuery = new PackQuery(Constant.INDEX_NAME);

	public IntroSearch() {
		super(Constant.INDEX_NAME);
	}

	/**
	 * 根据一部分书名查找
	 */
	public LiteraSearchResultBean searchLiteraName(String key, int start,
			int end) {
		if (key == null) {
			return null;
		}
		Query query = packQuery.getOneFieldQuery(IntroIndexBean.NAME_KEY, key);
		SearchResultBean bean = search(query, start, end);
		return parse(bean);
	}

	/**
	 * 根据完整书名查找
	 */
	public LiteraSearchResultBean searchLiteraName(String key) {
		if (key == null) {
			return null;
		}
		Query query = packQuery.getStringQuery(IntroIndexBean.NAME, key);
		SearchResultBean bean = search(query, 0, 1);
		return parse(bean);
	}

	private LiteraSearchResultBean parse(SearchResultBean bean) {
		if (bean == null) {
			return null;
		}
		LiteraSearchResultBean result = new LiteraSearchResultBean();
		result.setCount(bean.getCount());
		if (bean.getDocs() != null) {
			List<IntroSearchResultBean> data = new ArrayList<IntroSearchResultBean>();
			result.setDatas(data);
			for (Document doc : bean.getDocs()) {
				data.add(ParseBean.parseDocToBean(doc));
			}
		}
		return result;
	}

}
